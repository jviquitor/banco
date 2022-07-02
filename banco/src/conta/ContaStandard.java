package conta;

import cartao.CartaoStandard;

public class ContaStandard extends Conta {
	private static final int SAQUES_MAXIMOS = 5;
	private static final int VALOR_SAQUES_MAXIMO = 1000;

	public ContaStandard() {
		super();
		this.carteira.setLimiteMaximo(CartaoStandard.LIMITE_MAX);
	}

	public boolean renderSaldo() {
		return false;
	}
}
