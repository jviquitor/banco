package transacao;

import interfaceUsuario.dados.DadosChavesPix;
import utilsBank.GeracaoAleatoria;

import java.io.Serializable;

public class ChavePix implements Serializable {
	private String telefone;
	private String chaveAleatoria; //gerar
	private String email;
	private String identificacao; // CPF ou CNPJ

	private ChavePix(String telefone, String chaveAleatoria, String email, String identificacao) {
		this.telefone = telefone;
		this.chaveAleatoria = chaveAleatoria;
		this.email = email;
		this.identificacao = identificacao;
	}

	public static ChavePix criarChavePix(String tipoChave, DadosChavesPix dadosChavesPix) {
		switch (tipoChave) {
			case DadosChavesPix.TELEFONE -> {
				return new ChavePix(dadosChavesPix.getTelefone(), null, null, null);
			}
			case DadosChavesPix.EMAIL -> {
				return new ChavePix(null, null, dadosChavesPix.getEmail(), null);
			}
			case DadosChavesPix.IDENTIFICACAO -> {
				return new ChavePix(null, null, null, dadosChavesPix.getIdentificacao());
			}
		}
		throw new RuntimeException("Erro ao criar a chave pix, verifique os valores e tente novamente");
	}

	public static ChavePix criarChavePix(String chave) {
		if (chave.equals(DadosChavesPix.CHAVE_ALEATORIA)) {
			String value = GeracaoAleatoria.gerarChaveAleatoria(GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA);
			return new ChavePix(null, value, null, null);
		}
		throw new RuntimeException("Erro ao criar a chave pix, verifique os valores e tente novamente");
	}

	public boolean mudarAdicionarChavePix(String chave, DadosChavesPix dadosChavesPix) {
		switch (chave) {
			case DadosChavesPix.TELEFONE -> {
				this.telefone = dadosChavesPix.getTelefone();
			}
			case DadosChavesPix.EMAIL -> {
				this.email = dadosChavesPix.getEmail();
			}
			case DadosChavesPix.IDENTIFICACAO -> {
				this.identificacao = dadosChavesPix.getIdentificacao();
			}
			case DadosChavesPix.CHAVE_ALEATORIA -> {
				String value = GeracaoAleatoria.gerarChaveAleatoria(GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA);
				this.chaveAleatoria = value;
			}
		}
		return true;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getChaveAleatoria() {
		return chaveAleatoria;
	}

	public String getEmail() {
		return email;
	}

	public String getIdentificacao() {
		return identificacao;
	}
}
