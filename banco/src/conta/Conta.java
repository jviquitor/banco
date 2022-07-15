package conta;

import agencia.Agencia;
import cartao.*;
import cliente.Cliente;
import conta.exceptions.TipoInvalido;
import conta.exceptions.TransacaoNaoRealizadaException;
import funcionalidades.exceptions.EmprestimoException;
import historico.Historico;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.MenuUsuario;
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

public class Conta implements Serializable {
	@Serial
	private static final long serialVersionUID = 2L;
	protected final String idConta;
	protected final List<Transacao> transacoesRealizadas;
	protected final List<Transacao> transacoesAgendadas;
	protected final Historico historico;
	protected final GerenciamentoCartao carteira;
	protected final ChavePix chavesPix;
	protected Historico notificacoes;
	protected Double saldo;
	protected Double saldoTotalDepositado;
	protected Double dinheiroGuardado;
	protected Double emprestimo;
	protected Double parcelaEmprestimo;

    protected Conta() {
        this.ID_CONTA = GeracaoAleatoria.gerarIdConta(GeracaoAleatoria.TAMANHO_ID_CONTA);
        this.saldo = 0.0;
        this.dinheiroGuardado = 0.0;
        this.saldoTotalDepositado = 0.0;
        this.TRANSACOES_REALIZADAS = new ArrayList<>();
        this.TRANSACOES_AGENDADAS = new ArrayList<>();
        this.notificacoes = new Historico();
        this.HISTORICO = new Historico();
        this.CARTEIRA = new GerenciamentoCartao();
        this.emprestimo = 0.0;
        this.parcelaEmprestimo = 0.0;
        this.CHAVES_PIX = new ChavePix(null, null, null, null);
    }

    public Double getSaldoTotalDepositado() {
        return saldoTotalDepositado;
    }

    public void setSaldoTotalDepositado(Double saldoTotalDepositado) {
        this.saldoTotalDepositado = saldoTotalDepositado;
    }

	public String getIdConta() {
		return idConta;
	}

    public ChavePix getCHAVES_PIX() {
        return CHAVES_PIX;
    }

    public boolean modificarChavePix() {
        DadosChavesPix dadosChavePix = InterfaceUsuario.getDadosChavePix();
        return CHAVES_PIX.mudarAdicionarChavePix(dadosChavePix.getTipoChave(), dadosChavePix);
    }

    public void aumentarSaldo(Double valor) {
        this.saldo += valor;
    }

    private void diminuirSaldo(Double valor) {
        this.saldo -= valor;
    }

    public void setDinheiroGuardado(Double valor, String opcao) {
        if (opcao.equals(MenuUsuario.GUARDAR)) {
            this.saldo -= valor;
            this.dinheiroGuardado += valor;
        } else if (opcao.equals(MenuUsuario.RESGATAR)) {
            this.saldo += valor;
            this.dinheiroGuardado -= valor;
        }

    }

    public Double getDinheiroGuardado() {
        return dinheiroGuardado;
    }

    public Transacao transferir() throws TransacaoException {
        DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
        Transacao transacao = new Transacao(dadosTransacao);
        Double valorT = transacao.getValor();

        if (addTransacaoRealizada(transacao)) {
            transacao.getContaDestino().aumentarSaldo(valorT);
            transacao.getContaOrigem().diminuirSaldo(valorT);
            adicionarHistoricoNotificacao(transacao);
            return transacao;
        }
        throw new TransacaoNaoRealizadaException("Ocorreu algum erro ao realizar a Transacao. Tente novamente");
    }

    public boolean addTransacaoRealizada(Transacao t) {
        if (!TRANSACOES_REALIZADAS.contains(t)) {
            TRANSACOES_REALIZADAS.add(t);
            return true;
        }
        return false;
    }

    public boolean addTransacaoAgendadas(Transacao t) throws TransacaoException {
        if (!TRANSACOES_AGENDADAS.contains(t)) {
            this.HISTORICO.addTransacao(t);
            TRANSACOES_AGENDADAS.add(t);
            return true;
        }
        return false;
    }

    private void transferir(Transacao transacao) throws TransacaoException {
        Double valorT = transacao.getValor();
        transacao.getContaDestino().aumentarSaldo(valorT);
        transacao.getContaOrigem().diminuirSaldo(valorT);
        transacao.getContaDestino().addHistorico(transacao);
        transacao.getContaDestino().addNotificacao(transacao);
    }

    private void adicionarHistoricoNotificacao(Transacao transacao) throws TransacaoException {
        transacao.getContaOrigem().addHistorico(transacao);
        transacao.getContaDestino().addHistorico(transacao);
        transacao.getContaDestino().addNotificacao(transacao);
    }

	public boolean equals(Conta outraConta) {
		return this.idConta.equals(outraConta.idConta);
	}

    public void mostrarCartoes() {
        for (Cartao cartao : this.CARTEIRA.getListaDeCartao()) {
            System.out.println(cartao);
        }
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

        this.CARTEIRA.adicionarNovoCartao(cartao);
    }

    public void pagarBoleto(Boleto boleto, Cliente origem) throws TransacaoException {
        int intervalo = DataBank.criarData(DataBank.SEM_HORA).calcularIntervalo(boleto.getDataVencimento());
        Double valorTratado = (intervalo < 0) ? boleto.getMultaPorDias() * -intervalo : boleto.getMultaPorDias();
        valorTratado += boleto.getValor();
        if (this.saldo < valorTratado) {
            throw new TransacaoException("Saldo insuficiente");
        }
        boleto.pagar(origem);
        this.diminuirSaldo(valorTratado);
        boleto.getContaDestino().aumentarSaldo(valorTratado);
        adicionarHistoricoNotificacao(boleto);
    }

    public Transacao depositar() throws TransacaoException {
        DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
        Transacao transacao = new Transacao(dadosTransacao);
        Double valorT = transacao.getValor();

        if (addTransacaoRealizada(transacao)) {
            this.setSaldoTotalDepositado(valorT);
            if (transacao.getContaDestino().equals(transacao.getContaOrigem())) {
                transacao.getContaDestino().aumentarSaldo(valorT);
                transacao.getContaOrigem().addHistorico(transacao);
            } else {
                adicionarHistoricoNotificacao(transacao);
                transacao.getContaDestino().aumentarSaldo(valorT);
                transacao.getContaOrigem().diminuirSaldo(valorT);
            }
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

    public GerenciamentoCartao getCARTEIRA() {
        return this.CARTEIRA;
    }

    public Transacao agendarTransacao() throws TransacaoException {
        DadosTransacao dadosTransacao = InterfaceUsuario.getDadosTransacao();
        Data dataAgendada = dadosTransacao.getDataAgendada();
        Transacao transacao = Transacao.criarTransacaoAgendada(dadosTransacao, dataAgendada);
        if (addTransacaoAgendadas(transacao)) {
            Agencia.getInstance().addTransacao(transacao);
            return transacao;
        }
        throw new TransacaoNaoRealizadaException("Ocorreu algum erro ao realizar a Transacao. Tente novamente");
    }

    public Transacao realizarTransacaoAgendada(Transacao transacao) throws TransacaoException {
        if (transacao.getDataAgendada() == null) {
            throw new TransacaoException("Essa transacao ja foi realizada");
        }
        transferir(transacao);
        transacao.atualizar();
        return transacao;
    }

    public void apagarTransacaoAgendada(Transacao transacao) throws TransacaoException {
        try {
            this.TRANSACOES_AGENDADAS.remove(transacao);
        } catch (Exception ex) {
            throw new TransacaoException("Transacao nao encontrada");
        }
    }

    public Double getSaldo() {
        return saldo;
    }

    public void pagarFatura(Double valor) {
        this.CARTEIRA.aumentarLimiteAtual(valor);
        this.saldo -= valor;
    }

    public boolean aumentarFatura(Double valor) {
        this.CARTEIRA.diminuirLimiteAtual(valor);
        return true;
    }

    public Double getEmprestimo() {
        return emprestimo;
    }

    public void criarEmprestimo(Double valor, Integer parcelas) {
        this.emprestimo = valor;
        this.parcelaEmprestimo = valor / parcelas;
        this.aumentarSaldo(valor);
    }

    public Double getParcelaEmprestimo() {
        return this.parcelaEmprestimo;
    }

    public int getDataDebitoAutomatico() {
        return this.CARTEIRA.getDataDebitoAutomatico();
    }

    @Override
    public String toString() {
        String toString = "[CONTA] \n";
        if (ID_CONTA != null) {
            toString = toString + "ID_CONTA: " + ID_CONTA + "\n";
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
        if (CHAVES_PIX != null) {
            toString = toString + "" + CHAVES_PIX + "\n";
        }
        toString = toString + "© TODOS OS DIREITOS RESERVADOS AO BIC  " + "\n";
        return toString;
    }

    public boolean getDebitoAutomatico() {
        return this.CARTEIRA.isDebitoAutomatico();
    }

    public boolean hasEmprestimo() {
        return this.emprestimo > 0.0;
    }

    public Historico getHISTORICO() {
        return HISTORICO;
    }

    public void addHistorico(Transacao transacao) throws TransacaoException {
        this.HISTORICO.addTransacao(transacao);
    }

    public void resetarNotificacoes() {
        this.notificacoes = new Historico();
    }

    public void addNotificacao(Transacao transacao) throws TransacaoException {
        this.notificacoes.addTransacao(transacao);
    }

    public void addNotificacao(Fatura fatura) {
        this.notificacoes.addFaturas(fatura);
    }

    public boolean hasNotificacoes() {
        return this.notificacoes.getTransacoes().size() > 0;
    }

    public ArrayList<Transacao> getNotificacoes() {
        return this.notificacoes.getTransacoes();
    }

}
