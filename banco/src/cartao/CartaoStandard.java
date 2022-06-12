package cartao;

public class CartaoStandard extends Cartao {
	private int limite;
	public static final int LIMITE_MAX = 10 ^ 4;

	public CartaoStandard(Cliente cliente, DadosCartao dadosCartao) {
		super(cliente, dadosCartao);
		this.tipoCartao = "standard";
	}
}
