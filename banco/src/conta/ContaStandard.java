package conta;

import java.io.Serial;

public class ContaStandard extends Conta implements Rentavel {
    public static final int DEPOSITO_MAXIMO = 1000;
    @Serial
    private static final long serialVersionUID = 17L;

    public ContaStandard() {
        super();
    }

    @Override
    public void renderSaldo() {
        this.saldo = this.saldo + (this.saldo * Rentavel.FATOR_RENDER_STANDARD);
    }
}
