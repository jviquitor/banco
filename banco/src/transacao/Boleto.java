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
	private static final String NOME_DA_TRANSACAO = "Boleto";
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
		String toString = "[" + NOME_DA_TRANSACAO + "]\n";
		if (valor != null) {
			toString = toString + "VALOR: " + valor + "\n";
		}
		if (nossoNumero != null) {
			toString = toString + "NUMERO DO BOLETO: " + nossoNumero + "\n";
		}
		if (origem != null) {
			toString = toString + "ORIGEM DO BOLETO:  " + origem + "\n";
		}
		if (getDataAgendada() == null && dataEmissaoTransacao != null) {
			toString = toString + "DATA EMISSAO DO BOLETO: " + dataEmissaoTransacao + "\n";
		}
		if (dataVencimento != null) {
			toString = toString + "VENCIMENTO: " + dataVencimento + "\n";
		}
		if (multaPorDias != 0) {
			toString = toString + "MULTA POR DIAS: " + multaPorDias + "\n";
		}
		if (foiPago != null) {
			if (foiPago) {
				toString = toString + "BOLETO PAGO!\n";
				if (idPagamento != null) {
					toString = toString + "IDENTIFICACAO DE PAGAMENTO: " + idPagamento + "\n";
				}
			} else {
				toString = toString + "BOLETO NAO PAGO!\n";
			}

		}
		return toString;
	}

	public void pagar() throws TransacaoException {
		if (Boolean.TRUE.equals(foiPago)) {
			throw new TransacaoException("Esse boleto ja foi pago");
		}
		this.foiPago = true;
	}

}
