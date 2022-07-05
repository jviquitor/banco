package conta.exceptions;

public class TransacaoNaoRealizadaException extends IllegalArgumentException {
	public TransacaoNaoRealizadaException(String message) {
		super(message);
	}
}
