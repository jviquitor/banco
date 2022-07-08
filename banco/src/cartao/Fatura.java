package cartao;

import agencia.Agencia;
import cliente.Cliente;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

public class Fatura {
	private final Data DATA_PAGAMENTO;
	private final Double VALOR;
	private final String DOCUMENTO = "Fatura do Cartao BIC";
	private final String NOME_PAGADOR;
	private final String NUMERO_AGENCIA;
	private final String CONTA;

	public Fatura(Double VALOR, Cliente cliente) {
		this.DATA_PAGAMENTO = DataBank.criarData(DataBank.COM_HORA);
		this.VALOR = VALOR;
		this.NOME_PAGADOR = cliente.getNome();
		this.NUMERO_AGENCIA = Agencia.ID_AGENCIA;
		this.CONTA = cliente.getConta().getIdConta();
	}

	public boolean equals(Fatura outraFatura) {
		return this.DATA_PAGAMENTO.equals(outraFatura.DATA_PAGAMENTO);
	}

	@Override
	public String toString() {
		String toString = "[COMPROVANTE DE PAGAMENTO]\t";
		toString = toString + "" + DOCUMENTO + "\t";
		if (DATA_PAGAMENTO != null) {
			toString = toString + "" + DATA_PAGAMENTO + "\n";
		}
		if (VALOR != null) {
			toString = toString + "VALOR >>>>" + VALOR + "\n";
		}
		if (NOME_PAGADOR != null) {
			toString = toString + "PAGADOR >>>>" + NOME_PAGADOR + "\n";
		}
		if (NUMERO_AGENCIA != null) {
			toString = toString + "AGENCIA >>>>" + NUMERO_AGENCIA + "\n";
		}
		if (CONTA != null) {
			toString = toString + "CONTA >>>>" + CONTA + "\n";
		}
		return toString;
	}

}
