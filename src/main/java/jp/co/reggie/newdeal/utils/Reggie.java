package jp.co.reggie.newdeal.utils;

/**
 * 通用返回結果類
 *
 * @author Administrator
 */
public class Reggie<T> {

	/**
	 * 處理成功的信息
	 */
	private static final String SUCCESS = "SUCCESS";

	/**
	 * 處理失敗的信息
	 */
	private static final String ERROR = "ERROR";

	/**
	 * 編碼： 成功：SUCCESS，失敗：ERROR
	 */
	private String code;

	/**
	 * 錯誤信息
	 */
	private String msg;

	/**
	 * 數據
	 */
	private T data;

	/**
	 * 處理成功
	 *
	 * @param object 對象
	 * @param <T>    汎型
	 * @return 返回的對象
	 */
	public static <T> Reggie<T> success(final T object) {
		final Reggie<T> reggie = new Reggie<T>();
		reggie.data = object;
		reggie.code = SUCCESS;
		return reggie;
	}

	/**
	 * 請求失敗
	 *
	 * @param msg 請求失敗的信息
	 * @param <T> 汎型
	 * @return 失敗的信息
	 */
	public static <T> Reggie<T> error(final String msg) {
		final Reggie<T> reggie = new Reggie<>();
		reggie.msg = msg;
		reggie.code = ERROR;
		return reggie;
	}

	/**
	 * 無參構造器
	 */
	public Reggie() {
		super();
	}

	/**
	 * 全參構造器
	 *
	 * @param code 編碼
	 * @param msg  信息
	 * @param data 數據
	 */
	public Reggie(final String code, final String msg, final T data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
}
