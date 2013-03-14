package br.com.caelum.mocker;

import java.lang.reflect.Method;
import java.util.Arrays;

public class Invocacao {

	private final Object[] args;
	private final Method method;
	private final Object proxy;
	private Object retornoEsperado = null;

	public Invocacao(Object proxy, Method method, Object[] args) {
		this.proxy = proxy;
		this.method = method;
		this.args = args;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return false;
		if(!(obj instanceof Invocacao)) return false;
		Invocacao outra = (Invocacao) obj;
		boolean mesma = outra.method.equals(method) && outra.proxy == proxy && Arrays.equals(outra.args, args);
		return mesma;
	}

	public void entaoRetorna(Object object) {
		this.retornoEsperado = object;
	}

	public Object executaInvocacao() {
		return retornoEsperado;
	}

}
