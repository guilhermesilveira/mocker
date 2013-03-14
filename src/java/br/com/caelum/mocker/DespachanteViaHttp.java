package br.com.caelum.mocker;

public class DespachanteViaHttp implements Despachante {

	/* (non-Javadoc)
	 * @see br.com.caelum.mocker.Despachante#envia(br.com.caelum.mocker.Compra)
	 */
	@Override
	public boolean envia(Compra compra) {
		System.out.println("Despachante recebe uma msg via post http");
		return true;
	}

}
