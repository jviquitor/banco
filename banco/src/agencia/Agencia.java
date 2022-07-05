package agencia;

import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import conta.Conta;
import funcionalidades.exceptions.EmprestimoException;
import interfaceUsuario.dados.DadosChavesPix;
import transacao.Boleto;
import utilsBank.GerenciadorBanco;
import utilsBank.arquivo.Exception.EscritaArquivoException;
import utilsBank.arquivo.GerenciadorArquivo;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Agencia {
	public static final String ID_AGENCIA = "6721";
	public static final String CODIGO_MOEDA = "9";
	private static final Agencia instance = new Agencia();
	private static final Set<Cliente> clientes = GerenciadorBanco.inicializarClientes();
	private static final Set<Boleto> boletos = GerenciadorBanco.inicializarBoletos();
	private Double rendaAgencia;

	private Agencia() {
		this.rendaAgencia = Math.pow(2, 31);
	}

	public static Agencia getInstance() {
		return instance;
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

	public static Boleto buscarBoleto(String nossoNumero) {
		for (Boleto boleto : boletos) {
			if (boleto.getNossoNumero().equals(nossoNumero)) {
				return boleto;
			}
		}
		return null;
	}

	public void addCliente(Cliente cliente) throws InsercaoException, EscritaArquivoException {
		if (Agencia.buscarCliente(cliente.getIdentificacao()) == null) {
			if (clientes.add(cliente)) {
				try {
					GerenciadorArquivo.salvarClientes((HashSet<Cliente>) Agencia.clientes);
				} catch (EscritaArquivoException ex) {
					clientes.remove(cliente);
					throw ex;
				}
			} else {
				throw new InsercaoException("Ocorreu um erro ao criar o cliente");
			}
		} else {
			throw new InsercaoException("Cliente ja existe");
		}
	}

	public void addSaldo(double valor) {
		this.rendaAgencia += valor;
	}
}
