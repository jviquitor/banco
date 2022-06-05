package transacao;

import transacao.databank.DataBank;

public class Boleto extends Transacao {

	private String nomeDaTransacao = "Boleto";
	private DataBank dataVencimento;
	private int multaPorDias;
	private int nossoNumero;
	private int linhaDigitavel;

}
