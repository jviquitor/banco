package utilsBank.arquivo.Exception;

public class LeituraArquivoException extends RuntimeException {
	public LeituraArquivoException() {
		super("Erro de leitura");
	}

	public LeituraArquivoException(String msg) {
		super(msg);
	}
}
