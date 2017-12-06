package br.hfs.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@ApplicationScoped
@Named("userSessionController")
public class UserSessionController {

	private List<HttpSession> listaDeSessions;

	public void addSession(HttpSession session) {
		getListaDeSessions().add(session);
	}

	public void checkRepeatedLogin(HttpSession session) {
		Iterator<HttpSession> it = getListaDeSessions().iterator();

		while (it.hasNext()) {
			HttpSession sessaoDaLista = it.next();
			try {
				if (sessaoDaLista.getAttribute("login").equals(
						session.getAttribute("login"))) {
					sessaoDaLista.invalidate();
					it.remove();
				}
			} catch (IllegalStateException e) {
				it.remove();
			}
		}
		getListaDeSessions().add(session);
	}

	public List<HttpSession> getListaDeSessions() {
		if (listaDeSessions == null) {
			listaDeSessions = new ArrayList<HttpSession>();
		}
		return listaDeSessions;
	}
}
