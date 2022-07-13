package interfaceUsuario.menus;

import cliente.Cliente;
import interfaceUsuario.exceptions.ValorInvalido;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;
import utilsBank.GerenciadorBanco;

import static interfaceUsuario.menus.MenuUsuario.*;

public class MenuDinheiroGuardado {

    protected static void gerenciarDinheiroGuardado(Cliente cliente) {
        boolean loop = true;
        while (loop) {
            imprimirBorda("=");
            System.out.println("[0] - Cancelar");
            System.out.println("[1] - Guardar dinheiro");
            System.out.println("[2] - Resgatar dinheiro guardado");
            System.out.println("[3] - Mostrar dinheiro guardado");
            imprimirBorda("=");
            System.out.print("\n> ");
            try {
                String op = TECLADO.nextLine();
                double valor;
                switch (op) {
                    case "0":
                        loop = false;
                        break;
                    case "1":
                        valor = guardarDinheiro(GUARDAR);
                        cliente.getConta().setDinheiroGuardado(valor, GUARDAR);
                        System.out.println("NOVO SALDO >> " + cliente.getConta().getSaldo());
                        System.out.println("VALOR GUARDADO >> " + cliente.getConta().getDinheiroGuardado());
                        break;
                    case "2":
                        valor = guardarDinheiro(RESGATAR);
                        cliente.getConta().setDinheiroGuardado(valor, RESGATAR);
                        System.out.println("NOVO SALDO >> " + cliente.getConta().getSaldo());
                        System.out.println("VALOR GUARDADO >> " + cliente.getConta().getDinheiroGuardado());
                        break;
                    case "3":
                        System.out.println("VALOR GUARDADO >> " + cliente.getConta().getDinheiroGuardado());
                        break;
                    default:
                        GerenciadorBanco.imprimirErroOpcao();
                        break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    protected static Double guardarDinheiro(String tipoOperacao) throws ValorInvalido {
        imprimirBorda("=");
        String[] cabecalhoDinheiroGuardado = {
                "Digite o valor para ser " + tipoOperacao + "!",
        };
        String[] entrada = usuarioEntradas(cabecalhoDinheiroGuardado);

        while (!VerificadorEntrada.verificarEntradaValor(entrada[0], tipoOperacao)) {
            entrada = usuarioEntradas(cabecalhoDinheiroGuardado);
        }
        return Double.parseDouble(entrada[0]);
    }
}
