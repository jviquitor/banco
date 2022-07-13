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
	private final Set<Cliente> clientes;
	private final Set<Boleto> boletos;
	private final ArrayList<Transacao> transacoesAgendadas;
	private Double rendaAgencia;

	private Agencia() {
		this.rendaAgencia = Math.pow(2, 31);
		this.clientes = GerenciadorBanco.inicializarClientes();
		this.boletos = GerenciadorBanco.inicializarBoletos();
		this.transacoesAgendadas = GerenciadorBanco.inicializarTransacoes();
	}

	public static Agencia getInstance() {
		if (instance == null) {
			instance = new Agencia();
		}
		return instance;
	}

	/**
	 * Busca um cliente com a chave de identificação
	 *
	 * @param chave a identificação, sendo CPF ou CNPJ
	 * @return cliente — que contém o paramêtro buscada
	 * @throws BuscaException caso o cliente não for encontrado
	 */
	public Cliente buscarCliente(String chave) throws BuscaException {
		for (Cliente cliente : this.clientes) {
			if (Objects.equals(cliente.getIdentificacao(), chave)) {
				return cliente;
			}
		}
		throw new BuscaException("Cliente nao encontrado");
	}

	/**
	 * @param tipodeChave Informa o tipo da chave, declara em DadosChavesPix
	 * @param chave       Podendo ser de "email", chave aleatória, identificação, telefone
	 * @return cliente que contém a chave especificada
	 * @throws BuscaException caso o cliente não for encontrado
	 */
	public Cliente buscarClientePorChavePix(String tipodeChave, String chave) throws BuscaException {
		String chavePix = null;
		for (Cliente cliente : this.clientes) {
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
	 * @param cpf recebe uma ‘string’ de identificação para buscar uma empresa que esse cpf faz parte como gerente
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

	/**
	 * Busca o boleto requerido pelo paramêtro
	 *
	 * @param nossoNumero Identificação do boleto
	 * @return boleto
	 * @throws BuscaException caso o boleto não for encontrado
	 */
	public Boleto buscarBoleto(String nossoNumero) throws BuscaException {
		for (Boleto boleto : this.boletos) {
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
	 * @return HashSet<Boleto> dos boletos que fazem parte da conta do cliente
	 */
	public HashSet<Boleto> buscarBoletosConta(Conta conta) {
		HashSet<Boleto> boletosConta = new HashSet<>();
		for (Boleto boleto : this.boletos) {
			if (boleto.getContaDestino().getIdConta().equals(conta.getIdConta())) {
				boletosConta.add(boleto);
			}
		}

		return boletosConta;
	}

	/**
	 * Adiciona o boleto na lista de boletos da agência
	 *
	 * @param boleto que será adicionado
	 */

	public void addBoleto(Boleto boleto) {
		this.boletos.add(boleto);
	}

	/**
	 * Apaga o boleto da lista de boletos
	 *
	 * @param boleto boleto a ser removido
	 */
	public void apagarBoleto(Boleto boleto) {
		this.boletos.remove(boleto);
	}

	/**
	 * Apenas um metódo para ver todos os clientes com as suas informações.
	 */
	public void imprimirClientes() {
		for (Cliente cliente : this.clientes) {
			System.out.println(cliente.allInfos());
		}
	}

	/**
	 * Retorna as transações agendadas da agência
	 *
	 * @return {@link ArrayList<Transacao>}
	 */
	public ArrayList<Transacao> getTransacoes() {
		return this.transacoesAgendadas;
	}

	public void addTransacao(Transacao transacao) {
		this.transacoesAgendadas.add(transacao);
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
			((Rentavel) cliente.getConta()).renderDinheiroGuardado();
		}
	}

	public Set<Cliente> getClientes() {
		return this.clientes;
	}

	public void addCliente(Cliente cliente) throws InsercaoException, EscritaArquivoException {
		try {
			this.buscarCliente(cliente.getIdentificacao());
		} catch (BuscaException e) {
			if (clientes.add(cliente)) {
				try {
					GerenciadorArquivo.salvarClientes((HashSet<Cliente>) this.clientes);
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
		GerenciadorArquivo.salvarClientes((HashSet<Cliente>) this.clientes);
		GerenciadorArquivo.salvarBoletos((HashSet<Boleto>) this.boletos);
		GeracaoAleatoria.salvarChavesAleatorias();
		GeracaoAleatoria.salvarNossosNumeros();
		GeracaoAleatoria.salvarNumerosCartoes();
		GeracaoAleatoria.salvarIdsContas();
		GerenciadorArquivo.salvarData(VerificadorDiario.getInstance().getUltimaAtualizacao());
		GerenciadorArquivo.salvarTransacoes(Agencia.getInstance().getTransacoes());
	}
}
