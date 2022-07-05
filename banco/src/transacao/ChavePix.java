package transacao;

import interfaceUsuario.dados.DadosChavesPix;
import utilsBank.GeracaoAleatoria;

import java.io.Serial;
import java.io.Serializable;

public class ChavePix implements Serializable {
	@Serial
	private static final long serialVersionUID = 11L;
	private String telefone;
	private String chaveAleatoria; //gerar
	private String email;
	private String identificacao; // CPF ou CNPJ

	public ChavePix(String telefone, String chaveAleatoria, String email, String identificacao) {
		this.telefone = telefone;
		this.chaveAleatoria = chaveAleatoria;
		this.email = email;
		this.identificacao = identificacao;
	}

	public boolean mudarAdicionarChavePix(String tipoDechave, DadosChavesPix dadosChavesPix) {
		switch (tipoDechave) {
			case DadosChavesPix.TELEFONE:
				this.telefone = dadosChavesPix.getTelefone();
				break;
			case DadosChavesPix.EMAIL:
				this.email = dadosChavesPix.getEmail();
				break;
			case DadosChavesPix.CHAVE_ALEATORIA:
				this.chaveAleatoria = GeracaoAleatoria.gerarChaveAleatoria(GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA);
				break;
		}
		return true;
	}

	/*public ChavePix criarChavePix(String tipoChave, DadosChavesPix dadosChavesPix) {
		switch (tipoChave) {
			case DadosChavesPix.TELEFONE:
				return new ChavePix(dadosChavesPix.getTelefone(), null, null, null);
			case DadosChavesPix.EMAIL:
				return new ChavePix(null, null, dadosChavesPix.getEmail(), null);
		}
		throw new RuntimeException("Erro ao criar a chave pix, verifique os valores e tente novamente");
	}*/

	/*public ChavePix criarChavePix(String chave) {
		if (chave.equals(DadosChavesPix.CHAVE_ALEATORIA)) {
			String value = GeracaoAleatoria.gerarChaveAleatoria(GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA);
			return new ChavePix(null, value, null, null);
		}
		throw new RuntimeException("Erro ao criar a chave pix, verifique os valores e tente novamente");
	}*/

	@Override
	public String toString() {
		String toString = "[CHAVES PIX]\n";
		if (identificacao != null) {
			toString = toString + "IDENTIFICACAO: " + identificacao + "\n";
		}
		if (telefone != null) {
			toString = toString + "TELEFONE: " + telefone + "\n";
		}
		if (email != null) {
			toString = toString + "EMAIL: " + email + "\n";
		}
		if (chaveAleatoria != null) {
			toString = toString + "CHAVE ALEATORIA: " + chaveAleatoria + "\n";
		}
		return toString;
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

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}
}
