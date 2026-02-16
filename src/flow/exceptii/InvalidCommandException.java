package flow.exceptii;

public class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super("Comanda introdusa este invalida!");
    }
}
