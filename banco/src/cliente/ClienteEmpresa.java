package cliente;

import cliente.exceptions.GerenteJaExistenteException;
import cliente.exceptions.GerenteNaoEncontradoException;
import conta.Conta;
import interfaceUsuario.exceptions.ValorInvalido;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

public class ClienteEmpresa extends Cliente {
    @Serial
    private static final long serialVersionUID = 18L;
    private final Set<String> GERENTES_EMPRESA = new HashSet<>(); //Guardara as identificacoes de quem pode acessar a conta
    private final String CNPJ;


    public ClienteEmpresa(String nome, String email, String telefone, Integer idade, Endereco end, String cnpj, String senha) throws ValorInvalido {
        super(nome, email, telefone, idade, end, senha);
        this.CNPJ = cnpj;
    }

    public boolean addGerentes(String identificacao) throws GerenteJaExistenteException {
        if (!GERENTES_EMPRESA.contains(identificacao)) {
            return GERENTES_EMPRESA.add(identificacao);
        }
        throw new GerenteJaExistenteException("O gerente ja existe");
    }

    public boolean removerGerentes(String identificacao) throws GerenteNaoEncontradoException {
        if (GERENTES_EMPRESA.contains(identificacao)) {
            return GERENTES_EMPRESA.remove(identificacao);
        }
        throw new GerenteNaoEncontradoException("O gerente nao foi encontrado, logo nao foi possivel a remocao!");
    }

    public boolean verificarGerente(String chave) {
        return GERENTES_EMPRESA.contains(chave);
    }

    @Override
    public String toString() {
        String toString = "[CLIENTE]\n";
        if (NOME != null) {
            toString = toString + "NOME: " + NOME + "\n";
        }
        return getString(toString);
    }

    @Override
    public Conta getConta() {
        return this.conta;
    }

    public String getIdentificacao() {
        return this.CNPJ;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean equals(Cliente outroCliente) {
        if (outroCliente instanceof ClienteEmpresa) {
            return ((ClienteEmpresa) outroCliente).CNPJ.equalsIgnoreCase(this.CNPJ);
        }
        return false;
    }
}
