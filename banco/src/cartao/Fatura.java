package cartao;

import agencia.Agencia;
import cliente.Cliente;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.io.Serial;
import java.io.Serializable;

public class Fatura implements Serializable {
	@Serial
	private static final long serialVersionUID = 18L;
	private final Data DATA_PAGAMENTO;
	private final Double VALOR;
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

	@SuppressWarnings("unused")
	public boolean equals(Fatura outraFatura) {
		return this.DATA_PAGAMENTO.equals(outraFatura.DATA_PAGAMENTO);
	}

	@Override
	public String toString() {
		String toString = "[COMPROVANTE DE PAGAMENTO]\t";
		String DOCUMENTO = "Fatura do Cartao BIC";
		toString = toString + "" + DOCUMENTO + "\t";
		if (DATA_PAGAMENTO != null) {
			toString = toString + "" + DATA_PAGAMENTO + "\n";
		}
		if (VALOR != null) {
			toString = toString + "VALOR >>>> " + VALOR + "\n";
		}
		if (NOME_PAGADOR != null) {
			toString = toString + "PAGADOR >>>> " + NOME_PAGADOR + "\n";
		}
		if (NUMERO_AGENCIA != null) {
			toString = toString + "AGENCIA >>>> " + NUMERO_AGENCIA + "\n";
		}
		if (CONTA != null) {
			toString = toString + "CONTA >>>> " + CONTA + "\n";
		}
		return toString;
	}

}
