package flow.entities.characters;

public class CharacterFactory {

    public static Character getCharacter(String tipCaracter, String numeCaracter, Integer experienta, int nivel) {
        if (numeCaracter == null) {
            return null;
        }
        if (tipCaracter.toLowerCase().equals("mage"))
            return new Mage(numeCaracter, experienta, nivel);
        if (tipCaracter.toLowerCase().equals("rogue"))
            return new Rogue(numeCaracter, experienta, nivel);
        if (tipCaracter.toLowerCase().equals("warrior"))
            return new Warrior(numeCaracter, experienta, nivel);
        return null;
    }
}
