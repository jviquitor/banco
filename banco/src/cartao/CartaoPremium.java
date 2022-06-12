package cartao;

import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;

public class CartaoPremium extends CartaoStandard {
	private int limite;
	public static final int LIMITE_MAX = 30 ^ 4;

	public CartaoPremium(Cliente cliente, DadosCartao dadosCartao) {
		super(cliente, dadosCartao);
		this.tipoCartao = "premium";
	}
}
