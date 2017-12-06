package br.hfs.util.cep;


//http://api.postmon.com.br/v1/cep/20541090?format=xml

public class CepExemplo implements ViaCEPEvents {

	public static void main(String[] args) {
		new CepExemplo().run();
	}

	public void run() {
		ViaCEP viaCEP = new ViaCEP(this);
		String cep = "20541090";
		try {
			viaCEP.buscar(cep, "bravo.trtrio.gov.br", 8080, "henrique.souza", "");
		} catch (ViaCEPException ex) {
			System.err.println("Ocorreu um erro na classe " + ex.getClasse()
					+ ": " + ex.getMessage());
		}
	}

	@Override
	public void onCEPSuccess(ViaCEP cep) {
		System.out.println();
		System.out.println("CEP " + cep.getCep() + " encontrado!");
		System.out.println("Logradouro: " + cep.getLogradouro());
		System.out.println("Complemento: " + cep.getComplemento());
		System.out.println("Bairro: " + cep.getBairro());
		System.out.println("Localidade: " + cep.getLocalidade());
		System.out.println("UF: " + cep.getUf());
		System.out.println("Gia: " + cep.getGia());
		System.out.println("Ibge: " + cep.getIbge());
		System.out.println();
	}

	@Override
	public void onCEPError(String cep) {
		System.out.println();
		System.out.println("Não foi possível encontrar o CEP " + cep + "!");
		System.out.println();
	}

}
