package transacao;

import transacao.databank.DataBank;

public class Transacao {
	private String nomeDaTransacao; //todo Talvez seja melhor se colocar um nome mais adequado para a variável
	private int valor;
	private Pessoa destino; //pensar em um nome melhor para essa classe pessoa
	private Pessoa origem;
	private String idPagamento;
	private DataBank dataTransacao;

	public void gerarComprovante() {
		// TODO: 6/5/2022  
	}
	public void gerarIdPagamento() {
		// TODO: 6/5/2022  
	}
}
