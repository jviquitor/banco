package interfaceUsuario;

import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;

public class InterfaceUsuario {
	private static DadosConta dadosConta;
	private static DadosCartao dadosCartao;
	private static Cliente cliente;

	public static Cliente getCliente() {
		return cliente;
	}

	public static DadosConta getDadosConta() {
		return dadosConta;
	}

	public static DadosCartao getDadosCartao() {
		return dadosCartao;
	}
}
