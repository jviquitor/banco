package cartao;

public abstract class Cartao {
	private int numeroCartao; // TODO: 6/5/2022 Lembrando, os primeiros 4 dígitos se referem a agência e os outros seram aleatorios
	private int cvc;
	private String apelidoCartao;
	private String nomeTitular;
	private String validade;
	private TipoCartao tipoCartao; // TODO: 6/5/2022: Talvez, esses tipos podem ficar num ENUM. Lembrando, tipo se refere ao tipo de conta
	private String funcaoCartao; // TODO: 6/5/2022 Se refere a ser debito ou credito

	public abstract String getTipoCartao();
}
