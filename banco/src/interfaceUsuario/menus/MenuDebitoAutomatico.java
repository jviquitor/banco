package interfaceUsuario.menus;

import conta.GerenciamentoCartao;
import interfaceUsuario.verificadores.dados.VerificadorData;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;
import utilsBank.GerenciadorBanco;

import static interfaceUsuario.menus.MenuUsuario.TECLADO;

public class MenuDebitoAutomatico {
    protected static void escolherDebitoAutomatico(GerenciamentoCartao carteira) {
        System.out.println("DIGITE O DIA PARA DEBITAR AUTOMATICAMENTE ENTRE [" + VerificadorEntrada.DIA_MINIMO_DEB_AUTO + " - " + VerificadorEntrada.DIA_MAX_DEB_AUTO + "]");
        String entrada = TECLADO.nextLine();

        while (!VerificadorData.verificarDataDebitoAuto(entrada)) {
            System.out.println("DIA INVALIDO, POR FAVOR, INSIRA CORRETAMENTE UM DIA ENTRE [" + VerificadorEntrada.DIA_MINIMO_DEB_AUTO + " - " + VerificadorEntrada.DIA_MAX_DEB_AUTO + "]");
            entrada = TECLADO.nextLine();
        }
        carteira.setDebitoAutomatico(true, Integer.parseInt(entrada));
    }

    protected static void debitoAutomatico(GerenciamentoCartao carteiraCliente) {
        if (carteiraCliente.isDebitoAutomatico()) {
            System.out.printf("DIA DEBITO AUTOMATICO: %d\n", carteiraCliente.getDataDebitoAutomatico());
        }

        String DEBITO_ATIVADO = "DEBITO AUTOMATICO >> ATIVADO <<\nDESEJA DESATIVAR? [1] SIM [0] NAO";
        String DEBITO_ATIVADO_MODIFICAR = "DEBITO AUTOMATICO >> ATIVADO <<\nDESEJA MODIFICAR A DATA? [1] SIM [0] NAO";
        String DEBITO_DESATIVADO = "DEBITO AUTOMATICO >> DESATIVADO <<\nDESEJA ATIVAR? [1] SIM [0] NAO";
        String entrada;

        if (carteiraCliente.isDebitoAutomatico()) {
            System.out.println(DEBITO_ATIVADO);
            entrada = TECLADO.nextLine();
            while (VerificadorEntrada.verificarEntradasZeroUm(entrada)) {
                entrada = TECLADO.nextLine();
            }

            if (GerenciadorBanco.intToBoolean(Integer.parseInt(entrada))) {
                carteiraCliente.setDebitoAutomatico(false, -1);
            } else {
                gerenciarAtivarDesativarDebito(carteiraCliente, DEBITO_ATIVADO_MODIFICAR);
            }
        } else {
            gerenciarAtivarDesativarDebito(carteiraCliente, DEBITO_DESATIVADO);
        }
    }

    protected static void gerenciarAtivarDesativarDebito(GerenciamentoCartao carteiraCliente, String ESTADO_MENSAGEM) {
        String entrada;
        System.out.println(ESTADO_MENSAGEM);
        entrada = TECLADO.nextLine();
        while (VerificadorEntrada.verificarEntradasZeroUm(entrada)) {
            entrada = TECLADO.nextLine();
        }

        if (GerenciadorBanco.intToBoolean(Integer.parseInt(entrada))) {
            escolherDebitoAutomatico(carteiraCliente);
        }
    }
}
