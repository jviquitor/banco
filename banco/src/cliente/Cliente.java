package cliente;

import conta.Conta;

public abstract class Cliente {
	private String nome;
	private String email;
	private int telefone;
	private int idade;
	private Endereco end;
	private boolean isOnline;
	private double renda;
	private int quantidadeDeChavesAtuais; //TODO set quantidadeDeChavesDependendo da quantidade em lista
	private final int QUANTIDADE_DE_CHAVES_MAX = 4;

	public Cliente(String nome, String email, int telefone, int idade, Endereco end, boolean isOnline, double renda) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.idade = idade;
		this.end = end;
		this.isOnline = isOnline;
		this.renda = renda;
	}

	public String getNome() {
		return nome;
	}

	public abstract Conta getConta();
}
