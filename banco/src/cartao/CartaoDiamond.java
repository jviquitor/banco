package cartao;

import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;

public class CartaoDiamond extends CartaoPremium {
	public static final Double LIMITE_MAX = (60 ^ 4) * 1.0;
	private Double limite;

	public CartaoDiamond(Cliente cliente, DadosCartao dadosCartao) {
		super(cliente, dadosCartao);
		this.tipoCartao = "diamond";
	}

}
