package br.com.caelum.mocker;

import static org.junit.Assert.fail;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;

public class MentirosoTest {
	
	private Object internalVerify(Object proxyQueEuQueroGarantirAInvocacao, Object proxy, Method method, Object[] args, int vezes) throws ChamadaInesperadaException, NaoEraPraTerChamadoException, NaoChamouOSuficienteException {
		int invocadosAteAgora = 0;
		for(Iterator<Invocacao> iterator = invocacoes.iterator(); iterator.hasNext(); ) {
			Invocacao esperada = iterator.next();
			Invocacao invocacaoNesseInstante = new Invocacao(proxyQueEuQueroGarantirAInvocacao, method, args);
			if(esperada.equals(invocacaoNesseInstante)) {
				if(vezes==0) {
					throw new NaoEraPraTerChamadoException();
				}
				invocadosAteAgora++;
				if(invocadosAteAgora == vezes) {
					iterator.remove();
					return esperada.executaInvocacao();
				}
			}
		}
		if(invocadosAteAgora != vezes) {
			throw new NaoChamouOSuficienteException();
		}
		// eles sao iguais
		return null;
	}
	
	class ChamadaInesperadaException extends Exception {
	}
	class NaoEraPraTerChamadoException extends Exception {
	}
	class NaoChamouOSuficienteException extends Exception{
	}

	<T> T verify(T proxy) {
		return verify(proxy, 1);
	}
	
	@SuppressWarnings("unchecked")
	<T> T verify(final T proxyQueEuQueroGarantirAInvocacao, final int vezes) {
		MeuProxy meuProxy = (MeuProxy) proxyQueEuQueroGarantirAInvocacao;
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{meuProxy.getProxiedType()}, new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				try {
					return internalVerify(proxyQueEuQueroGarantirAInvocacao, proxy, method, args, vezes);
				} catch (NaoChamouOSuficienteException e) {
					fail("Era pra invocar " + method.getName() + "(" + Arrays.asList(args) + ") " + vezes + " vezes mas ele foi invocado menos do que isso.");
					return null;
				} catch (NaoEraPraTerChamadoException e) {
					fail("NAO era pra invocar " + method.getName() + "(" + Arrays.asList(args) + ") mas ele foi invocado.");
					return null;
				} catch (ChamadaInesperadaException e) {
					fail("Era esperado invocar " + method.getName() + "(" + Arrays.asList(args) + ") mas ele nao foi invocado.");
					return null;
				}
			}
		});
	}

	private List<Invocacao> invocacoes = new ArrayList<>();
	private Invocacao ultimaInvocacao;
	
	@Before
	public void clear() {
		invocacoes.clear();
		ultimaInvocacao = null;
	}
	
	@SuppressWarnings("unchecked")
	<T> T mock(final Class<T> typeToProxy) {
		return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{typeToProxy, MeuProxy.class}, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				if(method.getDeclaringClass().equals(MeuProxy.class)) {
					if(method.equals(MeuProxy.class.getMethod("getProxiedType"))) {
						return typeToProxy;
					}
					return null;
				}
				
				try {
					return internalVerify(proxy, proxy, method, args, 1);
				} catch (ChamadaInesperadaException | NaoEraPraTerChamadoException | NaoChamouOSuficienteException e) {
					// nao era esperado, nao precisa fazer nada
				}
				
				ultimaInvocacao = new Invocacao(proxy, method, args);
				invocacoes.add(ultimaInvocacao);
				if(method.getReturnType().equals(boolean.class)) {
					return false;
				}
				if(method.getReturnType().equals(Boolean.class)) {
					return Boolean.FALSE;
				}
				if(method.getReturnType().equals(int.class)) {
					return 0;
				}
				return null;
			}
		});
	}

	QuandoAcontecerAlgo quando(boolean valorQualquer) {
		return new QuandoAcontecerAlgo(ultimaInvocacao);
	}

}
