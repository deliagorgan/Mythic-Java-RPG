package flow.exceptii;

public class InexistentPlayerNumberException extends Exception{
    String message;
    public InexistentPlayerNumberException(String message){
        super("Pozitia numarul " + message + " nu extista! Introdu din nou numarul pozitiei jucatorului cu care doresti sa joci! ");
    }
}
