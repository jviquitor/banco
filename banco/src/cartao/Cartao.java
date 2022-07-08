package cartao;

import agencia.Agencia;
import interfaceUsuario.dados.DadosCartao;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.io.Serial;
import java.io.Serializable;

public abstract class Cartao implements Serializable {
	@Serial
	private static final long serialVersionUID = 6L;
	protected final String numeroCartao;
	protected final String cvc;
	protected final String apelidoCartao;
	protected final Data validade;
	protected final String nomeTitular;
	protected String tipoCartao; // @Lembrando, tipo se refere ao tipo de conta


	protected Cartao(String nomeTitular, DadosCartao dadosCartao) {
		this.numeroCartao = Agencia.ID_AGENCIA + GeracaoAleatoria.gerarNumeroCartao();
		this.cvc = GeracaoAleatoria.gerarNumeros(3);
		this.apelidoCartao = dadosCartao.getApelidoCartao();
		this.nomeTitular = nomeTitular;
		this.validade = DataBank.criarData(DataBank.SEM_HORA);
		this.validade.somar(2, Data.ANO);
	}

	/**
	 * Retorna o limite maximo do Cartao
	 *
	 * @return Double
	 */
	public abstract Double getLimiteMaximo();

	@Override
	public String toString() {
		String toString = "";
		toString = String.format(toString, "[CARTAO DE CREDITO]");

		if (apelidoCartao != null) {
			toString = toString + "APELIDO: " + apelidoCartao + "\n";
		}
		if (numeroCartao != null) {
			toString = toString + "NUMERO DO CARTAO: " + numeroCartao + "\n";
		}
		if (cvc != null) {
			toString = toString + "CVC: " + cvc + "\n";
		}
		if (validade != null) {
			toString = toString + "VALIDADE: " + validade + "\n";
		}
		if (nomeTitular != null) {
			toString = toString + "IDENTIFICACAO: " + nomeTitular + "\n";
		}
		if (tipoCartao != null) {
			toString = toString + "TIPO DO CARTAO: " + tipoCartao + "\n";
		}
		return toString;
	}
}
