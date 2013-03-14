package br.com.caelum.mocker;

public class QuandoAcontecerAlgo {

	private Invocacao invocacao;

	public QuandoAcontecerAlgo(Invocacao invocacao) {
		this.invocacao = invocacao;
	}

	public void retorna(Object object) {
		invocacao.entaoRetorna(object);
	}

}
