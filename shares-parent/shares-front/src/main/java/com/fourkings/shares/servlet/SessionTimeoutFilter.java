package com.fourkings.shares.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionTimeoutFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		if (session == null || session.getAttribute("user") == null) {
			// 如果判断是 AJAX 请求,直接设置为session超时
			if (req.getHeader("x-requested-with") != null && req.getHeader("x-requested-with").equals("XMLHttpRequest")) {
				rep.setHeader("sessionstatus", "timeout");
			} else {
				//rep.sendRedirect(req.getContextPath() + "/login");
			}
		}
		
		chain.doFilter(request, response);
	}

	public void destroy() {

	}

}
