package br.com.caelum.mocker;

public class ProcessoDeCompra {

	private Despachante despachante;
	private EnviadorDeEmail enviadorDeEmail;

	public ProcessoDeCompra(Despachante despachante,
			EnviadorDeEmail enviadorDeEmail) {
		this.despachante = despachante;
		this.enviadorDeEmail = enviadorDeEmail;
	}

	public void executa(Compra compra) {
		if(despachante.envia(compra)) {
			enviadorDeEmail.envia("Sua compra foi um sucesso.", compra.toString(), compra.getComprador());
		}
	}

}
