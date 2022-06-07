package cliente;

import conta.Conta;

import java.util.HashSet;
import java.util.Set;

public class ClienteEmpresa extends Cliente {
	private String cnpj;
	private final Set<String> gerentesEmpresa = new HashSet<>();
	private Conta conta;
}
