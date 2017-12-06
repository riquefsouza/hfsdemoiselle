package br.hfs.util;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.util.Beans;

public class SecurityFilter implements Filter {
	
	private List<String> extensionsFreeAcess = null;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String requestURL = req.getRequestURL().toString();
		boolean filterResource = true;
		int indice = requestURL.lastIndexOf(".");
		if (indice != -1) {
			String extensao = requestURL.substring(indice);
			filterResource = !this.extensionsFreeAcess.contains(extensao);
		}

		if (filterResource) {
			SecurityContext securityContext = (SecurityContext) Beans
					.getReference(SecurityContext.class);
			Principal usuarioLogado = securityContext.getUser();
			if (usuarioLogado != null) {
				chain.doFilter(request, response);
			} else {
				JsfSecurityConfig config = (JsfSecurityConfig) Beans
						.getReference(JsfSecurityConfig.class);
				String loginPage = config.getLoginPage();
				FacesContext fc = FacesContext.getCurrentInstance();
				if (fc != null) {
					NavigationHandler nav = fc.getApplication()
							.getNavigationHandler();
					nav.handleNavigation(fc, null, loginPage);
					fc.renderResponse();
				} else {
					req.getRequestDispatcher(loginPage).forward(request,
							response);
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String initParameter = filterConfig
				.getInitParameter("freeAcessResources");
		String[] split = initParameter.split(",");
		this.extensionsFreeAcess = new ArrayList<String>();
		for (String ext : split)
			this.extensionsFreeAcess.add(ext.trim());
	}
	
	public void destroy() {
	}
	
}
