package flow.exceptii;

public class ImpossibleMove extends Exception {
    public ImpossibleMove(String message) {
        super("Mutarea nu s a putut realiza la " + message + "!");
    }
}
