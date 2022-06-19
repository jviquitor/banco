package interfaceUsuario;

import cliente.Cliente;
import conta.Conta;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;
import interfaceUsuario.dados.DadosTransacao;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public class InterfaceUsuario {
	private static DadosConta dadosConta;
	private static DadosCartao dadosCartao;
	private static Cliente clienteAtual;

	public static DadosTransacao getDadosTransacao() {
		return dadosTransacao;
	}

	private static DadosTransacao dadosTransacao;

	public static Cliente getClienteAtual() {
		return clienteAtual;
	}


	public static Data getDataAgendada() {
		return DataBank.criaData(InterfaceUsuario.chamarUsuarioParaEscolherADataAgendada()); //TODO Lembrando que aqui ainda tem o horario
	}

	public static String chamarUsuarioParaEscolherADataAgendada() {
		return "01/02/2002";
	}

	public static Cliente clienteAtualLogado() {
		return clienteAtual;
	}

	public static Conta usuarioAtualConta() {

		return clienteAtual.getConta();
	}

	public static DadosConta getDadosConta() {
		return dadosConta;
	}

	public static DadosCartao getDadosCartao() {
		return dadosCartao;
	}

	public static Double getValorUsuarioDesejaPagar() {
		//TODO a interface ira perguntar qual o valor que o usuario ira pagar da fatura e ira tratar os casos
		//VERIFICAR SE O VALOR EXISTE NA CONTA, VERIFICAR SE O VALOR EH NEGATIVO, VERIFICAR SE O VALOR EH IGUAL A 0, VERIFICAR SE O VALOR EH MAIOR DO QUE O VALOR DA FATURA ATUAL
		return 1.0;
	}

	public static boolean pagarFatura() {
		Double valor = getValorUsuarioDesejaPagar();
		clienteAtual.getConta().pagarFatura(valor);
		return true;
	}

	public static boolean pagamentoDebitoAutomatic() {
		if (clienteAtual.getConta().getDebitoAutomatic()) {
			Data dataAtual = DataBank.criaData(); //TODO usar uma funcao que nao retorne tambem a hora

			if (dataAtual.equals(clienteAtual.getConta().getDataDebitoAutomatic())) {
				InterfaceUsuario.pagarFatura();
				return true;
			}
		}
		return false; //TODO aqui pode ser uma excecao de pagamento nao realizado por algo
	}

}
