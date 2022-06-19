package agencia;

import cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

//TODO Perguntar se a implementação em Singleton é melhor
public class Agencia {
	public static final String ID_AGENCIA = "6721";
	public static final String CODIGO_MOEDA = "9";
	private static final List<Cliente> clientes = new ArrayList<>(); //Talvez mudaremos o tipo de estrutura que usaremos para guardar os clientes
	private Double rendaAgencia = 0.0;

	public boolean addCliente(Cliente cliente) {
		if (!clientes.contains(cliente)) {
			clientes.add(cliente);
			return true;
		}
		return false;
	}

	public boolean buscarCliente() {
		return false;
	}


}
