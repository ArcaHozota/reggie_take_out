package com.itheima.reggie.config;

import java.io.Serializable;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class Snowflake {

	// 算法基礎屬性；
	private final SnowflakeProperties snowflakeProperties = new SnowflakeProperties();
	// 初始時間(紀年)，可用雪花算法服務上線時間戳的値；
	// 1649059688068L即2022-04-04 16:08:08:UTC+8
	private static final long INIT_EPOCH = 1649059688068L;
	// 記錄最後使用的毫秒時間戳，主要用於判斷是否同一毫秒數以及用於伺服器時鐘回撥判斷；
	private long lastTimeMillis = -1L;
	// dataCenterId的位數；
	private static final long DATA_CENTER_ID_BITS = 5L;
	// dataCenterId占用5个比特位，最大値31；
	// 0000000000000000000000000000000000000000000000000000000000011111
	private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
	// workId占用的位數；
	private static final long WORKER_ID_BITS = 5L;
	// workId占用5个比特位，最大値31；
	// 0000000000000000000000000000000000000000000000000000000000011111
	private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
	// 最後12位，代表每毫秒内可産生的最大序列號値，即 2^12 - 1 = 4095；
	private static final long SEQUENCE_BITS = 12L;
	// 掩碼（最低12位为1，高位都为0），主要用於與自增後的序列號進行位與，如果値為0，則代表自增後的序列號超過了4095；
	// 0000000000000000000000000000000000000000000000000000111111111111
	private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
	// 同一毫秒内的最新序號，最大値可為 2^12 - 1 = 4095；
	private long sequence;
	// workId位需要左移的位數：12；
	private static final long WORK_ID_SHIFT = SEQUENCE_BITS;
	// dataCenterId位需要左移的位數：17；
	private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
	// 時間戳需要左移的位數：12+5+5；
	private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

	/**
	 * 參數構造器
	 *
	 * @param workerId     ワークノードID
	 * @param dataCenterId データセンターID
	 */
	private Snowflake(final Long workerId, final Long dataCenterId) {
		// 檢查datacenterId値的合法性；
		if (this.snowflakeProperties.getDataCenterId() < 0
				|| this.snowflakeProperties.getDataCenterId() > MAX_DATA_CENTER_ID) {
			throw new IllegalArgumentException(String.format("datacenterId的値必須大於0並且小於%d", MAX_DATA_CENTER_ID));
		}
		// 檢查workId値的合法性；
		if (this.snowflakeProperties.getWorkerId() < 0 || this.snowflakeProperties.getWorkerId() > MAX_WORKER_ID) {
			throw new IllegalArgumentException(String.format("workId的値必須大於0並且小於%d", MAX_WORKER_ID));
		}
		this.snowflakeProperties.setDataCenterId(dataCenterId);
		this.snowflakeProperties.setWorkerId(workerId);
	}

	/**
	 * 初始化SnowflakeIdWorkerBean
	 *
	 * @return SnowflakeIdWorker
	 */
	@Bean
	public Snowflake snowflake() {
		return new Snowflake(this.snowflakeProperties.getWorkerId(), this.snowflakeProperties.getDataCenterId());
	}

	/**
	 * 獲取指定時間戳的下一時刻，也可以説是下一毫秒；
	 *
	 * @param lastTimeMillis 指定毫秒時間戳
	 * @return 時間戳
	 */
	private long toNextMillis(final long lastTimeMillis) {
		long currentTimeMillis = System.currentTimeMillis();
		while (currentTimeMillis <= lastTimeMillis) {
			currentTimeMillis = System.currentTimeMillis();
		}
		return currentTimeMillis;
	}

	/**
	 * 主鍵生成
	 *
	 * @return 主鍵ID
	 */
	public synchronized Serializable nextId() {
		// 獲取當前系統時間；
		long currentTimeMillis = System.currentTimeMillis();
		// 當前時間小於上一次生成ID使用的時間，可能出現服務器時鐘回撥問題；
		if (currentTimeMillis < this.lastTimeMillis) {
			throw new RuntimeException(String.format("可能出現伺服器時鐘回撥問題，請檢查伺服器時間。當前伺服器時間戳：%d，上一次使用時間戳：%d；",
					currentTimeMillis, this.lastTimeMillis));
		}
		// 處於同一毫秒數内，則序列號遞增1且最大値為4095；
		if (currentTimeMillis == this.lastTimeMillis) {
			// 序列號的最大値為4095；
			this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
			// 使用掩碼（最低12位为1，高位都为0）進行位與運行後如果値為0，則自增後的序列號超過了4095；
			if (this.sequence == 0) {
				// 使用新的時間戳；
				currentTimeMillis = this.toNextMillis(this.lastTimeMillis);
			}
		} else {
			// 不在同一毫秒内，則序列號重新从0開始且最大値為4095；
			this.sequence = 0;
		}
		// 記錄最後使用的毫秒時間戳；
		this.lastTimeMillis = currentTimeMillis;
		// 核心算法，將不同部分的數值移動到指定的位置，然后進行或運行；
		return ((currentTimeMillis - INIT_EPOCH) << TIMESTAMP_SHIFT)
				| (this.snowflakeProperties.getDataCenterId() << DATA_CENTER_ID_SHIFT)
				| (this.snowflakeProperties.getWorkerId() << WORK_ID_SHIFT) | this.sequence;
	}
}
