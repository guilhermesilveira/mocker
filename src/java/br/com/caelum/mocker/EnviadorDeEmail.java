package br.com.caelum.mocker;

public interface EnviadorDeEmail {

	public abstract void envia(String assunto, String corpo, String comprador);

}