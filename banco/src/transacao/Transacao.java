package transacao;

import utilsBank.databank.DataBank;

public class Transacao {
	private int valor;
	private PessoaTransacao destino;
	private PessoaTransacao origem;
	private String idPagamento;
	private DataBank dataTransacao;
	private Comprovante comprovante;
	private Pagavel tipoDeTransacao;

	public void gerarComprovante() {
		// TODO: 6/5/2022  
	}

	public void gerarIdPagamento() {
		// TODO: 6/5/2022  
	}
}
