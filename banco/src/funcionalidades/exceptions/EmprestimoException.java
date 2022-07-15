package funcionalidades.exceptions;

public class EmprestimoException extends Exception {
    public EmprestimoException() {
        super("Desculpe, estamos pobres. Tente novamente mais tarde");
    }

    public EmprestimoException(String msg) {
        super(msg);
    }
}
