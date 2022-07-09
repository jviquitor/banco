package conta;

import java.io.Serial;

public class ContaPremium extends ContaStandard implements Rentavel {
    public static final int DEPOSITO_MAXIMO = 50000;
    @Serial
    private static final long serialVersionUID = 16L;

    public ContaPremium() {
        super();
    }

    @Override
    public void renderSaldo() {
        this.saldo = this.saldo + (this.saldo * Rentavel.FATOR_RENDER_PREMIUM);
    }

    @Override
    public void renderDinheiroGuardado() {
        this.dinheiroGuardado = this.dinheiroGuardado + (this.dinheiroGuardado * Rentavel.FATOR_RENDER__GUARDADO_PREMIUM);
    }
}
