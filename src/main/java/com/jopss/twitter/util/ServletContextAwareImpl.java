package com.jopss.twitter.util;

import javax.servlet.ServletContext;
import org.springframework.stereotype.Component;

import org.springframework.web.context.ServletContextAware;

/**
 * Classe padrao do Spring para injecao do ServletContext.
 */
@Component
public class ServletContextAwareImpl implements ServletContextAware {

	private ServletContext servletCtx;

	@Override
	public void setServletContext(ServletContext ctx) {
		this.servletCtx = ctx;
	}

	public ServletContext getServletCtx() {
		return servletCtx;
	}

}
