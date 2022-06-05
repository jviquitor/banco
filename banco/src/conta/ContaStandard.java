package conta;

import transacao.Transacao;

public class ContaStandard extends Conta {
	private final int saquesMaximos = 5;
	private final int valorSaquesMaximo = 1000;

	@Override
	public boolean transferir(Transacao formaDeTransacao) {
		return false;
	}

	@Override
	public boolean pagar(Transacao formaDeTransacao) {
		return false;
	}

	@Override
	public boolean depositar(Transacao formaDeTransacao) {
		return false;
	}

	@Override
	public boolean agendarTransacao() {
		return false;
	}

	@Override
	public boolean resetNotificacoes() {
		return false;
	}

	@Override
	public boolean renderSaldo() {
		return false;
	}
}
