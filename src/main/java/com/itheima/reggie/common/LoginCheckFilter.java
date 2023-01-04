package com.itheima.reggie.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.utils.ComparisonUtils;
import com.itheima.reggie.utils.Reggie;

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
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain filterChain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 獲取請求的URI路徑；
		final String requestURI = request.getRequestURI();
		// 定義無需過濾的路徑集合；
		final String[] urls = new String[] { "/employee/login", "/employee/logout", "/front/**", "/backend/**",
				"/common/**", "/user/login", "/user/sendMsg" };
		// 獲取用戸ID；
		final Long empId = (Long) request.getSession().getAttribute(Constants.getEntityName(new Employee()));
		final Long UserId = (Long) request.getSession().getAttribute(Constants.getEntityName(new User()));
		// 判斷本次請求是否需要處理，如果勿需處理，則直接放行；
		if (this.check(requestURI, urls)) {
			log.info("本次請求{}不需要處理", requestURI);
			filterChain.doFilter(request, response);
			return;
		} else if (ComparisonUtils.isNotEqual(null, empId)) {
			log.info("用戸已登錄，用戸ID為：{}", empId);
			// 將當前ID儲存於内存綫程中；
			BaseContext.setCurrentId(empId);
			filterChain.doFilter(request, response);
			return;
		} else if (ComparisonUtils.isNotEqual(null, UserId)) {
			log.info("用戸已登錄，用戸ID為：{}", UserId);
			// 將當前ID儲存於内存綫程中；
			BaseContext.setCurrentId(UserId);
			filterChain.doFilter(request, response);
			return;
		}
		// 如果未登錄，則返回未登錄結果；
		log.info("訪問失敗：{}", Constants.NOT_LOGIN);
		response.getWriter().write(JSON.toJSONString(Reggie.error(Constants.NOT_LOGIN)));
	}

	/**
	 * 檢查本次請求是否位於無需過濾的路徑集合
	 * 
	 * @param requestURI 本次請求路徑
	 * @param urls       無需過濾的路徑集合
	 * @return true：滿足，false：不滿足
	 */
	private boolean check(final String requestURI, final String... urls) {
		for (final String url : urls) {
			final boolean match = PATH_MATCHER.match(url, requestURI);
			if (match) {
				return true;
			}
		}
		return false;
	}

}
