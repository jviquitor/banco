package cliente;

import conta.Conta;

public class ClientePessoa extends Cliente {
	private String cpf;
	private Conta conta;

	public ClientePessoa(String nome, String email, int telefone, int idade, Endereco end, boolean isOnline, double renda, String cpf) {
		super(nome, email, telefone, idade, end, isOnline, renda);
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	public Conta getConta() {
		return this.conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public String toString() {
		return "Pessoa {" +
				"Nome = " + this.getNome() +
				"CPF ='" + cpf + '\'' + //TODO esconder CPF NE
				'}';
	}
}

