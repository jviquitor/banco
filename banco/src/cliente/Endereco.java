package cliente;

import java.io.Serializable;

public class Endereco implements Serializable {
	private int CEP;
	private String rua;
	private int numeroRua;

	public Endereco(int CEP, String rua, int numeroRua) {
		this.CEP = CEP;
		this.rua = rua;
		this.numeroRua = numeroRua;
	}
}
