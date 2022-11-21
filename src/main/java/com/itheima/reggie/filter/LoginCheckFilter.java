package com.itheima.reggie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.Constants;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.utils.ComparisonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 登錄檢查過濾器
 *
 * @author Administrator
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

	/**
	 * 路徑匹配器，支持通配符；
	 */
	private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 獲取請求的URI路徑；
		String requestURI = request.getRequestURI();
		// 定義無需過濾的路徑集合；
		final String[] urls = new String[] { "/employee/login", "/employee/logout", "/front/**", "/backend/**" };
		// 獲取用戶ID；
		Long empId = (Long) request.getSession().getAttribute(Constants.getEntityName(new Employee()));
		// 判斷本次請求是否需要處理，如果勿需處理，則直接放行；
		if (this.check(requestURI, urls)) {
			log.info("本次請求{}不需要處理", requestURI);
			filterChain.doFilter(request, response);
			return;
		} else if (ComparisonUtils.isNotEqual(null, empId)) {
			log.info("用戶已登錄，用戶ID為：{}", empId);
			// 將當前ID儲存於内存綫程中；
			BaseContext.setCurrentId(empId);
			filterChain.doFilter(request, response);
			return;
		}
		// 如果未登錄，則返回未登錄結果；
		log.info("訪問失敗：{}", Constants.NOT_LOGIN);
		response.getWriter().write(JSON.toJSONString(R.error(Constants.NOT_LOGIN)));
	}

	private boolean check(String requestURI, @NonNull String... urls) {
		for (String url : urls) {
			boolean match = PATH_MATCHER.match(url, requestURI);
			if (match) {
				return true;
			}
		}
		return false;
	}

}
