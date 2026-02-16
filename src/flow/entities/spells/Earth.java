package flow.entities.spells;

public class Earth extends Spell {
    public Earth(int damage, int costMana) {
        super(damage, costMana);
    }

    @Override
    public String toString() {
        return "Earth " + super.toString();
    }
}
