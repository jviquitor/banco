package cartao;

import interfaceUsuario.VerificadorEntrada;
import interfaceUsuario.dados.DadosCartao;

import java.io.Serial;

public class CartaoStandard extends Cartao {
	@Serial
	private static final long serialVersionUID = 14L;
	public final Double LIMITE_MAX = Math.pow(10, 4);

	public CartaoStandard(String nomeTitular, DadosCartao dadosCartao) {
		super(nomeTitular, dadosCartao);
		this.tipoCartao = VerificadorEntrada.STANDARD;
	}

	@Override
	public Double getLimiteMaximo() {
		return this.LIMITE_MAX;
	}
}
