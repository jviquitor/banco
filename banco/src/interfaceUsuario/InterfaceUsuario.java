package interfaceUsuario;

import cliente.Cliente;
import conta.Conta;
import funcionalidades.exceptions.EmprestimoException;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;
import interfaceUsuario.dados.DadosTransacao;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public class InterfaceUsuario {
	private static DadosConta dadosConta;
	private static DadosCartao dadosCartao;
	private static Cliente clienteAtual;
	private static DadosTransacao dadosTransacao;

	public static DadosTransacao getDadosTransacao() {
		return dadosTransacao;
	}

	public static Cliente getClienteAtual() {
		return clienteAtual;
	}

	public static Data getDataAgendada() {
		return DataBank.criarData(InterfaceUsuario.chamarUsuarioParaEscolherDataAgendada(), DataBank.SEM_HORA);
	}

	public static String chamarUsuarioParaEscolherDataAgendada() {
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

	//TODO a interface ira perguntar qual o valor que o usuario ira pagar da fatura e ira tratar os casos
	//VERIFICAR SE O VALOR EXISTE NA CONTA, VERIFICAR SE O VALOR EH NEGATIVO, VERIFICAR SE O VALOR EH IGUAL A 0, VERIFICAR SE O VALOR EH MAIOR DO QUE O VALOR DA FATURA ATUAL
	public static Double getValorUsuarioDesejaPagar() {
		return 1.0;
	}

	//TODO a interface ira perguntar qual o valor que o usuario irá pedir de empréstimo e tratará os casos
	public static Double getValorUsuarioDesejaPedir() {
		return 1.0;
	}

	public static boolean pagarFatura() {
		Double valor = getValorUsuarioDesejaPagar();
		clienteAtual.getConta().pagarFatura(valor);
		return true;
	}

	public static void pedirEmprestimo() {
		if (InterfaceUsuario.usuarioAtualConta().hasEmprestimo()) {
			throw new EmprestimoException("Essa conta ja pediu emprestimo.");
		}
		InterfaceUsuario.usuarioAtualConta().setEmprestimo(getValorUsuarioDesejaPedir());
	}

	public static boolean pagamentoDebitoAutomatico() {
		if (clienteAtual.getConta().getDebitoAutomatico()) {
			Data dataAtual = DataBank.criarData(DataBank.SEM_HORA); //TODO usar uma funcao que nao retorne tambem a hora

			if (dataAtual.toString(DataBank.SEM_HORA).equals(clienteAtual.getConta().getDataDebitoAutomatico().toString(DataBank.SEM_HORA))) {
				InterfaceUsuario.pagarFatura();
				return true;
			}
		}
		return false; //TODO aqui pode ser uma excecao de pagamento nao realizado por algo
	}

}
