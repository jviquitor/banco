package conta;

public interface Rentavel {
    Double FATOR_RENDER_STANDARD = 0.0002;
    Double FATOR_RENDER_PREMIUM = 0.0005;
    Double FATOR_RENDER_DIAMOND = 0.0008;

    void renderSaldo();
}
