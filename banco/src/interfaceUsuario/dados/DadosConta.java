package interfaceUsuario.dados;

public class DadosConta {
	private final String tipoDaConta;
	private final boolean cartaoCredito;
	private final boolean debitoAutomatico;

	public DadosConta(String tipoDaConta, boolean cartaoCredito, boolean debitoAutomatico) {
		this.tipoDaConta = tipoDaConta;
		this.cartaoCredito = cartaoCredito;
		this.debitoAutomatico = debitoAutomatico;
	}

	public boolean hasCartaoCredito() {
		return cartaoCredito;
	}

	public boolean hasCartaoDebito() {
		return !cartaoCredito;
	}

	public String getTipoDaConta() {
		return tipoDaConta;
	}

	public boolean isDebitoAutomatico() {
		return debitoAutomatico;
	}
}
