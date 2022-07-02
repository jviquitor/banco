package cartao;

import interfaceUsuario.VerificadorEntrada;
import interfaceUsuario.dados.DadosCartao;

public class CartaoDiamond extends CartaoPremium {
	public static final Double LIMITE_MAX = (60 ^ 4) * 1.0;
	private Double limite;

	public CartaoDiamond(String nomeTitular, DadosCartao dadosCartao) {
		super(nomeTitular, dadosCartao);
		this.tipoCartao = VerificadorEntrada.DIAMOND;
	}

}
