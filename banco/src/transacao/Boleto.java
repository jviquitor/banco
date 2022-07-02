package transacao;

import interfaceUsuario.dados.DadosBoleto;
import interfaceUsuario.dados.DadosTransacao;
import utilsBank.databank.Data;

import java.io.Serializable;

public class Boleto extends Transacao implements Serializable {

	private final String nomeDaTransacao = "boleto";
	private final Data dataVencimento;
	private final double multaPorDias;
	private Boolean foiPago;

	public Boleto(DadosTransacao dadosTransacao, DadosBoleto dadosBoleto, boolean foiPago) {
		super(dadosTransacao);
		this.foiPago = foiPago;
		this.dataVencimento = dadosBoleto.getDataVencimento();
		this.multaPorDias = dadosBoleto.getMultaPorDias();
	}

	public Data getDataVencimento() {
		return dataVencimento;
	}

	public double getMultaPorDias() {
		return multaPorDias;
	}

	@Override
	public String toString() {
		return nomeDaTransacao;
	}

	public void setFoiPago(boolean b) {
		this.foiPago = b;
	}
}
