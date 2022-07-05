package conta;

import agencia.Agencia;
import cartao.*;
import conta.exceptions.TipoInvalido;
import conta.exceptions.TransacaoNaoRealizadaException;
import funcionalidades.exceptions.EmprestimoException;
import historico.Historico;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.dados.DadosChavesPix;
import interfaceUsuario.dados.DadosTransacao;
import transacao.Boleto;
import transacao.ChavePix;
import transacao.Transacao;
import transacao.exceptions.TransacaoException;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Conta implements Serializable {
	@Serial
	private static final long serialVersionUID = 2L;
	protected final String idConta;
	protected final List<Transacao> transacoesRealizadas;
	protected final List<Transacao> transacoesAgendadas;
	protected final List<Transacao> transacoesRecebidas;
	protected final List<Transacao> notificacoes;
	protected final Historico historico;
	protected final GerenciamentoCartao carteira;
	protected final ChavePix chavesPix;
	protected Double saldo;
	protected Double dinheiroGuardado;
	protected Double emprestimo;
	protected Double parcelaEmprestimo;

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
		this.emprestimo = 0.0;
		this.parcelaEmprestimo = 0.0;
		this.chavesPix = new ChavePix(null, null, null, null);
	}

	public ChavePix getChavesPix() {
		return chavesPix;
	}

	public boolean modificarChavePix() {
		DadosChavesPix dadosChavePix = InterfaceUsuario.getDadosChavePix();
		return chavesPix.mudarAdicionarChavePix(dadosChavePix.getTipoChave(), dadosChavePix);
	}

	public void aumentarSaldo(Double valor) {
		this.saldo += valor;
	}

	private void diminuirSaldo(Double valor) {
		this.saldo -= valor;
	}

	public void setDinheiroGuardado(Double valor) {
		this.saldo -= valor;
		this.dinheiroGuardado = valor;
	}

	public Transacao transferir() throws TransacaoException {
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
		Transacao transacao = new Transacao(dadosTransacao);
		Double valorT = transacao.getValor();

		if (addTransacaoRealizada(transacao)) {
			transacao.getContaDestino().addHistorico(transacao);
			transacao.getContaDestino().aumentarSaldo(valorT);
			transacao.getContaOrigem().diminuirSaldo(valorT);
			return transacao;
		}
		throw new TransacaoNaoRealizadaException("Ocorreu algum erro ao realizar a Transacao. Tente novamente");
	}

	public boolean addTransacaoRealizada(Transacao t) throws TransacaoException {
		if (!transacoesRealizadas.contains(t)) {
			this.historico.addTransacao(t);
			transacoesRealizadas.add(t);
			return true;
		}
		return false;
	}

	public boolean addTransacaoAgendadas(Transacao t) throws TransacaoException {
		if (!transacoesAgendadas.contains(t)) {
			this.historico.addTransacao(t);
			transacoesAgendadas.add(t);
			return true;
		}
		return false;
	}

	private void transferir(Transacao transacao) {
		Double valorT = transacao.getValor();
		transacao.getContaDestino().aumentarSaldo(valorT);
		transacao.getContaOrigem().diminuirSaldo(valorT);
	}

	public boolean equals(Conta outraConta) {
		return this.idConta.equals(outraConta.idConta);
	}

	//TODO MEXER NO GERENCIAMENTO DE CARTAO PARA FAZER O MENU DO USUARIO DO CARTAO (PAGAR FATURA)
	public boolean hasCartao(FuncaoCartao funcaoCartao) {
		for (Cartao cartao : this.carteira.getListaDeCartao()) {
			if (funcaoCartao == cartao.getFuncaoCartao()) {
				return true;
			}
		}
		return false;
	}

	public void criarCartao(String nomeTitular, DadosCartao dadosCartao) {
		Cartao cartao;

		if (this.getClass() == ContaStandard.class) {
			cartao = new CartaoStandard(nomeTitular, dadosCartao);
		} else if (this.getClass() == ContaPremium.class) {
			cartao = new CartaoPremium(nomeTitular, dadosCartao);
		} else if (this.getClass() == ContaDiamond.class) {
			cartao = new CartaoDiamond(nomeTitular, dadosCartao);
		} else {
			throw new TipoInvalido("Tipo do cartao invalido.");
		}

		this.carteira.adicionarNovoCartao(cartao);
	}

	public void pagarBoleto(Boleto boleto) throws TransacaoException {
		int intervalo = DataBank.criarData(DataBank.SEM_HORA).calcularIntervalo(boleto.getDataVencimento());
		Double valorTratado = (intervalo < 0) ? boleto.getMultaPorDias() * -intervalo : boleto.getMultaPorDias();
		valorTratado += boleto.getValor();
		if (this.saldo < valorTratado) {
			throw new TransacaoException("Saldo insuficiente");
		}
		boleto.pagar();
		this.diminuirSaldo(valorTratado);
		boleto.getContaDestino().aumentarSaldo(valorTratado);
		boleto.getContaDestino().addHistorico(boleto);
	}

	public Transacao depositar() throws TransacaoException {
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
		Transacao transacao = new Transacao(dadosTransacao);
		Double valorT = transacao.getValor();

		if (addTransacaoRealizada(transacao)) {
			if (transacao.getContaDestino().equals(transacao.getContaOrigem())) {
				transacao.getContaDestino().aumentarSaldo(valorT);
			} else {
				transacao.getContaDestino().aumentarSaldo(valorT);
				transacao.getContaOrigem().diminuirSaldo(valorT);
			}
			transacao.getContaDestino().addHistorico(transacao);
			return transacao;
		}
		throw new TransacaoNaoRealizadaException("Ocorreu algum erro ao realizar a Transacao. Tente novamente");
	}

	public void pagarEmprestimo() throws EmprestimoException {
		if (this.emprestimo <= this.saldo) {
			Agencia.getInstance().addSaldo(this.emprestimo);
			this.saldo -= this.emprestimo;
			this.emprestimo = 0.0;
			this.parcelaEmprestimo = 0.0;
		} else {
			throw new EmprestimoException("Saldo insuficiente");
		}
	}

	public void pagarParcelaEmprestimo() throws EmprestimoException {
		double parcela;
		if (this.emprestimo < this.parcelaEmprestimo) {
			parcela = this.emprestimo;
		} else {
			parcela = this.parcelaEmprestimo;
		}
		if (parcela <= this.saldo) {
			Agencia.getInstance().addSaldo(parcela);
			this.saldo -= parcela;
			this.emprestimo -= parcela;
			if (this.emprestimo == 0) {
				this.parcelaEmprestimo = 0.0;
			}
		} else {
			throw new EmprestimoException("Saldo insuficiente");
		}
	}

	public GerenciamentoCartao getCarteira() {
		return this.carteira;
	}

	public Transacao agendarTransacao() throws TransacaoException {
		DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
		Data dataAgendada = InterfaceUsuario.getDataAgendada();
		Transacao transacao = new Transacao(dadosTransacao, dataAgendada);
		if (addTransacaoAgendadas(transacao)) {
			return transacao;
		}
		throw new TransacaoNaoRealizadaException("Ocorreu algum erro ao realizar a Transacao. Tente novamente");
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

	public Double getParcelaEmprestimo() {
		return this.parcelaEmprestimo;
	}

	public void setParcelaEmprestimo(Double valor) {
		this.parcelaEmprestimo = valor;
	}

	public void aumentarFatura(Double valor) {
		this.carteira.diminuirLimiteAtual(valor);
	}

	public Data getDataDebitoAutomatico() {
		return this.carteira.getDataDebitoAutomatico();
	}

	@Override
	public String toString() {
		String toString = "[CONTA] \n";
		if (idConta != null) {
			toString = toString + "ID_CONTA: " + idConta + "\n";
		}
		if (saldo != null) {
			toString = toString + "SALDO " + saldo + "\n";
		}
		if (dinheiroGuardado != null) {
			toString = toString + "DINHEIRO GUARDADO" + dinheiroGuardado + "\n";
		}
		if (emprestimo != null) {
			toString = toString + "EMPRESTIMO " + emprestimo + "\n";
		}
		if (chavesPix != null) {
			toString = toString + "" + chavesPix + "\n";
		}
		toString = toString + "© TODOS OS DIREITOS RESERVADOS AO BIC  " + "\n";
		return toString;
	}

	public boolean getDebitoAutomatico() {
		return this.carteira.isDebitoAutomatico();
	}

	public boolean hasEmprestimo() {
		return this.emprestimo > 0.0;
	}

	public ArrayList<Transacao> getHistorico() {
		return this.historico.getTransacoes();
	}

	public void addHistorico(Transacao transacao) throws TransacaoException {
		this.historico.addTransacao(transacao);
	}

//Todo	public boolean resetNotificacoes();//Nao é abstrata
//	public abstract boolean renderSaldo();

}
