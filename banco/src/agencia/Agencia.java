package agencia;

import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import utilsBank.GerenciadorBanco;

import java.util.Objects;
import java.util.Set;

//TODO Perguntar se a implementação em Singleton é melhor
public class Agencia {
	private static final Agencia instance = new Agencia();

	public static final String ID_AGENCIA = "6721";
	public static final String CODIGO_MOEDA = "9";
	private static final Set<Cliente> clientes = GerenciadorBanco.inicializarClientes();
	private Double rendaAgencia = 0.0;

	private Agencia() {
		this.rendaAgencia = 0.0;
	}

	public void addCliente(Cliente cliente) throws InsercaoException {
		if (!clientes.add(cliente)) {
			throw new InsercaoException("Cliente ja existe");
		}
	}

	public boolean buscarCliente() {
		return false;
	}

	public static Agencia getInstance() {
		return instance;
	}
}
