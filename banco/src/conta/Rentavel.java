package conta;

public interface Rentavel {
    Double FATOR_RENDER_STANDARD = 0.0002;
    Double FATOR_RENDER_PREMIUM = 0.0005;
    Double FATOR_RENDER_DIAMOND = 0.0008;
    Double FATOR_RENDER_GUARDADO_STANDARD = 0.0003;
    Double FATOR_RENDER__GUARDADO_PREMIUM = 0.0007;
    Double FATOR_RENDER__GUARDADO_DIAMOND = 0.0009;

    void renderSaldo();

    void renderDinheiroGuardado();
}
