package transacao;

import interfaceUsuario.dados.DadosTransacao;

public class Pix implements Pagavel {
	private final String nomeDaTransacao;

	public Pix(DadosTransacao dadosTransacao) {
		this.nomeDaTransacao = "pix";
	}

	@Override
	public String toString() {
		return nomeDaTransacao;
	}
}
