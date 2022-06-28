package utilsBank.arquivo.Exception;

public class LeituraArquivoException extends Exception {
	public LeituraArquivoException() {
        super("Erro de leitura");
    }

	public LeituraArquivoException(String msg) {
		super(msg);
	}
}
