package transacao;

import interfaceUsuario.dados.DadosBoleto;
import interfaceUsuario.dados.DadosTransacao;
import transacao.exceptions.TransacaoException;
import utilsBank.databank.Data;

import java.io.Serial;
import java.io.Serializable;

public class Boleto extends Transacao implements Serializable {
	@Serial
	private static final long serialVersionUID = 10L;
	private final String NOME_DA_TRANSACAO = "boleto";
	private final Data dataVencimento;
	private final double multaPorDias;
	private Boolean foiPago;

	public Boleto(DadosTransacao dadosTransacao, DadosBoleto dadosBoleto) {
		super(dadosTransacao);
		this.foiPago = dadosBoleto.getFoiPago();
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
		return "[BOLETO'] {" +
				", [DATA DE VENCIMENTO] = " + dataVencimento +
				", [MULTA POR DIAS NAO PAGO] = " + multaPorDias +
				", [BOLETO PAGO]" + foiPago +
				'}';
	}

	public void pagar() throws TransacaoException {
		if (Boolean.TRUE.equals(foiPago)) {
			throw new TransacaoException("Esse boleto ja foi pago");
		}
		this.foiPago = true;
	}
}
