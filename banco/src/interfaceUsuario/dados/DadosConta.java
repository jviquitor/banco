package interfaceUsuario.dados;

public class DadosConta {
    private final String tipoDaConta;
    private final boolean debitoAutomatico;

    public DadosConta(String tipoDaConta, boolean debitoAutomatico) {
        this.tipoDaConta = tipoDaConta;
        this.debitoAutomatico = debitoAutomatico;
    }

    public String getTipoDaConta() {
        return tipoDaConta;
    }

    public boolean isDebitoAutomatico() {
        return debitoAutomatico;
    }
}
