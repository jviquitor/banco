package transacao;

import interfaceUsuario.dados.DadosTransacao;

public class Pix implements Pagavel {
	private String nomeDaTransacao;

	public Pix(DadosTransacao dadosTransacao) { //TODO Perguntar para a vania se acha errado deixar essa classe pix/boleto publica,
		// ela ta sendo obrigada a ser publica porque o dadosTransacao chama ela com base na transacao que o usuario escolheu
		this.nomeDaTransacao = "Pix";

	}
}
