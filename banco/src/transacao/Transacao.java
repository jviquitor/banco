package transacao;

import agencia.Agencia;
import cliente.Cliente;
import conta.Conta;
import interfaceUsuario.dados.DadosTransacao;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.io.Serializable;

public class Transacao implements Serializable {
	private Double valor;
	private String nossoNumero;
	private String idPagamento;
	private Data dataEmissaoTransacao;
	private Cliente destino; //@Lembrando DESTINO EH O DESTINATARIO DO DINHEIRO, QUEM RECEBE
	private Cliente origem; //@Lembrando Origem EH QUEM MANDOU TAL COISA
	private Data dataAgendada;

	public Transacao(DadosTransacao dadosTransacao) {
		this.valor = dadosTransacao.getValor();
		this.nossoNumero = GeracaoAleatoria.gerarNossosNumeros(25);
		this.dataEmissaoTransacao = DataBank.criarData(DataBank.COM_HORA);
		this.idPagamento = Agencia.ID_AGENCIA + Agencia.CODIGO_MOEDA + GeracaoAleatoria.gerarNumeros(4) +
				this.nossoNumero + dataEmissaoTransacao.toString(DataBank.SEM_HORA);
		this.destino = dadosTransacao.getdestino();
		this.origem = dadosTransacao.getorigem();
	}

	public Transacao(DadosTransacao dadosTransacao, Data dataAgendada) {
		Transacao transacao = new Transacao(dadosTransacao);
		transacao.dataAgendada = dataAgendada;
	}

	public void gerarComprovante(Transacao transacao) {
		System.out.println(transacao.toString());
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	@Override
	public String toString() {
		return "Transacao " +
				"valor =" + valor +
				", idPagamento ='" + idPagamento + '\'' +
				", dataEmissaoTransacao =" + dataEmissaoTransacao.toString(DataBank.COM_HORA) +
				", destino =" + destino.toString() +
				", origem =" + origem.toString();
	}

	public Data getDataAgendada() {
		return dataAgendada;
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
}
