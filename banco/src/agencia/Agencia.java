package agencia;

import agencia.exceptions.BuscaException;
import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import conta.Conta;
import conta.Rentavel;
import funcionalidades.exceptions.EmprestimoException;
import interfaceUsuario.dados.DadosChavesPix;
import transacao.Boleto;
import transacao.Transacao;
import utilsBank.GeracaoAleatoria;
import utilsBank.GerenciadorBanco;
import utilsBank.VerificadorDiario;
import utilsBank.arquivo.GerenciadorArquivo;
import utilsBank.arquivo.exception.EscritaArquivoException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Agencia {
	public static final String ID_AGENCIA = "6721";
	public static final String CODIGO_MOEDA = "9";
	private static Agencia instance;
	private static Set<Cliente> clientes;
	private static Set<Boleto> boletos;
	private static ArrayList<Transacao> transacoesAgendadas;
	private Double rendaAgencia;

	private Agencia() {
		this.rendaAgencia = Math.pow(2, 31);
	}

	public static Agencia getInstance() {
		if (instance == null) {
			clientes = GerenciadorBanco.inicializarClientes();
			boletos = GerenciadorBanco.inicializarBoletos();
			transacoesAgendadas = GerenciadorBanco.inicializarTransacoes();
			instance = new Agencia();
		}
		return instance;
	}

	public static Cliente buscarCliente(String chave) throws BuscaException {
		for (Cliente cliente : clientes) {
			if (Objects.equals(cliente.getIdentificacao(), chave)) {
				return cliente;
			}
		}
		throw new BuscaException("Cliente nao encontrado");
	}

	/**
	 * @param tipodeChave Informa o tipo da chave, declara em DadosChavesPix
	 * @param chave       Podendo ser de email, chave aleatória, identificação, telefone
	 * @return cliente que contém a chave especificada
	 * @throws BuscaException caso o cliente não for encontrado
	 */
	public Cliente buscarClientePorChavePix(String tipodeChave, String chave) throws BuscaException {
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
		throw new BuscaException("Cliente nao encontrado");
	}

	/**
	 * Busca um cliente que é gerente de uma empresa, e retorna a empresa que o mesmo faz parte.
	 *
	 * @param cpf recebe uma string de identificação para buscar uma empresa que esse cpf faz parte como gerente
	 * @return clienteEmpresa ou null se não for encontrado o gerente
	 */
	public ClienteEmpresa buscarEmpresa(String cpf) {
		for (Cliente cliente : this.clientes) {
			if (cliente instanceof ClienteEmpresa clienteEmpresa) {
				if (clienteEmpresa.verificarGerente(cpf)) {
					return clienteEmpresa;
				}
			}
		}
		return null;
	}

	public static Boleto buscarBoleto(String nossoNumero) throws BuscaException {
		for (Boleto boleto : boletos) {
			if (boleto.getNossoNumero().equals(nossoNumero)) {
				return boleto;
			}
		}
		throw new BuscaException("Boleto nao encontrado");
	}

	/**
	 * retorna os boletos que estão atrelados a uma conta.
	 *
	 * @param conta do cliente
	 * @return HashSet dos boletos que fazem parte da conta do cliente
	 */
	public HashSet<Boleto> buscarBoletosConta(Conta conta) {
		HashSet<Boleto> boletosConta = new HashSet<>();
		for (Boleto boleto : boletos) {
			if (boleto.getContaDestino().getIdConta().equals(conta.getIdConta())) {
				boletosConta.add(boleto);
			}
		}

		return boletosConta;
	}

	public static void addBoleto(Boleto boleto) {
		boletos.add(boleto);
	}

	public static void apagarBoleto(Boleto boleto) {
		boletos.remove(boleto);
	}

	public static void imprimirClientes() {
		for (Cliente cliente : clientes) {
			System.out.println(cliente.allInfos());
		}
	}

	public static ArrayList<Transacao> getTransacoes() {
		return transacoesAgendadas;
	}

	/**
	 * Função que gerencia a funcionalidade do cliente ter o empréstimo
	 *
	 * @param valor do emprestimo
	 * @throws EmprestimoException caso a renda da agência não tiver dinheiro para emprestar
	 */
	public void pegarEmprestimo(double valor) throws EmprestimoException {
		if (this.rendaAgencia >= valor) {
			this.rendaAgencia -= valor;
		} else {
			throw new EmprestimoException();
		}
	}

	public void abrindoAgencia() {
		for (Cliente cliente : clientes) {
			cliente.getConta().setSaldoTotalDepositado(0.0);
		}
	}

	public void renderContas() {
		for (Cliente cliente : clientes) {
			((Rentavel) cliente.getConta()).renderSaldo();
		}
	}

	public void addCliente(Cliente cliente) throws InsercaoException, EscritaArquivoException {
		try {
			Agencia.buscarCliente(cliente.getIdentificacao());
		} catch (BuscaException e) {
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
		}
	}

	public void addSaldo(double valor) {
		this.rendaAgencia += valor;
	}

	public void atualizarArquivos() throws EscritaArquivoException {
		GerenciadorArquivo.salvarClientes((HashSet<Cliente>) Agencia.clientes);
		GerenciadorArquivo.salvarBoletos((HashSet<Boleto>) Agencia.boletos);
		GeracaoAleatoria.salvarChavesAleatorias();
		GeracaoAleatoria.salvarNossosNumeros();
		GeracaoAleatoria.salvarNumerosCartoes();
		GeracaoAleatoria.salvarIdsContas();
		GerenciadorArquivo.salvarData(VerificadorDiario.getInstance().getUltimaAtualizacao());
	}
}
