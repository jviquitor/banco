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
	protected final Integer cvc;
	protected final String apelidoCartao;
	protected final Data validade;
	protected final FuncaoCartao funcaoCartao; // @Lembrando Se refere a ser debito ou credito
	protected String nomeTitular;
	protected String tipoCartao; // @Lembrando, tipo se refere ao tipo de conta


	protected Cartao(String nomeTitular, DadosCartao dadosCartao) {
		this.numeroCartao = Agencia.ID_AGENCIA + GeracaoAleatoria.gerarNumeroCartao();
		this.cvc = Integer.parseInt(GeracaoAleatoria.gerarNumeros(3));
		this.apelidoCartao = dadosCartao.getApelidoCartao();
		this.nomeTitular = nomeTitular;
		//Possível implementação de método para somar datas (na classe Data)
		this.validade = DataBank.criarData(DataBank.SEM_HORA);
		this.funcaoCartao = dadosCartao.getFuncaoCartao();
	}

	@Override
	public String toString() {
		String toString = "";
		toString = String.format(toString, "[CARTAO DE %s]", this.funcaoCartao.toString());

		if (apelidoCartao != null) {
			toString = toString + "APELIDO: " + apelidoCartao + "\n";
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
