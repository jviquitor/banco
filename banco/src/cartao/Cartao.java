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
	protected String numeroCartao;
	protected Integer cvc;
	protected String apelidoCartao;
	protected String nomeTitular;
	protected Data validade;
	protected String tipoCartao; // @Lembrando, tipo se refere ao tipo de conta
	protected FuncaoCartao funcaoCartao; // @Lembrando Se refere a ser debito ou credito

	protected Cartao(String nomeTitular, DadosCartao dadosCartao) {
		this.numeroCartao = Agencia.ID_AGENCIA + GeracaoAleatoria.gerarNumeroCartao();
		this.cvc = Integer.parseInt(GeracaoAleatoria.gerarNumeros(3));
		this.apelidoCartao = dadosCartao.getApelidoCartao();
		this.nomeTitular = nomeTitular;
		//Possível implementação de método para somar datas (na classe Data)
		this.validade = DataBank.criarData(DataBank.SEM_HORA);
		this.funcaoCartao = dadosCartao.getFuncaoCartao();
	}

	public String getTipoCartao() {
		return this.tipoCartao;
	}

	public FuncaoCartao getFuncaoCartao() {
		return funcaoCartao;
	}
}
