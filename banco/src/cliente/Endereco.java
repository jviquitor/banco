package cliente;

import java.io.Serial;
import java.io.Serializable;

public class Endereco implements Serializable {
	@Serial
	private static final long serialVersionUID = 3L;
	private final Integer CEP;
	private final Integer numeroCasa;
	private String complemento;

	public Endereco(Integer CEP, Integer numeroCasa, String complemento) {
		this.CEP = CEP;
		this.numeroCasa = numeroCasa;
		this.complemento = complemento;
	}

	public Endereco(Integer CEP, Integer numeroCasa) {
		this.CEP = CEP;
		this.numeroCasa = numeroCasa;
		this.complemento = null;
	}

	@Override
	public String toString() {
		String toString = "\n";
		if (CEP != null) {
			toString = toString + "CEP: " + CEP + "\n";
		}
		return toString;
	}

	protected void setComplemento(String complemento) {
		this.complemento = complemento;
	}
}
