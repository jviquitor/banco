package interfaceUsuario.dados;

public class DadosChavesPix {
	public static final String TELEFONE = "telefone";
	public static final String CHAVE_ALEATORIA = "chave_aleatoria";
	public static final String EMAIL = "email";
	public static final String IDENTIFICACAO = "identificacao";
	private final String telefone;
	private final String email;
	private final String tipoChave;
	private final boolean chaveAleatoria;

	public DadosChavesPix(String telefone, String email, String tipoChave, boolean chaveAleatoria) {
		this.telefone = telefone;
		this.email = email;
		this.tipoChave = tipoChave;
		this.chaveAleatoria = chaveAleatoria;
	}

	public String getTipoChave() {
		return tipoChave;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEmail() {
		return email;
	}

}
