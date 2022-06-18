package conta;

import cartao.CartaoStandard;
import interfaceUsuario.dados.DadosConta;

public class ContaStandard extends Conta {
	private static final int SAQUES_MAXIMOS = 5;
	private static final int VALOR_SAQUES_MAXIMO = 1000;
	private static final int FATOR_EMPRESTIMO =  4;

	public boolean renderSaldo() {
		return false;
	}

	public ContaStandard(DadosConta dados) {
		super();
		this.dinheiroDisponivelEmprestimo = dados.getRendaAtual() * FATOR_EMPRESTIMO;
		this.carteira.setLimiteMaximo(CartaoStandard.LIMITE_MAX);
	}
}
