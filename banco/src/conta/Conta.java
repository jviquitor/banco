package conta;

import cartao.*;
import cliente.Cliente;
import conta.exceptions.DadosInvalidosException;
import conta.exceptions.TipoInvalido;
import historico.Historico;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosConta;
import interfaceUsuario.dados.DadosTransacao;
import transacao.ChavePix;
import transacao.Transacao;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Conta {
	protected static final int TAMANHO_ID_CONTA = 4;

	protected String idConta;
	protected Double saldo;
	protected Double dinheiroGuardado;
	protected Double dinheiroDisponivelEmprestimo;

	protected List<Transacao> transacoesRealizadas;
	protected List<Transacao> transacoesAgendadas;
	protected List<Transacao> transacoesRecebidas;
	protected List<Transacao> notificacoes;
	protected Historico historico;

	protected GerenciamentoCartao carteira;

	protected List<ChavePix> chavesPix;

	protected Conta() {
		this.idConta = GeracaoAleatoria.gerarIdConta(Conta.TAMANHO_ID_CONTA);
		this.saldo = 0.0;
		this.dinheiroGuardado = 0.0;
		this.transacoesRealizadas = new ArrayList<>();
		this.transacoesAgendadas = new ArrayList<>();
		this.transacoesRecebidas = new ArrayList<>();
		this.notificacoes = new ArrayList<>();
		this.historico = new Historico();
		this.carteira = new GerenciamentoCartao();
		this.chavesPix = new ArrayList<>();
	}


	public void setChavesPix(List<ChavePix> chavesPix) {
		this.chavesPix = chavesPix;
	}

	public static Conta criarConta() {
		//Sabendo que o cliente está online (a Interface precisa tratar isso)
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

			if (diamond.contains(dadosConta.getTipoDaConta().toLowerCase(Locale.ROOT))) {
				conta = new ContaDiamond(dadosConta);
			} else if (premium.contains(dadosConta.getTipoDaConta().toLowerCase(Locale.ROOT))) {
				conta = new ContaPremium(dadosConta);
			} else if (standard.contains(dadosConta.getTipoDaConta().toLowerCase(Locale.ROOT))) {
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

	private void aumentarSaldo(Double valor) {
		//TODO Interface trata caso o valor seja negativo ou zero, avisando que o mesmo esta inserindo um valor errado
		this.saldo += valor;
	}

	private void diminuirSaldo(Double valor) {
		//TODO Interface trata caso o valor seja maior que o saldo disponivel na conta
		this.saldo -= valor;
	}

	public void transferir() {
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
		Transacao transacao = new Transacao(dadosTransacao);
		Double valorT = transacao.getValor();
		transacao.getCobrador().aumentarSaldo(valorT);
		transacao.getPagador().diminuirSaldo(valorT);

	}

	public void transferir(DadosTransacao dadosTransacao) {
		Transacao transacao = new Transacao(dadosTransacao);
		Double valorT = transacao.getValor();
		transacao.getCobrador().aumentarSaldo(valorT);
		transacao.getPagador().diminuirSaldo(valorT);
	}

	public boolean hasCartao(FuncaoCartao funcaoCartao) {
		for (Cartao cartao : this.carteira.getListaDeCartao()) {
			if (funcaoCartao == cartao.getFuncaoCartao()) {
				return true;
			}
		}
		return false;
	}

	public boolean criarCartao(Cliente cliente, DadosCartao dadosCartao) {
		Cartao cartao;

		if (this.getClass() == ContaStandard.class) {
			cartao = new CartaoStandard(cliente, dadosCartao);
		} else if (this.getClass() == ContaPremium.class) {
			cartao = new CartaoPremium(cliente, dadosCartao);
		} else if (this.getClass() == ContaDiamond.class) {
			cartao = new CartaoDiamond(cliente, dadosCartao);
		} else {
			throw new TipoInvalido("Tipo do cartao invalido.");
		}

		return this.carteira.adicionarNovoCartao(cartao);
	}

	public void pagar() {
		transferir();
	}

	public void depositar() {
		transferir();
	}

	//TODO perguntar a vania se tem problema essa funcao ser meio que simbolica (explicar que eh por causa do async etc)
	public boolean agendarTransacao() {
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
		InterfaceUsuario.setDataAgendada();
		Data data = DataBank.criaData(InterfaceUsuario.getDataAgendada());

		if (data.equals(DataBank.criaData())) {
			transferir(dadosTransacao);
			return true;
		}
		return false;
	}

	public void pagarFatura(Double valor) {
		this.carteira.aumentarLimiteAtual(valor);
		this.saldo -= valor; //TODO por enquanto a fatura sera descontada direto pelo valor do saldo e a interface precisa tratar caso a pessoa tenha saldo
	}

	public void aumentarFatura(Double valor) {
		this.carteira.diminuirLimiteAtual(valor);
	}

	public Data getDataDebitoAutomatic() {
		return this.carteira.getDataDebitoAutomatico();
	}

	public boolean getDebitoAutomatic() {
		return this.carteira.isDebitoAutomatico();
	}
//	public boolean resetNotificacoes();//Nao é abstrata
//	public abstract boolean renderSaldo();

}
