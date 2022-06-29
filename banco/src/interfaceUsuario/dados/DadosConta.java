package interfaceUsuario.dados;

public class DadosConta {
	private String tipoDaConta;
	private Double rendaAtual;
	private boolean cartaoCredito;
	private boolean cartaoDebito;

	public DadosConta(String tipoDaConta, Double rendaAtual, boolean cartaoCredito, boolean cartaoDebito, boolean debitoAutomatico) {
		this.tipoDaConta = tipoDaConta;
		this.rendaAtual = rendaAtual;
		this.cartaoCredito = cartaoCredito;
		this.cartaoDebito = cartaoDebito;
		this.debitoAutomatico = debitoAutomatico;
	}

	private boolean debitoAutomatico;

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
