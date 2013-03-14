package br.com.caelum.mocker;

import org.junit.Test;

public class ProcessoDeCompraTest extends MentirosoTest{
	
	@Test
	public void deveDespacharOProdutoEEnviarUmEmail() {
		Compra compra = new Compra("guilherme.silveira@caelum.com.br",new Produto(45.0));
		
		Despachante despachante= mock(Despachante.class);
		EnviadorDeEmail enviadorDeEmail = mock(EnviadorDeEmail.class);
		ProcessoDeCompra processo = new ProcessoDeCompra(despachante, enviadorDeEmail);
		
		quando(despachante.envia(compra)).retorna(true);
		processo.executa(compra);
		
		verify(enviadorDeEmail).envia("Sua compra foi um sucesso.", compra.toString(), "guilherme.silveira@caelum.com.br");
	}

	
	@Test
	public void naoDeveEnviarEmailSeOcorreFalhaNoDespachante() {
		Compra compra = new Compra("guilherme.silveira@caelum.com.br",new Produto(45.0));
		
		Despachante despachante= mock(Despachante.class);
		EnviadorDeEmail enviadorDeEmail = mock(EnviadorDeEmail.class);
		ProcessoDeCompra processo = new ProcessoDeCompra(despachante, enviadorDeEmail);
		
		quando(despachante.envia(compra)).retorna(false);
		processo.executa(compra);
		
		verify(enviadorDeEmail, 0).envia("Sua compra foi um sucesso.", compra.toString(), "guilherme.silveira@caelum.com.br");
	}

	
}
