package transacao;

import utilsBank.databank.Data;

public class Boleto extends Transacao implements Pagavel {

	private String nomeDaTransacao = "Boleto";
	private Data dataVencimento;
	private int multaPorDias;
	private int nossoNumero;
	private int linhaDigitavel;

}
