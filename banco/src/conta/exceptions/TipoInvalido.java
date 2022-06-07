package conta.exceptions;

public class TipoInvalido extends IllegalArgumentException {
    public TipoInvalido() {
        super();
    }

    public TipoInvalido(String msg) {
        super(msg);
    }
}
