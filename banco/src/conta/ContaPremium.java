package conta;

import cartao.CartaoPremium;
import interfaceUsuario.dados.DadosConta;

public class ContaPremium extends ContaStandard {
	private final int SAQUES_MAXIMOS = 10;
	private final int VALOR_SAQUES_MAXIMO = 10000;
	private final int FATOR_EMPRESTIMO = 8;

	public ContaPremium(DadosConta dados) {
		super(dados);
		this.dinheiroDisponivelEmprestimo = dados.getRendaAtual() * FATOR_EMPRESTIMO;
		this.carteira.setLimiteMaximo(CartaoPremium.LIMITE_MAX);
	}
}
