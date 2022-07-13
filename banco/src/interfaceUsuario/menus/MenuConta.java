package interfaceUsuario.menus;

import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.dados.DadosConta;
import interfaceUsuario.exceptions.ValorInvalido;
import interfaceUsuario.verificadores.dados.VerificadorConta;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;
import utilsBank.GerenciadorBanco;

import static interfaceUsuario.menus.MenuUsuario.TECLADO;

public class MenuConta {
    public static Double criacaoConta() throws ValorInvalido {
        Double renda = inserirRenda();

        String entrada;
        System.out.println("Deseja debito automatico? [1] SIM [0] NAO");
        entrada = TECLADO.nextLine();
        while (VerificadorEntrada.verificarEntradasZeroUm(entrada)) {
            System.out.println("Por favor, insira corretamente a opcao!");
            entrada = TECLADO.nextLine();
        }
        boolean debitoAutomatico = GerenciadorBanco.intToBoolean(Integer.parseInt(entrada));
        MenuCartoes.criacaoCartao();

        InterfaceUsuario.setDadosConta(new DadosConta(
                VerificadorConta.tipoDeContaPelaRenda(renda),
                debitoAutomatico)
        );

        return renda;
    }

    protected static Double inserirRenda() {
        double renda = 0.0;
        while (renda < VerificadorEntrada.RENDA_MINIMA) {
            try {
                System.out.println("Por favor, Insira sua Renda");
                renda = Double.parseDouble(TECLADO.nextLine());
                VerificadorConta.verificarRenda(renda);
            } catch (ValorInvalido ex) {
                System.out.println(ex.getMessage());
            }
        }
        return renda;
    }
}
