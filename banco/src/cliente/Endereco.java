package cliente;

import java.io.Serializable;

public class Endereco implements Serializable {
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
	}
}
