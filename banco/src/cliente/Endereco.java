package cliente;

import java.io.Serial;
import java.io.Serializable;

@SuppressWarnings("unused")
public class Endereco implements Serializable {
    @Serial
    private static final long serialVersionUID = 3L;
    private final String CEP;
    private final Integer NUMERO_CASA;
    private String complemento;

    public Endereco(String CEP, Integer numeroCasa, String complemento) {
        this.CEP = CEP;
        this.NUMERO_CASA = numeroCasa;
        this.complemento = complemento;
    }

    @Override
    public String toString() {
        String toString = "\n";
        if (CEP != null) {
            toString = toString + "CEP: " + CEP + "\n";
        }
        return toString;
    }

    protected void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    protected Integer getNumeroCasa() {
        return NUMERO_CASA;
    }
}
