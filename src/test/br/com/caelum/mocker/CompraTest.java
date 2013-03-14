package br.com.caelum.mocker;

import static org.junit.Assert.*;

import org.junit.Test;

public class CompraTest {
	
	@Test
	public void deveSomarOPrecoDeTodosOsProdutos() {
		Compra compra = new Compra("", new Produto(15.0), new Produto(30.2));
		assertEquals(45.2, compra.getTotal(), 0.000001);
	}

	@Test
	public void deveTerCustoZeroEmCompraVazia() {
		Compra compra = new Compra("");
		assertEquals(0.0, compra.getTotal(), 0.000001);
	}

}
