package cliente;

import conta.Conta;

import java.util.HashSet;
import java.util.Set;

public class ClienteEmpresa extends Cliente {
	private final Set<String> gerentesEmpresa = new HashSet<>();
	private final String CNPJ;
	private Conta conta;

	public ClienteEmpresa(String nome, String email, int telefone, int idade, Endereco end, boolean isOnline, double renda, String cnpj) {
		super(nome, email, telefone, idade, end, isOnline, renda);
		this.CNPJ = cnpj;
	}

	public String getCnpj() {
		return CNPJ;
	}

	@Override
	public String toString() {
		return "Empresa {" +
				"Nome = " + this.getNome() +
				"CNPJ = '" + CNPJ + '\'' + //TODO ESCONDER CNPJ NE
				'}';
	}

	@Override
	public Conta getConta() {
		return this.conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public boolean equals(Cliente outroCliente) {
		if (outroCliente instanceof ClienteEmpresa) {
			return ((ClienteEmpresa) outroCliente).CNPJ.equalsIgnoreCase(this.CNPJ);
		}
		return false;
	}
}
