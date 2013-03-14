package br.com.caelum.mocker;

public class EnviadorDeEmailPadrao implements EnviadorDeEmail {

	/* (non-Javadoc)
	 * @see br.com.caelum.mocker.EnviadorDeEmail#envia(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void envia(String assunto, String corpo, String comprador) {
		System.out.println("Enviando um email DE VERDADE para " + comprador);
	}

}
