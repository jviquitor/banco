package agencia;

import agencia.exceptions.BuscaException;
import agencia.exceptions.InsercaoException;
import cliente.Cliente;
import cliente.ClienteEmpresa;
import conta.Conta;
import funcionalidades.exceptions.EmprestimoException;
import interfaceUsuario.dados.DadosChavesPix;
import transacao.Boleto;
import utilsBank.GeracaoAleatoria;
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

	public static Cliente buscarCliente(String chave) throws BuscaException {
		for (Cliente cliente : clientes) {
			if (Objects.equals(cliente.getIdentificacao(), chave)) {
				return cliente;
			}
		}
		throw new BuscaException("Cliente nao encontrado");
	}

	public static Cliente buscarClientePorChavePix(String tipodeChave, String chave) throws BuscaException {
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

	public static ClienteEmpresa buscarEmpresa(String cpf) {
		for (Cliente cliente : clientes) {
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

	public static void addBoleto(Boleto boleto) {
		boletos.add(boleto);
	}

	public static void imprimirClientes() {
		for (Cliente cliente : clientes) {
			System.out.println(cliente.allInfos());
		}
	}

	public void pegarEmprestimo(double valor) throws EmprestimoException {
		if (this.rendaAgencia >= valor) {
			this.rendaAgencia -= valor;
		} else {
			throw new EmprestimoException();
		}
	}

	public void addCliente(Cliente cliente) throws InsercaoException, EscritaArquivoException, BuscaException {
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
	}
}
