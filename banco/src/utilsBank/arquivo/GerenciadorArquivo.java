package utilsBank.arquivo;

import cliente.Cliente;
import conta.Conta;
import utilsBank.arquivo.Exception.ArquivoVazioException;
import utilsBank.arquivo.Exception.EscritaArquivoException;
import utilsBank.arquivo.Exception.LeituraArquivoException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

//TODO Criar exceptions para os casos inválidos
public class GerenciadorArquivo {
	public static final String PATH_CLIENTES = "banco/clientes.dat";
	public static final String PATH_CHAVES_NOSSO_NUMEROS = "banco/chaves_nossos_numeros.dat";
	public static final String PATH_CHAVES_GERADAS_ALEATORIA = "banco/chaves_geradas_aleatoria.dat";
	public static final String PATH_CHAVES_GERADAS_NUMERO_CARTAO = "banco/geradas_numero_cartao.dat";
	public static final String PATH_CHAVES_ID_CONTA = "banco/chaves_id_conta.dat";

	public static ArrayList<Conta> listar(String path) throws RuntimeException, LeituraArquivoException {
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

	public static HashSet<Cliente> listarSet(String path) throws RuntimeException, LeituraArquivoException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			HashSet<Cliente> dados = (HashSet<Cliente>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			throw new LeituraArquivoException("Conjunto vazio");
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			throw new LeituraArquivoException("Arquivo nao encontrado");
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new LeituraArquivoException("Arquivo nao pode ser acessado");
		} catch (ClassNotFoundException ex) {
			/* Classe invalida */
			throw new LeituraArquivoException("Classe invalida");
		}
	}


	public static HashSet<String> listarSetGeracaoAleatoria(String path) throws LeituraArquivoException, ArquivoVazioException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			HashSet<String> dados = (HashSet<String>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			throw new ArquivoVazioException();
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			throw new LeituraArquivoException("Arquivo nao encontrado");
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new LeituraArquivoException("Arquivo nao pode ser acessado");
		} catch (ClassNotFoundException ex) {
			/* Classe invalida */
			throw new LeituraArquivoException("Classe invalida");
		}
	}

	public static void inserir(String path, ArrayList<Conta> novosDados) throws LeituraArquivoException, EscritaArquivoException {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(path));
			arquivo.writeObject(novosDados);
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

	public static <T extends Collection, Serializable> T tryListar(String path) throws RuntimeException, LeituraArquivoException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			T dados = (T) arquivo.readObject();
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

	public static <T extends Serializable> void tryInserir(String path, T colecao) {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(path));
			arquivo.writeObject(colecao);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
		}
	}
}
