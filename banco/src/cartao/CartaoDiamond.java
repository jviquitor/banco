package cartao;

import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;

public class CartaoDiamond extends CartaoPremium {
	private int limite;
	public static final int LIMITE_MAX = 60 ^ 4;

	public CartaoDiamond(Cliente cliente, DadosCartao dadosCartao) {
		super(cliente, dadosCartao);
		this.tipoCartao = "diamond";
	}

}
