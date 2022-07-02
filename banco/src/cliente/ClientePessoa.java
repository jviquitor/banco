package cliente;

import conta.Conta;

public class ClientePessoa extends Cliente {
	private final String CPF;

	public ClientePessoa(String nome, String email, String telefone, Integer idade, Endereco end, String cpf, String senha) {
		super(nome, email, telefone, idade, end, senha);
		this.CPF = cpf;
	}

	@Override
	public String toString() {
		return "Pessoa {" +
				"Nome = " + this.getNome() +
				"CPF ='" + CPF + '\'' + //TODO esconder CPF NE
				'}';
	}

	public String getIdentificacao() {
		return this.CPF;
	}

	@Override
	public boolean equals(Cliente outroCliente) {
		if (outroCliente instanceof ClientePessoa) {
			return ((ClientePessoa) outroCliente).CPF.equalsIgnoreCase(this.CPF);
		}
		return false;
	}
}

