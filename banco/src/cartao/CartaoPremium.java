package cartao;

import interfaceUsuario.VerificadorEntrada;
import interfaceUsuario.dados.DadosCartao;

import java.io.Serial;

public class CartaoPremium extends CartaoStandard {
	@Serial
	private static final long serialVersionUID = 13L;
	public final Double LIMITE_MAX = Math.pow(30, 4);

	public CartaoPremium(String nomeTitular, DadosCartao dadosCartao) {
		super(nomeTitular, dadosCartao);
		this.tipoCartao = VerificadorEntrada.PREMIUM;
	}

	@Override
	public Double getLimiteMaximo() {
		return this.LIMITE_MAX;
	}
}
