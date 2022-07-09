package utilsBank.arquivo;

import cliente.Cliente;
import transacao.Boleto;
import transacao.Transacao;
import utilsBank.arquivo.exception.EscritaArquivoException;
import utilsBank.arquivo.exception.LeituraArquivoException;
import utilsBank.databank.Data;
import utilsBank.databank.DataBank;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class GerenciadorArquivo {
	public static final String PATH_CLIENTES = "banco/clientes.dat";
	public static final String PATH_CHAVES_NOSSO_NUMEROS = "banco/chaves_nossos_numeros.dat";
	public static final String PATH_CHAVES_GERADAS_ALEATORIA = "banco/chaves_geradas_aleatoria.dat";
	public static final String PATH_CHAVES_GERADAS_NUMERO_CARTAO = "banco/geradas_numero_cartao.dat";
	public static final String PATH_CHAVES_ID_CONTA = "banco/chaves_id_conta.dat";
	public static final String PATH_BOLETOS = "banco/boletos.dat";
	public static final String PATH_DATA = "banco/data.dat";
	public static final String PATH_TRANSACOES = "banco/transacoes.dat";

	public static Data lerData(String path) throws RuntimeException {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			Data data = (Data) arquivo.readObject();
			arquivo.close();
			if (data != null) {
				return data;
			}
			return DataBank.criarData("01/01/2000", DataBank.SEM_HORA);
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			return DataBank.criarData("01/01/2000", DataBank.SEM_HORA);
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

	public static void salvarData(Data data) throws LeituraArquivoException, EscritaArquivoException {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(GerenciadorArquivo.PATH_DATA));
			arquivo.writeObject(data);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
			throw new LeituraArquivoException("Diretorio nao encontrado");
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new EscritaArquivoException("Arquivo nao pode ser acessado");
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

	public static ArrayList<Transacao> listarTransacoes(String path) {
		try {
			ObjectInputStream arquivo = new ObjectInputStream(new FileInputStream(path));
			ArrayList<Transacao> dados = (ArrayList<Transacao>) arquivo.readObject();
			arquivo.close();
			if (dados.size() > 0) {
				return dados;
			}
			/* Lista vazia */
			return new ArrayList<>();
		} catch (FileNotFoundException ex) {
			/* Arquivo nao encontrado */
			return new ArrayList<>();
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new LeituraArquivoException("Arquivo nao pode ser acessado");
		} catch (ClassNotFoundException ex) {
			/* Classe invalida */
			throw new LeituraArquivoException("Classe invalida");
		}
	}


	public static void salvarTransacoes(ArrayList<Transacao> transacaos) throws LeituraArquivoException, EscritaArquivoException {
		try {
			ObjectOutputStream arquivo = new ObjectOutputStream(new FileOutputStream(GerenciadorArquivo.PATH_TRANSACOES));
			arquivo.writeObject(transacaos);
			arquivo.close();
		} catch (FileNotFoundException ex) {
			/* Diretorio nao encontrado */
			throw new LeituraArquivoException("Diretorio nao encontrado");
		} catch (IOException ex) {
			/* Arquivo nao pode ser acessado */
			throw new EscritaArquivoException("Arquivo nao pode ser acessado");
		}
	}
}
