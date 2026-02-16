package flow.exceptii;

public class InexistentAbilityNumberException extends RuntimeException {
  String message;
    public InexistentAbilityNumberException(String message) {
        super("Abilitatea cu numarul " + message + " nu exista! Alegeti o abilitate valida!");
    }
}
