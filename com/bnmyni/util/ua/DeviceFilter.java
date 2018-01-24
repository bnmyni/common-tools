package com.pica.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pica.common.util.UserAgent;
import com.pica.common.util.file.FilterUtils;

/**
 * 机型识别过滤器
 */
public class DeviceFilter implements Filter {

	private FilterConfig filterConfig = null;

	@Override
	public void destroy() {
		filterConfig = null;
	}

	/*
	 * 获取用户机型信息，分析UA，从cookie中取机型信息，如没有再从机型map中取，如还没有再按指定逻辑分析
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (filterConfig == null) {
			return;
		}

		if (!FilterUtils.needFilter(req.getRequestURI(),
				FilterUtils.FILTER_DEVICE)) {
			chain.doFilter(request, response);
		} else {
			String uaHeader = UserAgent.useragent(req);

			if (StringUtils.isBlank(uaHeader)) {
				chain.doFilter(request, response);
				return;
			}

			uaHeader = uaHeader.trim().toLowerCase();
			// ua适配，重定向至指定路径
			if (uaHeader.contains("android") || uaHeader.contains("iphone")
					|| uaHeader.contains("ipad")) {
				request.setAttribute("isWap", true);
			} else {
				request.setAttribute("isWap", false);
			}

			chain.doFilter(request, response);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}
}
