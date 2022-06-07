package conta;

import cartao.CartaoDiamond;
import interfaceUsuario.dados.Dados;

public class ContaDiamond extends ContaPremium {
	private final int SAQUES_MAXIMOS = 15;
	private final int VALOR_SAQUES_MAXIMO = 100000;
	private final int FATOR_EMPRESTIMO =  16;

	public ContaDiamond (Dados dados) {
		super(dados);
		this.dinheiroDisponivelEmprestimo = dados.getRendaAtual() * FATOR_EMPRESTIMO;
		this.limiteMaximo = CartaoDiamond.LIMITE_MAX;
	}
}
