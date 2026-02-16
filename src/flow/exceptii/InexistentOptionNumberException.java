package flow.exceptii;

public class InexistentOptionNumberException extends RuntimeException {
    String message;
    public InexistentOptionNumberException(String message) {
        super("Optiunea cu numarul " + message + " nu exista! Introduceti o optiune valida! ");
    }
}
