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
                return true;
            case DadosChavesPix.EMAIL:
                this.email = dadosChavesPix.getEmail();
                return true;
            case DadosChavesPix.CHAVE_ALEATORIA:
                this.chaveAleatoria = GeracaoAleatoria.gerarChaveAleatoria(GeracaoAleatoria.TAMANHO_CHAVE_ALEATORIA);
                return true;
        }
        return false;
    }

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
