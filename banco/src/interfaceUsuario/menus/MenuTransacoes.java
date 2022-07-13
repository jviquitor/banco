package interfaceUsuario.menus;

import agencia.exceptions.BuscaException;
import cliente.Cliente;
import cliente.exceptions.TiposClientes;
import interfaceUsuario.InterfaceUsuario;
import interfaceUsuario.PrintUtils;
import interfaceUsuario.dados.DadosChavesPix;
import interfaceUsuario.dados.DadosTransacao;
import interfaceUsuario.exceptions.ValorInvalido;
import interfaceUsuario.verificadores.dados.VerificadorEntrada;
import interfaceUsuario.verificadores.dados.VerificadorPix;
import interfaceUsuario.verificadores.dados.VerificadorTransacao;

import static interfaceUsuario.menus.MenuUsuario.*;

public class MenuTransacoes {
    protected static void receberDadosTransacoes(String tipoOperacao, String tipoConta) throws BuscaException, ValorInvalido {
        imprimirBorda("=");

        String[] cabecalhoDadosTransacao = {
                "Digite o valor ",
        };
        String[] entradaDadosTransacao = usuarioEntradas(cabecalhoDadosTransacao);

        while (!VerificadorTransacao.dadosTransacao(entradaDadosTransacao[0], tipoOperacao, tipoConta)) {
            System.out.println("DIGITE OS DADOS CORRETAMENTE POR FAVOR");
            entradaDadosTransacao = usuarioEntradas(cabecalhoDadosTransacao);
        }

        if (tipoOperacao.equals(TRANSFERENCIA)) {
            PrintUtils.avisoPix();
            String[] cabecalhoDados = {
                    "Digite o tipo da chave inserida [FORMATOS DISPONIVEIS]: " + CHAVES_DISPONIVEIS,
                    "Digite a chave",
            };
            String[] entradaDados = usuarioEntradas(cabecalhoDados);

            while (VerificadorPix.chavePix(entradaDados[1], entradaDados[0])) {
                System.out.println("DIGITE OS DADOS CORRETAMENTE POR FAVOR");
                entradaDados = usuarioEntradas(cabecalhoDados);
            }
            InterfaceUsuario.setDadosTransacao(new DadosTransacao(
                            Double.parseDouble(entradaDadosTransacao[0]),
                            entradaDados[1],
                            InterfaceUsuario.getClienteAtual().getIdentificacao(),
                            entradaDados[0],
                            VerificadorEntrada.IDENTIFICACAO
                    )
            );
        } else if (tipoOperacao.equals(DEPOSITO)) {

            Cliente c = InterfaceUsuario.getClienteAtual();
            InterfaceUsuario.setDadosTransacao(new DadosTransacao(
                    Double.parseDouble(entradaDadosTransacao[0]),
                    c,
                    c)
            );
        }
    }

    protected static void agendamentoTransferencia(String tipoConta) throws BuscaException, ValorInvalido {
        imprimirBorda("=");
        String[] cabecalhoDadosTransacao = {
                "Digite o valor ",
                "Digite a data para realizar a transferencia [FORMATO CORRETO] " + FORMATO_DATAS,
        };

        String[] entradaDadosTransacao = usuarioEntradas(cabecalhoDadosTransacao);

        while (!VerificadorTransacao.agendamentoTransacao(entradaDadosTransacao, tipoConta)) {
            entradaDadosTransacao = usuarioEntradas(cabecalhoDadosTransacao);
        }

        PrintUtils.avisoPix();
        String[] cabecalhoDados = {
                "Digite o tipo da chave inserida [FORMATOS DISPONIVEIS]: " + CHAVES_DISPONIVEIS,
                "Digite a chave",
        };
        String[] entradaDados = usuarioEntradas(cabecalhoDados);
        InterfaceUsuario.setDadosTransacao(new DadosTransacao(
                        Double.parseDouble(entradaDadosTransacao[0]),
                        entradaDados[1],
                        InterfaceUsuario.getClienteAtual().getIdentificacao(),
                        entradaDados[0],
                        VerificadorEntrada.IDENTIFICACAO,
                        entradaDadosTransacao[1]
                )
        );
    }

    protected static void adicionarChavePix(TiposClientes tiposClientes) {
        String[] cabecalhoTipoPix = {
                "[ESCREVA] Escolha o tipo de chave que deseja adicionar ou modificar! [FORMATOS ACEITOS PARA TEXTO]\n  " + CHAVES_DISPONIVEIS_ALTERACAO,
        };
        String[] entradas = usuarioEntradas(cabecalhoTipoPix);

        while (!VerificadorPix.tipoChavePix(entradas[0])) {
            entradas = usuarioEntradas(cabecalhoTipoPix);
        }
        if (entradas[0].equals(DadosChavesPix.CHAVE_ALEATORIA)) {
            InterfaceUsuario.setDadosChavePix(new DadosChavesPix(null, null, DadosChavesPix.CHAVE_ALEATORIA));
        } else {
            String[] cabecalhoChave = {
                    "POR FAVOR, DIGITE A CHAVE CORRETAMENTE\n",
            };
            String[] entradaChave = usuarioEntradas(cabecalhoChave);

            while (VerificadorPix.chavePix(entradaChave[0], entradas[0])) {
                entradas = usuarioEntradas(cabecalhoTipoPix);
            }

            switch (entradas[0]) {
                case DadosChavesPix.TELEFONE:
                    InterfaceUsuario.setDadosChavePix(new DadosChavesPix(entradaChave[0], null, DadosChavesPix.TELEFONE));
                    break;
                case DadosChavesPix.EMAIL:
                    InterfaceUsuario.setDadosChavePix(new DadosChavesPix(null, entradaChave[0], DadosChavesPix.EMAIL));
                    break;
            }
        }
    }
}
