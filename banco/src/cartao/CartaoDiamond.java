package cartao;

import interfaceUsuario.dados.DadosCartao;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;

import java.io.Serial;

public class CartaoDiamond extends CartaoPremium {
	@Serial
	private static final long serialVersionUID = 12L;
	public final Double LIMITE_MAX = 90000.0;

	public CartaoDiamond(String nomeTitular, DadosCartao dadosCartao) {
		super(nomeTitular, dadosCartao);
		this.tipoCartao = VerificadorEntrada.DIAMOND;
	}

	@Override
	public Double getLimiteMaximo() {
		return this.LIMITE_MAX;
	}
}
