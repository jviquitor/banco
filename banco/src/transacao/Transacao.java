package transacao;

import agencia.Agencia;
import cliente.Cliente;
import conta.Conta;
import interfaceUsuario.dados.DadosTransacao;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.io.Serial;
import java.io.Serializable;

public class Transacao implements Serializable {
	@Serial
	private static final long serialVersionUID = 5L;
	protected final Double valor;
	protected final String nossoNumero;
	protected final String idPagamento;
	private final Cliente destino; //@Lembrando DESTINO, QUEM RECEBE
	protected Data dataEmissaoTransacao;
	protected Cliente origem; //@Lembrando Origem, QUEM MANDOU TAL COISA
	private Data dataAgendada;

	public Transacao(DadosTransacao dadosTransacao) {
		this.valor = dadosTransacao.getValor();
		this.nossoNumero = GeracaoAleatoria.gerarNossosNumeros(25);
		this.dataEmissaoTransacao = DataBank.criarData(DataBank.COM_HORA);
		this.idPagamento = Agencia.ID_AGENCIA + Agencia.CODIGO_MOEDA + GeracaoAleatoria.gerarNumeros(4) +
				this.nossoNumero + dataEmissaoTransacao.toString(new int[]{DataBank.SEM_HORA, DataBank.SEM_BARRA});
		this.destino = dadosTransacao.getdestino();
		this.origem = dadosTransacao.getorigem();
		this.dataAgendada = null;
	}

	public static Transacao criarTransacaoAgendada(DadosTransacao dadosTransacao, Data dataAgendada) {
		Transacao transacao = new Transacao(dadosTransacao);
		transacao.dataAgendada = dataAgendada;
		transacao.dataEmissaoTransacao = DataBank.criarData(DataBank.COM_HORA);
		return transacao;
	}

	public void gerarComprovante() {
		System.out.println(this);
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public boolean equals(Transacao outroT) {
		return this.getNossoNumero().equals(outroT.nossoNumero);
	}

	@Override
	public String toString() {
		String toString = "[TRANSACAO]\n";
		if (valor != null) {
			toString = toString + "VALOR: " + valor + "\n";
		}
		if (idPagamento != null) {
			toString = toString + "IDENTIFICACAO DA TRANSACAO: " + idPagamento + "\n";
		}
		if (origem != null) {
			toString = toString + "ORIGEM DA TRANSACAO:  " + origem + "\n";
		}
		if (destino != null) {
			toString = toString + "DESTINO DA TRANSACAO:  " + destino + "\n";
		}
		if (dataAgendada != null) {
			toString = toString + "DATA AGENDADA PARA A TRANSACAO: " + dataAgendada + "\n";
		}
		if (dataAgendada == null && dataEmissaoTransacao != null) {
			toString = toString + "DATA EMISSAO DA TRANSACAO: " + dataEmissaoTransacao + "\n";
		}
		return toString;
	}

	public Data getDataAgendada() {
		return this.dataAgendada;
	}

	public boolean hasDataAgendada() {
		return this.dataAgendada != null;
	}

	public Data getDataEmissaoTransacao() {
		return this.dataEmissaoTransacao;
	}

	public Conta getContaOrigem() {
		return origem.getConta();
	}

	public Double getValor() {
		return valor;
	}

	public Conta getContaDestino() {
		return destino.getConta();
	}

	public void atualizar() {
		this.dataEmissaoTransacao = this.dataAgendada;
		this.dataAgendada = null;
	}
}
