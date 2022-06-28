package interfaceUsuario.dados;

public class DadosConta {
	private String tipoDaConta;
	private Double rendaAtual;
	private boolean cartaoCredito;
	private boolean cartaoDebito;

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
