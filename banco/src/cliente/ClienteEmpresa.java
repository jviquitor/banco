package cliente;

import conta.Conta;

import java.util.HashSet;
import java.util.Set;

public class ClienteEmpresa extends Cliente {
	private final Set<String> GERENTES_EMPRESA = new HashSet<>(); //Guardara as identificacoes de quem pode acessar a conta
	private final String CNPJ;


	public ClienteEmpresa(String nome, String email, String telefone, Integer idade, Endereco end, Double renda, String cnpj) {
		super(nome, email, telefone, idade, end, renda);
		this.CNPJ = cnpj;
	}

	public String getCnpj() {
		return CNPJ;
	}

	public boolean addGerentes(String identificacao) {
		return GERENTES_EMPRESA.add(identificacao);
	}

	public Boolean verificarGerente(String chave) {
		return GERENTES_EMPRESA.contains(chave);
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

	public String getIdentificacao() {
		return this.CNPJ;
	}

	@Override
	public boolean equals(Cliente outroCliente) {
		if (outroCliente instanceof ClienteEmpresa) {
			return ((ClienteEmpresa) outroCliente).CNPJ.equalsIgnoreCase(this.CNPJ);
		}
		return false;
	}
}
