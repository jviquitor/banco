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
	protected final Double VALOR;
	protected final String NOSSO_NUMERO;
	protected final String ID_PAGAMENTO;
	private final Cliente DESTINO; //@Lembrando DESTINO, QUEM RECEBE
	protected Data dataEmissaoTransacao;
	protected Cliente origem; //@Lembrando Origem, QUEM MANDOU TAL COISA
	private Data dataAgendada;

	public Transacao(DadosTransacao dadosTransacao) {
		this.VALOR = dadosTransacao.getValor();
		this.NOSSO_NUMERO = GeracaoAleatoria.gerarNossosNumeros(25);
		this.dataEmissaoTransacao = DataBank.criarData(DataBank.COM_HORA);
		this.ID_PAGAMENTO = Agencia.ID_AGENCIA + Agencia.CODIGO_MOEDA + GeracaoAleatoria.gerarNumeros(4) +
				this.NOSSO_NUMERO + dataEmissaoTransacao.toString(new int[]{DataBank.SEM_HORA, DataBank.SEM_BARRA});
		this.DESTINO = dadosTransacao.getdestino();
		this.origem = dadosTransacao.getorigem();
		this.dataAgendada = null;
	}

	public static Transacao criarTransacaoAgendada(DadosTransacao dadosTransacao, Data dataAgendada) {
		Transacao transacao = new Transacao(dadosTransacao);
		transacao.dataAgendada = dataAgendada;
		transacao.dataEmissaoTransacao = DataBank.criarData(DataBank.COM_HORA);
		return transacao;
	}

	public void atualizar() {
		this.dataEmissaoTransacao = this.dataAgendada;
		this.dataAgendada = null;
	}

	public void gerarComprovante() {
		System.out.println(this);
	}

	public boolean equals(Transacao outroT) {
		return this.getNossoNumero().equals(outroT.NOSSO_NUMERO);
	}

	@Override
	public String toString() {
		String toString = "[TRANSACAO]\n";
		if (VALOR != null) {
			toString = toString + "VALOR: " + VALOR + "\n";
		}
		if (ID_PAGAMENTO != null) {
			toString = toString + "IDENTIFICACAO DA TRANSACAO: " + ID_PAGAMENTO + "\n";
		}
		if (origem != null) {
			toString = toString + "ORIGEM DA TRANSACAO:  " + origem + "\n";
		}
		if (DESTINO != null) {
			toString = toString + "DESTINO DA TRANSACAO:  " + DESTINO + "\n";
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
		return VALOR;
	}

	public String getNossoNumero() {
		return NOSSO_NUMERO;
	}

	public Conta getContaDestino() {
		return DESTINO.getConta();
	}


}
