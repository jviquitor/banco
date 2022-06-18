package cartao;

import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;

public class CartaoPremium extends CartaoStandard {
	public static final Double LIMITE_MAX = (30 ^ 4) * 1.0;
	private Double limite;

	public CartaoPremium(Cliente cliente, DadosCartao dadosCartao) {
		super(cliente, dadosCartao);
		this.tipoCartao = "premium";
	}
}
