package teste.conta;

//import das classes
import conta.Conta;
import transacao.Transacao;
import interfaceUsuario.dados.DadosTransacao;

//import dos testes
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import agencia.exceptions.BuscaException;



public class ContaTeste {
	
	private Conta conta, conta2;
	private DadosTransacao dadosTransacao;
	private Transacao transacao;
	
	public void inicaliza() throws BuscaException {
		conta = new Conta();
		conta2 = new Conta();
		dadosTransacao = new DadosTransacao(99.90, "ChavePix@abubleh.com", "ChavePix2@abubleh.com", "pix", "pix");

		transacao = new Transacao(dadosTransacao);
		
		
		
	}
	
	@Test
	public void addTransacaoRealizadaTest() throws BuscaException {
		inicaliza();
		boolean statusTransacao = conta.addTransacaoRealizada(transacao);
		
		Assertions.assertTrue(statusTransacao);
		
	}
	
	
}