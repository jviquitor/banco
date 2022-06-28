package cliente;

import conta.Conta;

import java.io.Serializable;

public abstract class Cliente implements Serializable {
	private String nome;
	private String email;
	private String telefone;
	private Integer idade;
	private Endereco end;
	//private Boolean isOnline;
	private Double renda;
	private Integer quantidadeDeChavesAtuais;

	public Cliente(String nome, String email, String telefone, Integer idade, Endereco end, Double renda) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.idade = idade;
		this.end = end;
		this.renda = renda;
		this.quantidadeDeChavesAtuais = 0;
	}

	public String getNome() {
		return nome;
	}

	public abstract Conta getConta();

	public void setQuantidadeDeChavesAtuais() {
		this.quantidadeDeChavesAtuais++;
	}

	public abstract String getIdentificacao();

	public abstract boolean equals(Cliente outroCliente);
}
