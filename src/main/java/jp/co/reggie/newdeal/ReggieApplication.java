package jp.co.reggie.newdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @date 2022-11-08
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReggieApplication.class, args);
		log.info("本工程啓動成功......");
	}
}
