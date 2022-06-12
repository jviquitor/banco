package interfaceUsuario;

import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;
import interfaceUsuario.dados.DadosTransacao;

public class InterfaceUsuario {
	private static DadosConta dadosConta;
	private static DadosCartao dadosCartao;
	private static String dataAgendada;
	private static Cliente cliente;

	public static DadosTransacao getDadosTransacao() {
		return dadosTransacao;
	}

	private static DadosTransacao dadosTransacao;

	public static Cliente getCliente() {
		return cliente;
	}

	public static String getDataAgendada() {
		return dataAgendada;
	}

	public static void setDataAgendada() {
		dataAgendada = InterfaceUsuario.chamarUsuarioParaEscolherADataAgendada();
		;
	}

	public static String chamarUsuarioParaEscolherADataAgendada() {
		return "01/02/2002";
	}

	public static DadosConta getDadosConta() {
		return dadosConta;
	}

	public static DadosCartao getDadosCartao() {
		return dadosCartao;
	}
}
