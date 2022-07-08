package utilsBank;

import cliente.Cliente;
import transacao.Boleto;
import transacao.Transacao;
import utilsBank.arquivo.Exception.LeituraArquivoException;
import utilsBank.arquivo.GerenciadorArquivo;

import java.util.ArrayList;
import java.util.HashSet;

public class GerenciadorBanco {
	//Classe para gerenciar o inicio do programa do banco
	//Chega e guarda todos os clientes numa estrutura para a agencia
	private static final String TODOS_OS_DIREITOS_RESERVADOS = "© TODOS OS DIREITOS RESERVADOS AO BIC";
	private static final String TRANSFERENCIA_AGENDADA = "Sua transferencia foi agendada com sucesso. Obrigada";

	//
	public static HashSet<Cliente> inicializarClientes() throws LeituraArquivoException {
		return GerenciadorArquivo.listarSet(GerenciadorArquivo.PATH_CLIENTES);
	}

	public static ArrayList<Transacao> inicializarTransacoes() throws LeituraArquivoException {
		return GerenciadorArquivo.listarTransacoes(GerenciadorArquivo.PATH_TRANSACOES);
	}

	public static boolean intToBoolean(int value) {
		return value != 0;
	}

	public static HashSet<Boleto> inicializarBoletos() {
		return GerenciadorArquivo.listarSetBoleto(GerenciadorArquivo.PATH_BOLETOS);
	}

	public static void imprimirDireitos() {
		System.out.println(TODOS_OS_DIREITOS_RESERVADOS);
	}

	public static void imprimirErroOpcao() {
		System.out.println("Opcao Invalida");
		imprimirDireitos();
	}

	public static void imprimirMensagemTransferenciaAgendada() {
		System.out.println(TRANSFERENCIA_AGENDADA);
	}
}
