package interfaceUsuario.dados;

import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public class DadosBoleto {
	private final String DATA_VENCIMENTO; //@Lembrando sera setada no construtor e sera utilizada com o getters
	private final Integer MULTA_DIAS;
	private final Boolean FOI_PAGO;

	public DadosBoleto(String dataVencimento, Integer multaPorDias, boolean foiPago) {
		this.DATA_VENCIMENTO = dataVencimento;
		this.MULTA_DIAS = multaPorDias;
		this.FOI_PAGO = foiPago;
	}


	public Integer getMultaPorDias() {
		return MULTA_DIAS;
	}

	public Data getDataVencimento() {
		assert DATA_VENCIMENTO != null;
		return DataBank.criarData(DATA_VENCIMENTO, DataBank.SEM_HORA);
	}

	public boolean getFoiPago() {
		return Boolean.TRUE.equals(this.FOI_PAGO);
	}
}
