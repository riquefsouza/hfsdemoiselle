package br.hfs.util;

import java.io.IOException;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import br.gov.frameworkdemoiselle.internal.configuration.JsfSecurityConfig;
import br.gov.frameworkdemoiselle.util.Beans;

public class UrlDirectAcessBlockFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String header = req.getHeader("referer");
		if (header != null) {
			chain.doFilter(request, response);
		} else {
			JsfSecurityConfig config = (JsfSecurityConfig) Beans
					.getReference(JsfSecurityConfig.class);
			String indexPrivatePage = config.getRedirectAfterLogin();
			FacesContext fc = FacesContext.getCurrentInstance();
			if (fc != null) {
				NavigationHandler nav = fc.getApplication()
						.getNavigationHandler();
				nav.handleNavigation(fc, null, indexPrivatePage);
				fc.renderResponse();
			} else {
				req.getRequestDispatcher(indexPrivatePage).forward(request,
						response);
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
