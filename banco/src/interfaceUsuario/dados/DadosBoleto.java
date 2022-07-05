package interfaceUsuario.dados;

import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public class DadosBoleto {
	private final String nossoNumero;
	private final String dataVencimento; //@Lembrando sera setada no construtor e sera utilizada com o getters
	private final Integer multaPorDias;
	private final Boolean foiPago;

	public DadosBoleto(String dataVencimento, Integer multaPorDias, boolean foiPago) {
		this.dataVencimento = dataVencimento;
		this.multaPorDias = multaPorDias;
		this.foiPago = foiPago;
		this.nossoNumero = null;
	}

	public DadosBoleto(String nossoNumero) {
		this.nossoNumero = nossoNumero;
		this.dataVencimento = null;
		this.multaPorDias = null;
		this.foiPago = null;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public Integer getMultaPorDias() {
		return multaPorDias;
	}

	public Data getDataVencimento() {
		return DataBank.criarData(dataVencimento, DataBank.SEM_HORA);
	}
}
