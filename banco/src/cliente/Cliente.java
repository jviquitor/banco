package cliente;

import cartao.Fatura;
import cliente.exceptions.LoginException;
import conta.Conta;
import conta.ContaDiamond;
import conta.ContaPremium;
import conta.ContaStandard;
import conta.exceptions.DadosInvalidosException;
import conta.exceptions.TipoInvalido;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.MenuUsuario;
import interfaceUsuario.VerificadorEntrada;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;
import interfaceUsuario.exceptions.ValorInvalido;

import java.io.Serial;
import java.io.Serializable;

@SuppressWarnings("SameReturnValue")
public abstract class Cliente implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	protected final String nome;
	protected final String email;
	protected final String telefone;
	protected final Integer idade;
	protected final Endereco end;
	private final String senha;
	protected Conta conta;
	//private Boolean isOnline;
	protected Double renda;

	public Cliente(String nome, String email, String telefone, Integer idade, Endereco end, String senha) throws ValorInvalido {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.idade = idade;
		this.end = end;
		this.conta = criarConta();
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public Conta getConta() {
		return this.conta;
	}

	public abstract String getIdentificacao();

	@SuppressWarnings("unused")
	public abstract boolean equals(Cliente outroCliente);

	public String allInfos() {
		String toString = "[CLIENTE]\n";
		if (nome != null) {
			toString = toString + "NOME: " + nome + "\n";
		}
		if (getIdentificacao() != null) {
			toString = toString + "IDENTIFICACAO: " + getIdentificacao() + "\n";
		}
		return getString(toString);
	}

	public boolean pagarFatura(Double valor) {
		Fatura fatura = new Fatura(valor, this);
		this.getConta().getHistorico().addFaturas(fatura);
		this.getConta().pagarFatura(valor);
		return true;
	}

	public void aumentarFatura(Double valor) {
		this.getConta().aumentarFatura(valor);
	}

	public void setChavesPix() {
		this.getConta().getChavesPix().setIdentificacao(this.getIdentificacao());
	}

	protected String getString(String toString) {
		if (email != null) {
			toString = toString + "EMAIL: " + email + "\n";
		}
		if (end != null) {
			toString = toString + "ENDERECO: " + end;
		}

		return toString;
	}

	public Conta criarConta() throws ValorInvalido {
		this.renda = MenuUsuario.menuCriacaoConta();
		DadosConta dadosConta = InterfaceUsuario.getDadosConta();
		DadosCartao dadosCartao = InterfaceUsuario.getDadosCartao();
		Conta conta;

		if (dadosConta == null || dadosCartao == null) {
			throw new DadosInvalidosException("Dados inseridos incorretamente, Por favor, logue novamente!");
		} else {
			if (dadosConta.getTipoDaConta().equalsIgnoreCase(VerificadorEntrada.DIAMOND)) {
				conta = new ContaDiamond();
			} else if (dadosConta.getTipoDaConta().equalsIgnoreCase(VerificadorEntrada.PREMIUM)) {
				conta = new ContaPremium();
			} else if (dadosConta.getTipoDaConta().equalsIgnoreCase(VerificadorEntrada.STANDARD)) {
				conta = new ContaStandard();
			} else {
				throw new TipoInvalido("Por favor, escolha um tipo de conta valido");
			}
			conta.criarCartao(nome, dadosCartao);
		}
		return conta;
	}

	public boolean verificarSenha(String senha) throws LoginException {
		if (!this.senha.equals(senha)) {
			throw new LoginException("Senha incorreta");
		}
		return true;
	}
}
