package interfaceUsuario.menus;

import agencia.Agencia;
import agencia.exceptions.BuscaException;
import conta.Conta;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.DadosBoleto;
import interfaceUsuario.dados.DadosTransacao;
import interfaceUsuario.exceptions.ValorInvalido;
import interfaceUsuario.verificadores.dados.VerificadorTransacao;
import transacao.Boleto;
import transacao.exceptions.TransacaoException;

import java.util.HashSet;

import static interfaceUsuario.menus.MenuUsuario.*;

public class MenuBoletos {

    protected static void gerarBoleto() throws ValorInvalido {
        String[] cabecalho = {
                "Valor",
                "Data de vencimento [EXEMPLO DE FORMATO CORRETO: " + FORMATO_DATAS + "]",
                "Multa por dias",
        };
        String[] entrada;
        do {
            entrada = usuarioEntradas(cabecalho);
        } while (!VerificadorTransacao.verificarBoleto(entrada));

        DadosTransacao dadosTransacao = new DadosTransacao(Double.parseDouble(entrada[0]), InterfaceUsuario.getClienteAtual());
        DadosBoleto dadosBoleto = new DadosBoleto(entrada[1], Integer.parseInt(entrada[2]), false);

        InterfaceUsuario.setDadosBoleto(dadosBoleto);
        InterfaceUsuario.setDadosTransacao(dadosTransacao);
    }

    protected static void mostrarBoletos() throws BuscaException {
        HashSet<Boleto> boletosGerados = Agencia.getInstance().buscarBoletosConta(InterfaceUsuario.usuarioAtualConta());
        if (boletosGerados.size() <= 0) {
            throw new BuscaException("Voce nao possui boletos gerados");
        }
        for (Boleto boleto : boletosGerados) {
            System.out.println(boleto);
        }
    }

    protected static void pagarBoleto() throws BuscaException, TransacaoException {
        System.out.print("Numero do boleto: \n> ");
        String numBoleto = TECLADO.nextLine();
        Boleto boleto = Agencia.getInstance().buscarBoleto(numBoleto);
        Conta origem = InterfaceUsuario.usuarioAtualConta();
        origem.pagarBoleto(boleto, InterfaceUsuario.getClienteAtual());
        Agencia.getInstance().apagarBoleto(boleto);
    }
}
