package utilsBank.arquivo.Exception;

public class EscritaArquivoException extends Exception {
	public EscritaArquivoException() {
		super("Erro de escrita");
	}

	public EscritaArquivoException(String msg) {
		super(msg);
	}
}
