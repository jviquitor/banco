package cartao;

import agencia.Agencia;
import cliente.Cliente;
import interfaceUsuario.dados.DadosCartao;
import utilsBank.GeracaoAleatoria;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public abstract class Cartao {
	protected String numeroCartao;
	protected int cvc;
	protected String apelidoCartao;
	protected String nomeTitular;
	protected Data validade;
	protected String tipoCartao; // TODO: 6/5/2022: Lembrando, tipo se refere ao tipo de conta
	protected FuncaoCartao funcaoCartao; // TODO: 6/5/2022 Se refere a ser debito ou credito

	protected Cartao(Cliente cliente, DadosCartao dadosCartao) {
		this.numeroCartao = Agencia.ID_AGENCIA + GeracaoAleatoria.gerarNumeroCartao();
		this.cvc = Integer.parseInt(GeracaoAleatoria.gerarNumeros(3));
		this.apelidoCartao = dadosCartao.getApelidoCartao();
		this.nomeTitular = cliente.getNome();
		Data dataAtual = DataBank.criaData();
		String dataString = String.format("%02d/%02d/%04d %02d:%02d:%02d", dataAtual.getDia(), dataAtual.getMes(), dataAtual.getAno() + 1, dataAtual.getHora(), dataAtual.getMinuto(), dataAtual.getSegundo());
		this.validade = DataBank.criaData(dataString);
		this.funcaoCartao = dadosCartao.getFuncaoCartao();
	}

	public String getTipoCartao() {
		return this.tipoCartao;
	}

}
