package transacoes;

import transacoes.databank.DataBank;

public class Boleto extends Transacoes {

	private String nomeDaTransacao = "Boleto";
	private DataBank dataVencimento;
	private int multaPorDias;
	private int nossoNumero;
	private int linhaDigitavel;

}
