package cliente;

import conta.Conta;

import java.util.HashSet;
import java.util.Set;

public class ClienteEmpresa extends Cliente {
	private final Set<String> GERENTES_EMPRESA = new HashSet<>(); //Guardara as identificacoes de quem pode acessar a conta
	private final String CNPJ;


	public ClienteEmpresa(String nome, String email, String telefone, Integer idade, Endereco end, String cnpj, String senha) {
		super(nome, email, telefone, idade, end, senha);
		this.CNPJ = cnpj;
	}

	//TODO MENU DO CLIENTE EMPRESA ISA @AMANHA
	public String getCnpj() {
		return CNPJ;
	}

	public boolean addGerentes(String identificacao) {
		return GERENTES_EMPRESA.add(identificacao);
	}

	public Boolean verificarGerente(String chave) {
		return GERENTES_EMPRESA.contains(chave);
	}

	private String clienteAllInfos() {
		String toString = "[CLIENTE]\n";
		if (nome != null) {
			toString = toString + "NOME: " + nome + "\n";
		}
		if (CNPJ != null) {
			toString = toString + "CPF: " + CNPJ + "\n";
		}
		return getString(toString);
	}

	@Override
	public String toString() {
		String toString = "[CLIENTE]\n";
		if (nome != null) {
			toString = toString + "NOME: " + nome + "\n";
		}
		return getString(toString);
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
