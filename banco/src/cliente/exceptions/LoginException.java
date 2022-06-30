package cliente.exceptions;

public class LoginException extends Exception {
    public LoginException() {
        super("Nao foi possivel fazer login");
    }

    public LoginException(String msg) {
        super(msg);
    }
}
