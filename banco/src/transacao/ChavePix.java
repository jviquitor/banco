package transacao;

import interfaceUsuario.dados.DadosChavesPix;
import utilsBank.GeracaoAleatoria;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class ChavePix implements Serializable {
	private HashMap<String, String> telefone;
	private HashMap<String, String> chaveAleatoria; //gerar
	private HashMap<String, String> email;
	private HashMap<String, String> identificacao; // CPF ou CNPJ

	private ChavePix(HashMap<String, String> telefone, HashMap<String, String> chaveAleatoria, HashMap<String, String> email, HashMap<String, String> identificacao) {
		this.telefone = telefone;
		this.chaveAleatoria = chaveAleatoria;
		this.email = email;
		this.identificacao = identificacao;
	}

	public static ChavePix criarChavePix(String chave, DadosChavesPix dadosChavesPix) {
		HashMap<String, String> hashMap = new HashMap<>();
		switch (chave) {
			case DadosChavesPix.TELEFONE -> {
				hashMap.put(chave, dadosChavesPix.getTelefone());
				return new ChavePix(hashMap, null, null, null);
			}
			case DadosChavesPix.EMAIL -> {
				hashMap.put(chave, dadosChavesPix.getEmail());
				return new ChavePix(null, null, hashMap, null);
			}
			case DadosChavesPix.IDENTIFICACAO -> {
				hashMap.put(chave, dadosChavesPix.getIdentificacao());
				return new ChavePix(null, null, null, hashMap);
			}
		}
		throw new RuntimeException("Erro ao criar a chave pix, verifique os valores e tente novamente");
	}

	public static ChavePix criarChavePix(String chave) {
		if (Objects.equals(chave, DadosChavesPix.CHAVE_ALEATORIA)) {
			HashMap<String, String> hashMap = new HashMap<>();
			String value = GeracaoAleatoria.gerarChaveAleatoria(GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA);
			hashMap.put(chave, value);
			return new ChavePix(null, hashMap, null, null);
		}
		throw new RuntimeException("Erro ao criar a chave pix, verifique os valores e tente novamente");
	}

	public boolean mudarAdicionarChavePix(String chave, DadosChavesPix dadosChavesPix) {
		HashMap<String, String> hashMap = new HashMap<>();
		switch (chave) {
			case DadosChavesPix.TELEFONE -> {
				hashMap.put(chave, dadosChavesPix.getTelefone());
				this.telefone = hashMap;
			}
			case DadosChavesPix.EMAIL -> {
				hashMap.put(chave, dadosChavesPix.getEmail());
				this.email = hashMap;
			}
			case DadosChavesPix.IDENTIFICACAO -> {
				hashMap.put(chave, dadosChavesPix.getIdentificacao());
				this.identificacao = hashMap;
			}
			case DadosChavesPix.CHAVE_ALEATORIA -> {
				String value = GeracaoAleatoria.gerarChaveAleatoria(GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA);
				hashMap.put(chave, value);
				this.chaveAleatoria = hashMap;
			}
		}
		return true;
	}


	public HashMap<String, String> getTelefone() {
		return telefone;
	}

	public HashMap<String, String> getChaveAleatoria() {
		return chaveAleatoria;
	}

	public HashMap<String, String> getEmail() {
		return email;
	}

	public HashMap<String, String> getIdentificacao() {
		return identificacao;
	}


}
