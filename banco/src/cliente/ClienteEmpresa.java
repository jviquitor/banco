package cliente;

import conta.Conta;

import java.util.HashSet;
import java.util.Set;

public class ClienteEmpresa extends Cliente {
	private String cnpj;
	private final Set<String> gerentesEmpresa = new HashSet<>();
	private Conta conta;

	public ClienteEmpresa(String nome, String email, int telefone, int idade, Endereco end, boolean isOnline, double renda, String cnpj) {
		super(nome, email, telefone, idade, end, isOnline, renda);
		this.cnpj = cnpj;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
}
