package cliente;

import conta.Conta;
import conta.ContaDiamond;
import conta.ContaPremium;
import conta.ContaStandard;
import conta.exceptions.DadosInvalidosException;
import conta.exceptions.TipoInvalido;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Cliente implements Serializable {
	private String nome;
	private String email;
	private String telefone;
	private Integer idade;
	private Endereco end;
	//private Boolean isOnline;
	private Double renda;
	private Integer quantidadeDeChavesAtuais;

	protected Conta conta;

	public Cliente(String nome, String email, String telefone, Integer idade, Endereco end, Double renda) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.idade = idade;
		this.end = end;
		this.renda = renda;
		this.quantidadeDeChavesAtuais = 0;
		this.conta = this.criarConta();
	}

	public String getNome() {
		return nome;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public abstract Conta getConta();

	public void setQuantidadeDeChavesAtuais() {
		this.quantidadeDeChavesAtuais++;
	}

	public abstract String getIdentificacao();

	public abstract boolean equals(Cliente outroCliente);

	private Conta criarConta() {
		DadosConta dadosConta = InterfaceUsuario.getDadosConta();
		DadosCartao dadosCartao = InterfaceUsuario.getDadosCartao();
		Cliente cliente = InterfaceUsuario.getClienteAtual();
		Conta conta;

		if (dadosConta == null || dadosCartao == null || cliente == null) {
			throw new DadosInvalidosException("Dados inseridos incorretamente, Por favor, logue novamente!");
		} else {
			List<String> standard = new ArrayList<>(Arrays.asList("standard", "normal", "conta de pobre", "qualquer conta", "basica"));
			List<String> premium = new ArrayList<>(Arrays.asList("premium", "plus", "conta mediana"));
			List<String> diamond = new ArrayList<>(Arrays.asList("diamond", "a melhor", "com mais beneficios", "conta de rico"));

			if (diamond.contains(dadosConta.getTipoDaConta().toLowerCase())) {
				conta = new ContaDiamond(dadosConta);
			} else if (premium.contains(dadosConta.getTipoDaConta().toLowerCase())) {
				conta = new ContaPremium(dadosConta);
			} else if (standard.contains(dadosConta.getTipoDaConta().toLowerCase())) {
				conta = new ContaStandard(dadosConta);
			} else {
				throw new TipoInvalido("Por favor, escolha um tipo de conta valido");
			}
			if (dadosConta.hasCartaoCredito()) {
				conta.criarCartao(cliente, dadosCartao);
			} else if (dadosConta.hasCartaoDebit()) {
				conta.criarCartao(cliente, dadosCartao);
			}
		}
		return conta;
	}
}
