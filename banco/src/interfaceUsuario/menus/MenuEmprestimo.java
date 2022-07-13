package interfaceUsuario.menus;

import agencia.Agencia;
import conta.Conta;
import funcionalidades.exceptions.EmprestimoException;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.exceptions.ValorInvalido;
import utilsBank.GerenciadorBanco;

import static interfaceUsuario.menus.MenuUsuario.*;

public class MenuEmprestimo {

    protected static void menuEmprestimo() {
        imprimirBorda("=");
        System.out.println("[0] - Cancelar");
        System.out.println("[1] - Pedir emprestimo");
        System.out.println("[2] - Pagar emprestimo");
        imprimirBorda("=");
        System.out.print("\n> ");
        try {
            String op = TECLADO.nextLine();
            switch (op) {
                case "0":
                    break;
                case "1":
                    MenuEmprestimo.gerarEmprestimo();
                    break;
                case "2":
                    MenuEmprestimo.pagarEmprestimo();
                    break;
                default:
                    GerenciadorBanco.imprimirErroOpcao();
                    break;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    protected static void gerarEmprestimo() throws EmprestimoException, ValorInvalido {
        Conta contaAtual = InterfaceUsuario.usuarioAtualConta();
        if (contaAtual.hasEmprestimo()) {
            throw new EmprestimoException("Voce ja possui um emprestimo");
        }
        String[] cabecalho = {
                "Valor do emprestimo: ",
                "Quantidade de parcelas (ate 12x): ",
        };
        String[] entrada = usuarioEntradas(cabecalho);

        int parcelas = Integer.parseInt(entrada[1]);
        double valor = Double.parseDouble(entrada[0]);

        if (contaAtual.getCARTEIRA().getLimiteMaximo() * 4 < valor) {
            throw new EmprestimoException("Seu limite e insuficiente para esse emprestimo");
        }

        if (!(0 < parcelas && parcelas <= 12)) {
            throw new EmprestimoException();
        }

        Agencia.getInstance().pegarEmprestimo(valor);
        contaAtual.criarEmprestimo(valor, parcelas);
        System.out.printf("EMPRESTIMO REALIZADO! (Novo saldo: %.2f)\n", contaAtual.getSaldo());
    }

    protected static void pagarEmprestimo() throws EmprestimoException {
        Conta contaAtual = InterfaceUsuario.usuarioAtualConta();
        if (contaAtual.hasEmprestimo()) {
            imprimirBorda("=");
            System.out.println("[0] - Cancelar");
            System.out.printf("[1] - Pagar parcela (%.2f)\n", contaAtual.getParcelaEmprestimo());
            System.out.printf("[2] - Pagar total (%.2f)\n", contaAtual.getEmprestimo());
            imprimirBorda("=");
            System.out.print("\n> ");
            try {
                String op = TECLADO.nextLine();
                switch (op) {
                    case "0":
                        break;
                    case "1":
                        contaAtual.pagarParcelaEmprestimo();
                        System.out.printf("PARCELA PAGA! (Novo total: %.2f)\n", contaAtual.getEmprestimo());
                        break;
                    case "2":
                        contaAtual.pagarEmprestimo();
                        System.out.printf("EMPRESTIMO PAGO! (Novo saldo: %.2f)\n", contaAtual.getSaldo());
                        break;
                    default:
                        GerenciadorBanco.imprimirErroOpcao();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            throw new EmprestimoException("Voce nao possui emprestimo");
        }
    }
}
