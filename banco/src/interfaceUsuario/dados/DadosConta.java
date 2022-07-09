package interfaceUsuario.dados;

public class DadosConta {
    private final String TIPO_CONTA;
    private final boolean DEBITO_AUTOMATICO;

    public DadosConta(String tipoDaConta, boolean debitoAutomatico) {
        this.TIPO_CONTA = tipoDaConta;
        this.DEBITO_AUTOMATICO = debitoAutomatico;
    }

    public String getTipoDaConta() {
        return TIPO_CONTA;
    }

    public boolean isDebitoAutomatico() {
        return DEBITO_AUTOMATICO;
    }
}
