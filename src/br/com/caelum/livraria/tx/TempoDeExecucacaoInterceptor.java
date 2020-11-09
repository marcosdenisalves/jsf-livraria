package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class TempoDeExecucacaoInterceptor implements Serializable{
	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object log(InvocationContext context) throws Exception {
		
		long before = System.currentTimeMillis();
		
		String nameMethod = context.getMethod().getName();
		Object proceed = context.proceed();
		
		long after = System.currentTimeMillis();
		long tempo = after - before;
		
		System.out.println("Método executado: "
		+ nameMethod 
		+ "\nTempo de Execução: " 
		+ tempo);
		
		return proceed;
	}

}
