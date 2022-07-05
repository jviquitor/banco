package utilsBank.arquivo;

import cliente.Cliente;
import conta.Conta;
import transacao.Boleto;
import utilsBank.arquivo.Exception.EscritaArquivoException;
import utilsBank.arquivo.Exception.LeituraArquivoException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

//TODO Criar exceptions para os casos inválidos
public class GerenciadorArquivo {
	public static final String PATH_CLIENTES = "banco/clientes.dat";
	public static final String PATH_CHAVES_NOSSO_NUMEROS = "banco/chaves_nossos_numeros.dat";
	public static final String PATH_CHAVES_GERADAS_ALEATORIA = "banco/chaves_geradas_aleatoria.dat";
	public static final String PATH_CHAVES_GERADAS_NUMERO_CARTAO = "banco/geradas_numero_cartao.dat";
	public static final String PATH_CHAVES_ID_CONTA = "banco/chaves_id_conta.dat";
	public static final String PATH_BOLETOS = "banco/boletos.dat";

	public static ArrayList<Conta> listar(String path) throws RuntimeException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			ArrayList<Conta> dados = (ArrayList<Conta>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */

			throw new LeituraArquivoException("Lista vazia");
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			throw new RuntimeException("Arquivo nao encontrado");
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new RuntimeException("Arquivo nao pode ser acessado");
		} catch (ClassNotFoundException ex) {
			/* Classe invalida */
			throw new RuntimeException("Classe invalida");
		}
	}

	public static HashSet<Cliente> listarSet(String path) throws RuntimeException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			HashSet<Cliente> dados = (HashSet<Cliente>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			return new HashSet<>();
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			return new HashSet<>();
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			System.out.println(ex.getMessage());
			throw new LeituraArquivoException("Arquivo nao pode ser acessado");
		} catch (ClassNotFoundException ex) {
			/* Classe invalida */
			throw new LeituraArquivoException("Classe invalida");
		}
	}

	public static HashSet<Boleto> listarSetBoleto(String path) throws RuntimeException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			HashSet<Boleto> dados = (HashSet<Boleto>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			return new HashSet<>();
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			return new HashSet<>();
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new LeituraArquivoException("Arquivo nao pode ser acessado");
		} catch (ClassNotFoundException ex) {
			/* Classe invalida */
			throw new LeituraArquivoException("Classe invalida");
		}
	}

	public static HashSet<String> listarSetGeracaoAleatoria(String path) throws LeituraArquivoException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			HashSet<String> dados = (HashSet<String>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			return new HashSet<>();
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			return new HashSet<>();
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new LeituraArquivoException("Arquivo nao pode ser acessado");
		} catch (ClassNotFoundException ex) {
			/* Classe invalida */
			throw new LeituraArquivoException("Classe invalida");
		}
	}

	public static void salvarClientes(HashSet<Cliente> clientes) throws LeituraArquivoException, EscritaArquivoException {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(GerenciadorArquivo.PATH_CLIENTES));
			arquivo.writeObject(clientes);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
			throw new LeituraArquivoException("Diretorio nao encontrado");
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new EscritaArquivoException("Arquivo nao pode ser acessado");
		}
	}

	public static void salvarBoletos(HashSet<Boleto> boletos) throws LeituraArquivoException, EscritaArquivoException {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(GerenciadorArquivo.PATH_BOLETOS));
			arquivo.writeObject(boletos);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
			throw new LeituraArquivoException("Diretorio nao encontrado");
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new EscritaArquivoException("Arquivo nao pode ser acessado");
		}
	}

	public static void inserirSet(String path, HashSet<Cliente> novosDados) {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(path));
			arquivo.writeObject(novosDados);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
		}
	}

	public static void inserirSetGeracao(String path, HashSet<String> novosDados) {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(path));
			arquivo.writeObject(novosDados);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
		}
	}

}
