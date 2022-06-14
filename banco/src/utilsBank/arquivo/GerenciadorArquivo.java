package utilsBank.arquivo;

import conta.Conta;
import utilsBank.arquivo.Exception.ArquivoVazioException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

//TODO Criar exceptions para os casos inválidos
public class GerenciadorArquivo {
	private static final String PATH = "banco/contas.dados";

	public static ArrayList<Conta> listar() throws RuntimeException, ArquivoVazioException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(PATH));
			ArrayList<Conta> dados = (ArrayList<Conta>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			throw new ArquivoVazioException("Lista vazia");
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

	public static void inserir(ArrayList<Conta> novosDados) {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(PATH));
			arquivo.writeObject(novosDados);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
		}
	}

	public static <T extends Collection, Serializable> T trylistar(T t) throws RuntimeException, ArquivoVazioException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(PATH));
			T dados = (T) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			throw new ArquivoVazioException("Lista vazia");
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

	public static <T extends Serializable> void Tryinserir(T colecao) {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(PATH));
			arquivo.writeObject(colecao);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
		}
	}
}
