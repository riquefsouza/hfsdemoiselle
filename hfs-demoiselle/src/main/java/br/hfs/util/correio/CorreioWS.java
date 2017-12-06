package br.hfs.util.correio;

import javax.xml.ws.WebServiceRef;

import br.hfs.util.correio.ws.AtendeCliente;
import br.hfs.util.correio.ws.AtendeClienteService;
import br.hfs.util.correio.ws.EnderecoERP;
import br.hfs.util.correio.ws.SQLException_Exception;
import br.hfs.util.correio.ws.SigepClienteException;

public class CorreioWS {

	@WebServiceRef(wsdlLocation = "https://apps.correios.com.br/SigepMasterJPA/AtendeClienteService/AtendeCliente?wsdl")
	static AtendeClienteService service;

	public static void main(String[] args) {
		try {
			CorreioWS cliente = new CorreioWS();
			Correio co = cliente.consultaCEPviaWS("20541090",
					"bravo.trtrio.gov.br", 8080, "henrique.souza",
					"");
			System.out.println(co.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Correio consultaCEPviaWS(String cep, String hostProxy,
			int portProxy, final String loginProxy, final String senhaProxy) {
		Correio co = new Correio();
		try {
			/*
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
					hostProxy, portProxy));

			Authenticator authenticator = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return (new PasswordAuthentication(loginProxy,
							senhaProxy.toCharArray()));
				}
			};
			Authenticator.setDefault(authenticator);
			 */
			// URL url = new URL("http://viacep.com.br/ws/" + cep + "/json");
			// URLConnection urlConnection = url.openConnection(proxy);

			service = new AtendeClienteService();

			AtendeCliente port = service.getAtendeClientePort();
			/*
			BindingProvider bp = ((BindingProvider) port);
			bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
					loginProxy);
			bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,
					senhaProxy);
			 	*/
			// ((BindingProvider)
			// AtendeCliente).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,"ac-erpweb01\\oracle");
			// ((BindingProvider)
			// AtendeCliente).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY,"test");

			EnderecoERP end = port.consultaCEP(cep);

			co.setId(end.getId());
			co.setUf(end.getUf());
			co.setEstado(end.getUf());
			co.setUfCep1("");
			co.setUfCep2("");
			co.setCidade(end.getCidade());
			co.setLogradouro(end.getEnd());
			co.setBairro(end.getBairro());
			co.setCep(end.getCep());
			co.setTipoLogradouro(end.getEnd().substring(0, end.getEnd().indexOf(" ")));
			
		} catch (SQLException_Exception e) {
			e.printStackTrace();
		} catch (SigepClienteException e) {
			e.printStackTrace();
		}
		return co;
	}

}
