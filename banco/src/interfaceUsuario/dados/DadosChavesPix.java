package interfaceUsuario.dados;

public class DadosChavesPix {
	public static final String TELEFONE = "telefone";
	public static final String CHAVE_ALEATORIA = "chave_aleatoria";
	public static final String EMAIL = "email";
	public static final String IDENTIFICACAO = "identificacao";
	private final String NUMERO_TELEFONE;
	private final String EMAIL_USUARIO;
	private final String TIPO_CHAVE;

	public DadosChavesPix(String telefone, String email, String tipoChave) {
		this.NUMERO_TELEFONE = telefone;
		this.EMAIL_USUARIO = email;
		this.TIPO_CHAVE = tipoChave;
	}

	public String getTipoChave() {
		return TIPO_CHAVE;
	}

	public String getTelefone() {
		return NUMERO_TELEFONE;
	}

	public String getEmail() {
		return EMAIL_USUARIO;
	}

}
