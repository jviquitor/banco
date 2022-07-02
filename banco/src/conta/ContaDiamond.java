package conta;

import cartao.CartaoDiamond;
import interfaceUsuario.dados.DadosConta;

public class ContaDiamond extends ContaPremium {
	private static final int SAQUES_MAXIMOS = 15;
	private static final int VALOR_SAQUES_MAXIMO = 100000;
	private static final int FATOR_EMPRESTIMO = 16;

	public ContaDiamond(DadosConta dados) {
		super(dados);
		this.carteira.setLimiteMaximo(CartaoDiamond.LIMITE_MAX);
	}
}
