package cartao;

import interfaceUsuario.VerificadorEntrada;
import interfaceUsuario.dados.DadosCartao;

public class CartaoStandard extends Cartao {
	public static final Double LIMITE_MAX = (10 ^ 4) * 1.0;
	private Double limite;

	public CartaoStandard(String nomeTitular, DadosCartao dadosCartao) {
		super(nomeTitular, dadosCartao);
		this.tipoCartao = VerificadorEntrada.STANDARD;
	}
}
