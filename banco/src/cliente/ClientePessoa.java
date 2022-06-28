package cliente;

import conta.Conta;

public class ClientePessoa extends Cliente {
	private final String CPF;
	private Conta conta;

	public ClientePessoa(String nome, String email, int telefone, int idade, Endereco end, boolean isOnline, double renda, String cpf) {
		super(nome, email, telefone, idade, end, isOnline, renda);
		this.CPF = cpf;
	}

	public String getCpf() {
		return CPF;
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
				"CPF ='" + CPF + '\'' + //TODO esconder CPF NE
				'}';
	}

	@Override
	public boolean equals(Cliente outroCliente) {
		if (outroCliente instanceof ClientePessoa) {
			return ((ClientePessoa) outroCliente).CPF.equalsIgnoreCase(this.CPF);
		}
		return false;
	}
}

