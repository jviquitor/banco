package cliente;

import interfaceUsuario.exceptions.ValorInvalido;

import java.io.Serial;

public class ClientePessoa extends Cliente {
    @Serial
    private static final long serialVersionUID = 19L;
    private final String CPF;

    public ClientePessoa(String nome, String email, String telefone, Integer idade, Endereco end, String cpf, String senha) throws ValorInvalido {
        super(nome, email, telefone, idade, end, senha);
        this.CPF = cpf;
    }

// --Commented out by Inspection START (7/8/2022 5:27 PM):
//	private String clienteAllInfos() {
//		String toString = "[CLIENTE]\n";
//		if (nome != null) {
//			toString = toString + "NOME: " + nome + "\n";
//		}
//		if (CPF != null) {
//			toString = toString + "CPF: " + CPF + "\n";
//		}
//		return getString(toString);
//	}
// --Commented out by Inspection STOP (7/8/2022 5:27 PM)

    @Override
    public String toString() {
        String toString = "[CLIENTE]\n";
        if (nome != null) {
            toString = toString + "NOME: " + nome + "\n";
        }
        return getString(toString);
    }


    public String getIdentificacao() {
        return this.CPF;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean equals(Cliente outroCliente) {
        if (outroCliente instanceof ClientePessoa) {
            return ((ClientePessoa) outroCliente).CPF.equalsIgnoreCase(this.CPF);
        }
        return false;
    }
}

