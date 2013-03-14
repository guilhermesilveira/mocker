package br.com.caelum.mocker;

import java.util.Arrays;
import java.util.List;

public class Compra {

	private final List<Produto> produtos;
	private final String comprador;

	public Compra(String comprador, Produto ... produtos) {
		this.comprador = comprador;
		this.produtos = Arrays.asList(produtos);
	}

	public double getTotal() {
		double total = 0;
		for (Produto p : produtos) {
			total += p.getPreco();
		}
		return total;
	}

	public String  getComprador() {
		return comprador;
	}

}
