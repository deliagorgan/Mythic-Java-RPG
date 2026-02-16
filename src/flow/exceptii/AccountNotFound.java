package flow.exceptii;

public class AccountNotFound extends Exception {
    private String message;

    public AccountNotFound(String message) {
        super("Contul cu emailul " + message + " nu a fost gasit !");
    }
}
