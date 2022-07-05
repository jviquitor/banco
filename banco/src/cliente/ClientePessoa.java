package cliente;

public class ClientePessoa extends Cliente {
	private final String CPF;

	public ClientePessoa(String nome, String email, String telefone, Integer idade, Endereco end, String cpf, String senha) {
		super(nome, email, telefone, idade, end, senha);
		this.CPF = cpf;
	}

	private String clienteAllInfos() {
		String toString = "[CLIENTE]\n";
		if (nome != null) {
			toString = toString + "NOME: " + nome + "\n";
		}
		if (CPF != null) {
			toString = toString + "CPF: " + CPF + "\n";
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

