package transacao;

import interfaceUsuario.dados.DadosTransacao;
import utilsBank.databank.Data;

public class Boleto implements Pagavel {

	private String nomeDaTransacao = "Boleto";
	private Data dataVencimento;
	private double multaPorDias;

	public Boleto(DadosTransacao dadosTransacao) {
		this.dataVencimento = dadosTransacao.getDataVencimento();
		this.multaPorDias = dadosTransacao.getMultaPorDias();
	}
}
