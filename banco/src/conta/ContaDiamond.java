package conta;

import java.io.Serial;

public class ContaDiamond extends ContaPremium implements Rentavel {
    public static final int DEPOSITO_MAXIMO = 80000;
    @Serial
    private static final long serialVersionUID = 15L;

    public ContaDiamond() {
        super();
    }

    @Override
    public void renderSaldo() {
        this.saldo = this.saldo + (this.saldo * Rentavel.FATOR_RENDER_DIAMOND);
    }

    @Override
    public void renderDinheiroGuardado() {
        this.dinheiroGuardado = this.dinheiroGuardado + (this.dinheiroGuardado * Rentavel.FATOR_RENDER__GUARDADO_DIAMOND);
    }
}
