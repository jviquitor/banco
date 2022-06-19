package transacao;

import interfaceUsuario.dados.DadosTransacao;
import utilsBank.databank.Data;

public class Boleto implements Pagavel {

	private final String nomeDaTransacao = "Boleto";
	private final Data dataVencimento;
	private final double multaPorDias;

	public Boleto(DadosTransacao dadosTransacao) {
		this.dataVencimento = dadosTransacao.getDataVencimento();
		this.multaPorDias = dadosTransacao.getMultaPorDias();
	}
}
