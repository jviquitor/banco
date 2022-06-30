package cliente;

import cliente.exceptions.LoginException;
import conta.Conta;
import conta.ContaDiamond;
import conta.ContaPremium;
import conta.ContaStandard;
import conta.exceptions.DadosInvalidosException;
import conta.exceptions.TipoInvalido;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.MenuUsuario;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;

import java.io.Serializable;

public abstract class Cliente implements Serializable {
	private final String nome;
	private final String email;
	private final String telefone;
	private final Integer idade;
	private final Endereco end;
	//private Boolean isOnline;
	private final Double renda;
	protected Conta conta;
	private String senha;
	private Integer quantidadeDeChavesAtuais;

	public Cliente(String nome, String email, String telefone, Integer idade, Endereco end, Double renda, String senha) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.idade = idade;
		this.end = end;
		this.renda = renda;
		this.quantidadeDeChavesAtuais = 0;
		this.conta = this.criarConta();
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public abstract Conta getConta();

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public void setQuantidadeDeChavesAtuais() {
		this.quantidadeDeChavesAtuais++;
	}

	public abstract String getIdentificacao();

	public abstract boolean equals(Cliente outroCliente);

	public Conta criarConta() {
		DadosConta dadosConta = InterfaceUsuario.getDadosConta();
		DadosCartao dadosCartao = InterfaceUsuario.getDadosCartao();
		Conta conta;

		if (dadosConta == null || dadosCartao == null) {
			throw new DadosInvalidosException("Dados inseridos incorretamente, Por favor, logue novamente!");
		} else {
			if (dadosConta.getTipoDaConta().equalsIgnoreCase(MenuUsuario.DIAMOND)) {
				conta = new ContaDiamond(dadosConta);
			} else if (dadosConta.getTipoDaConta().equalsIgnoreCase(MenuUsuario.PREMIUM)) {
				conta = new ContaPremium(dadosConta);
			} else if (dadosConta.getTipoDaConta().equalsIgnoreCase(MenuUsuario.STANDARD)) {
				conta = new ContaStandard(dadosConta);
			} else {
				throw new TipoInvalido("Por favor, escolha um tipo de conta valido");
			}
			if (dadosConta.hasCartaoCredito()) {
				conta.criarCartao(this, dadosCartao);
			} else if (dadosConta.hasCartaoDebit()) {
				conta.criarCartao(this, dadosCartao);
			}
		}
		return conta;
	}

	public void verificarSenha(String senha) throws LoginException {
		if (!this.senha.equals(senha)) {
			throw new LoginException("Senha incorreta");
		}
	}
}
