package conta;

import cartao.CartaoPremium;
import interfaceUsuario.dados.DadosConta;

public class ContaPremium extends ContaStandard {
	private static final int SAQUES_MAXIMOS = 10;
	private static final int VALOR_SAQUES_MAXIMO = 10000;
	private static final int FATOR_EMPRESTIMO = 8;

	public ContaPremium(DadosConta dados) {
		super(dados);
		this.carteira.setLimiteMaximo(CartaoPremium.LIMITE_MAX);
	}
}
