package utilsBank;

import cliente.Cliente;
import conta.Conta;
import conta.ContaDiamond;
import conta.ContaPremium;
import conta.ContaStandard;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;
import transacao.Boleto;
import transacao.Transacao;
import utilsBank.arquivo.GerenciadorArquivo;
import utilsBank.arquivo.exception.LeituraArquivoException;

import java.util.ArrayList;
import java.util.HashSet;

public class GerenciadorBanco {
    public static final Double JUROS_FATURA = 0.03;
    private static final String TODOS_OS_DIREITOS_RESERVADOS = "© TODOS OS DIREITOS RESERVADOS AO BIC";
    private static final String TRANSFERENCIA_AGENDADA = "Sua transferencia foi agendada com sucesso. Obrigada";

    //
    public static boolean intToBoolean(int value) {
        return value != 0;
    }

    public static HashSet<Cliente> inicializarClientes() throws LeituraArquivoException {
        return GerenciadorArquivo.listarSet(GerenciadorArquivo.PATH_CLIENTES);
    }

    public static ArrayList<Transacao> inicializarTransacoes() throws LeituraArquivoException {
        return GerenciadorArquivo.listarTransacoes(GerenciadorArquivo.PATH_TRANSACOES);
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

    public static String getTipoConta(Conta c) {
        if (c instanceof ContaDiamond) {
            return VerificadorEntrada.DIAMOND;
        }
        if (c instanceof ContaPremium) {
            return VerificadorEntrada.PREMIUM;
        }
        if (c instanceof ContaStandard) {
            return VerificadorEntrada.STANDARD;
        }
        return " ";
    }
}
