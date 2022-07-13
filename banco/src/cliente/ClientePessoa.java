package cliente;

import interfaceUsuario.exceptions.ValorInvalido;

import java.io.Serial;

public class ClientePessoa extends Cliente {
	@Serial
	private static final long serialVersionUID = 19L;
	private final String CPF;

	public ClientePessoa(String nome, String email, String telefone, Integer idade, Endereco end, String cpf, String senha) throws ValorInvalido {
		super(nome, email, telefone, idade, end, senha);
		this.CPF = cpf;
	}

	/**
	 * Apenas mostra todas as informações são mostradas, útil para debug.
	 *
	 * @return String
	 */

	/*private String clienteAllInfos() {
		String toString = "[CLIENTE]\n";
		if (NOME != null) {
			toString = toString + "NOME: " + NOME + "\n";
		}
		if (CPF != null) {
			toString = toString + "CPF: " + CPF + "\n";
		}
		return getString(toString);
	}*/
	@Override
	public String toString() {
		String toString = "[CLIENTE]\n";
		if (NOME != null) {
			toString = toString + "NOME: " + NOME + "\n";
		}
		return getString(toString);
	}


	public String getIdentificacao() {
		return this.CPF;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean equals(Cliente outroCliente) {
		if (outroCliente instanceof ClientePessoa) {
			return ((ClientePessoa) outroCliente).CPF.equalsIgnoreCase(this.CPF);
		}
		return false;
	}
}

