package agencia;

import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import conta.Conta;
import interfaceUsuario.dados.DadosChavesPix;
import utilsBank.GerenciadorBanco;

import java.util.Objects;
import java.util.Set;

public class Agencia {
	public static final String ID_AGENCIA = "6721";
	public static final String CODIGO_MOEDA = "9";
	private static final Agencia instance = new Agencia();
	private static final Set<Cliente> clientes = GerenciadorBanco.inicializarClientes();
	private final Double rendaAgencia;

	private Agencia() {
		this.rendaAgencia = 0.0;
	}

	public static Agencia getInstance() {
		return instance;
	}

	public void addCliente(Cliente cliente) throws InsercaoException {
		if (!clientes.add(cliente)) {
			throw new InsercaoException("Cliente ja existe");
		}
	}

	public static Cliente buscarCliente(String chave) {
		for (Cliente cliente : clientes) {
			if (Objects.equals(cliente.getIdentificacao(), chave)) {
				return cliente;
			}
		}
		return null;
	}

	public static Cliente buscarClientePorChavePix(String tipodeChave, String chave) {
		for (Cliente cliente : clientes) {
			Conta contaCliente = cliente.getConta();
			switch (tipodeChave) {
				case DadosChavesPix.TELEFONE -> {
					if (chave.equals(contaCliente.getChavesPix().getTelefone().get(tipodeChave))) {
						return cliente;
					}
				}
				case DadosChavesPix.EMAIL -> {
					if (chave.equals(contaCliente.getChavesPix().getEmail().get(tipodeChave))) {
						return cliente;
					}
				}
				case DadosChavesPix.IDENTIFICACAO -> {
					if (chave.equals(contaCliente.getChavesPix().getIdentificacao().get(tipodeChave))) {
						return cliente;
					}
				}
				case DadosChavesPix.CHAVE_ALEATORIA -> {
					if (chave.equals(contaCliente.getChavesPix().getChaveAleatoria().get(tipodeChave))) {
						return cliente;
					}
				}
			}

		}
		return null;
	}
}
