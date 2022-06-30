package interfaceUsuario.dados;

public class DadosConta {
	private final String tipoDaConta;
	private final Double rendaAtual;
	private final boolean cartaoCredito;
	private final boolean cartaoDebito;
	private final boolean debitoAutomatico;

	public DadosConta(String tipoDaConta, Double rendaAtual, boolean cartaoCredito, boolean cartaoDebito, boolean debitoAutomatico) {
		this.tipoDaConta = tipoDaConta;
		this.rendaAtual = rendaAtual;
		this.cartaoCredito = cartaoCredito;
		this.cartaoDebito = cartaoDebito;
		this.debitoAutomatico = debitoAutomatico;
	}

	public boolean hasCartaoCredito() {
		return cartaoCredito;
	}

	public boolean hasCartaoDebit() {
		return cartaoCredito;
	}

	public String getTipoDaConta() {
		return tipoDaConta;
	}

	public Double getRendaAtual() {
		return rendaAtual;
	}

	public boolean isDebitoAutomatico() {
		return debitoAutomatico;
	}
}
