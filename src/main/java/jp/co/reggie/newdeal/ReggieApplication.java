package jp.co.reggie.newdeal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Administrator
 * @date 2022-11-08
 */
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReggieApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(ReggieApplication.class, args);
		LOGGER.info("本工程啓動成功......");
	}
}
