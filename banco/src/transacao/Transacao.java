package transacao;

import agencia.Agencia;
import cliente.Cliente;
import conta.Conta;
import interfaceUsuario.dados.DadosTransacao;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public class Transacao {
	private Double valor;
	private String nossoNumero;
	private String idPagamento;
	private Data dataEmissaoTransacao;
	private Cliente cobrador; //@Lembrando Cobrador cobrara o dinheiro de alguem recebe o dinheiro
	private Cliente pagador; //@Lembrando Pagador pagara o dinheiro cobrado pelo cobrador LEMBRANDO QUE SE O PAGADOR FOR NULO DEVE SER UM BOLETO O TIPO DE TRANSACAO
	private Data dataAgendada;
	private Pagavel tipoDeTransacao;

	public Transacao(DadosTransacao dadosTransacao) {
		this.valor = dadosTransacao.getValor();
		this.nossoNumero = GeracaoAleatoria.gerarNossosNumeros(25);
		this.dataEmissaoTransacao = DataBank.criarData(DataBank.COM_HORA);
		this.idPagamento = Agencia.ID_AGENCIA + Agencia.CODIGO_MOEDA + GeracaoAleatoria.gerarNumeros(4) +
				this.nossoNumero + dadosTransacao.getDataVencimentoString() + dataEmissaoTransacao.toString(DataBank.SEM_HORA);
		this.cobrador = dadosTransacao.getCobrador();
		this.pagador = dadosTransacao.getPagador();
		this.tipoDeTransacao = dadosTransacao.getTipoDaTransacao();
	}

	public Transacao(DadosTransacao dadosTransacao, Data dataAgendada) {
		Transacao transacao = new Transacao(dadosTransacao);
		transacao.dataAgendada = dataAgendada;
	}

	public void gerarComprovante(Transacao transacao) {
		System.out.println(transacao.toString());
	}

	@Override
	public String toString() {
		return "Transacao " +
				"valor =" + valor +
				", idPagamento ='" + idPagamento + '\'' +
				", dataEmissaoTransacao =" + dataEmissaoTransacao.toString(DataBank.COM_HORA) +
				", Cobrador =" + cobrador.toString() +
				", Pagador =" + pagador.toString() +
				", tipoDeTransacao=" + tipoDeTransacao.toString();
	}

	public Data getDataAgendada() {
		return dataAgendada;
	}

	public Conta getContaPagador() {
		return pagador.getConta();
	}

	public Double getValor() {
		return valor;
	}

	public Conta getContaCobrador() {
		return cobrador.getConta();
	}
}
