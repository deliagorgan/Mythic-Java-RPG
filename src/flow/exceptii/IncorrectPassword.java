package flow.exceptii;

public class IncorrectPassword extends Exception {
    public IncorrectPassword(String message) {
        super("Parola incorecta! Introduceti parola din nou!");
    }
}
