package interfaceUsuario;

import cliente.Cliente;
import conta.Conta;
import interfaceUsuario.dados.*;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public class InterfaceUsuario {
	private static DadosConta dadosConta;
	private static DadosCartao dadosCartao;
	private static Cliente clienteAtual;
	private static DadosTransacao dadosTransacao;
	private static DadosChavesPix dadosChavePix;
	private static DadosBoleto dadosBoleto;

	public static DadosTransacao getDadosTransacao() {
		return dadosTransacao;
	}

	public static void setDadosTransacao(DadosTransacao dadosTransacao) {
		InterfaceUsuario.dadosTransacao = dadosTransacao;
	}

	public static DadosBoleto getDadosBoleto() {
		return dadosBoleto;
	}

	public static void setDadosBoleto(DadosBoleto dadosBoleto) {
		InterfaceUsuario.dadosBoleto = dadosBoleto;
	}

	public static DadosChavesPix getDadosChavePix() {
		return dadosChavePix;
	}

	public static void setDadosChavePix(DadosChavesPix dadosChavePix) {
		InterfaceUsuario.dadosChavePix = dadosChavePix;
	}

	public static Cliente getClienteAtual() {
		return clienteAtual;
	}

	public static void setClienteAtual(Cliente clienteAtual) {
		InterfaceUsuario.clienteAtual = clienteAtual;
	}

	public static Data getDataAgendada() {
		return DataBank.criarData(InterfaceUsuario.chamarUsuarioParaEscolherDataAgendada(), DataBank.SEM_HORA);
	}

	public static String chamarUsuarioParaEscolherDataAgendada() {
		return "01/02/2002";
	}

	public static Conta usuarioAtualConta() {
		return clienteAtual.getConta();
	}

	public static DadosConta getDadosConta() {
		return dadosConta;
	}

	public static void setDadosConta(DadosConta dadosConta) {
		InterfaceUsuario.dadosConta = dadosConta;
	}

	public static DadosCartao getDadosCartao() {
		return dadosCartao;
	}

	public static void setDadosCartao(DadosCartao dadosCartao) {
		InterfaceUsuario.dadosCartao = dadosCartao;
	}

	public static boolean pagarFatura(Double valor) {
		clienteAtual.getConta().pagarFatura(valor);
		return true;
	}

	//TODO fazer pagamento debito automatico e Agendada Transacoes
	public static boolean pagamentoDebitoAutomatico(Double v) {
		if (clienteAtual.getConta().getDebitoAutomatico()) {
			Data dataAtual = DataBank.criarData(DataBank.SEM_HORA); //TODO usar uma funcao que nao retorne tambem a hora

			if (dataAtual.toString(new int[]{DataBank.SEM_HORA}).equals(clienteAtual.getConta().getDataDebitoAutomatico().toString(new int[]{DataBank.SEM_HORA}))) {
				return InterfaceUsuario.pagarFatura(v);
			}
		}
		return false; //TODO aqui pode ser uma excecao de pagamento nao realizado por algo
	}

}
