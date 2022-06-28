package cartao;

import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;

public class CartaoStandard extends Cartao {
	public static final Double LIMITE_MAX = (10 ^ 4) * 1.0;
	private Double limite;

	public CartaoStandard(Cliente cliente, DadosCartao dadosCartao) {
		super(cliente, dadosCartao);
		this.tipoCartao = "standard";
	}
}
