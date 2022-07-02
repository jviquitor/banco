package agencia;

import funcionalidades.exceptions.EmprestimoException;
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
	private Double rendaAgencia;

	private Agencia() {
		this.rendaAgencia = Math.pow(2, 31);
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
		String chavePix = null;
		for (Cliente cliente : clientes) {
			Conta contaCliente = cliente.getConta();
			switch (tipodeChave) {
				case DadosChavesPix.TELEFONE:
					chavePix = contaCliente.getChavesPix().getTelefone();
					break;
				case DadosChavesPix.EMAIL:
					chavePix = contaCliente.getChavesPix().getEmail();
					break;
				case DadosChavesPix.IDENTIFICACAO:
					chavePix = contaCliente.getChavesPix().getIdentificacao();
					break;
				case DadosChavesPix.CHAVE_ALEATORIA:
					chavePix = contaCliente.getChavesPix().getChaveAleatoria();
					break;
			}
			if (chave.equals(chavePix)) {
				return cliente;
			}
		}
		return null;
	}

	public void pegarEmprestimo(double valor) throws EmprestimoException {
		if (this.rendaAgencia >= valor) {
			this.rendaAgencia -= valor;
		} else {
			throw new EmprestimoException();
		}
	}

	public void addSaldo(double valor) {
		this.rendaAgencia += valor;
	}
}
