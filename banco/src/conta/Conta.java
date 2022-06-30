package conta;

import cartao.*;
import cliente.Cliente;
import conta.exceptions.TipoInvalido;
import historico.Historico;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosTransacao;
import transacao.ChavePix;
import transacao.Transacao;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Conta implements Serializable {

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

	protected Double emprestimo;

	protected Conta() {
		this.idConta = GeracaoAleatoria.gerarIdConta(GeracaoAleatoria.TAMANHO_ID_CONTA);
		this.saldo = 0.0;
		this.dinheiroGuardado = 0.0;
		this.transacoesRealizadas = new ArrayList<>();
		this.transacoesAgendadas = new ArrayList<>();
		this.transacoesRecebidas = new ArrayList<>();
		this.notificacoes = new ArrayList<>();
		this.historico = new Historico();
		this.carteira = new GerenciamentoCartao();
		this.chavesPix = new ArrayList<>();
		this.emprestimo = 0.0;
	}

	public boolean addChavesPix(Cliente cliente, ChavePix chavePix) {
		if (chavesPix.contains(chavePix))
			return false;
		cliente.setQuantidadeDeChavesAtuais();
		return chavesPix.add(chavePix);
	}

	//TODO Interface trata caso o valor seja negativo ou zero, avisando que o mesmo esta inserindo um valor errado
	private void aumentarSaldo(Double valor) {
		this.saldo += valor;
	}

	//TODO Interface trata caso o valor seja maior que o saldo disponivel na conta
	private void diminuirSaldo(Double valor) {
		this.saldo -= valor;
	}

	public void transferir() {
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
		Transacao transacao = new Transacao(dadosTransacao);
		Double valorT = transacao.getValor();
		transacao.getContaCobrador().aumentarSaldo(valorT);
		transacao.getContaPagador().diminuirSaldo(valorT);

	}

	public void transferir(Transacao transacao) {
		Double valorT = transacao.getValor();
		transacao.getContaCobrador().aumentarSaldo(valorT);
		transacao.getContaPagador().diminuirSaldo(valorT);
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

	public void pagarEmprestimo() {
		this.saldo -= this.emprestimo;
		this.emprestimo = 0.0;
	}

	public boolean agendarTransacao() {
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
		Data dataAgendada = InterfaceUsuario.getDataAgendada();
		Transacao transacao = new Transacao(dadosTransacao, dataAgendada);
		transacoesAgendadas.add(transacao);
		return true;
	}

	//TODO: a Interface eh responsavel por checar o CADA DIA esta corretamente para chamar essa funcao
	public Transacao buscarTransacoesAgendadas(Data data) {
		for (Transacao t : transacoesAgendadas) {
			if (Objects.equals(t.getDataAgendada().toString(), data.toString())) {
				return t;
			}
		}
		return null;
	}

	public boolean realizarTransacaoAgendada(Transacao transacao) {
		transferir(transacao);
		return true;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void pagarFatura(Double valor) {
		this.carteira.aumentarLimiteAtual(valor);
		this.saldo -= valor; //TODO por enquanto a fatura sera descontada direto pelo valor do saldo e a interface precisa tratar caso a pessoa tenha saldo
	}

	public Double getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Double valor) {
		this.emprestimo = valor;
	}

	public void aumentarFatura(Double valor) {
		this.carteira.diminuirLimiteAtual(valor);
	}

	public Data getDataDebitoAutomatico() {
		return this.carteira.getDataDebitoAutomatico();
	}

	public boolean getDebitoAutomatico() {
		return this.carteira.isDebitoAutomatico();
	}

	public boolean hasEmprestimo() {
		return this.emprestimo > 0.0;
	}
//	public boolean resetNotificacoes();//Nao é abstrata
//	public abstract boolean renderSaldo();

}
