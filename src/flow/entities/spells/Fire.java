package flow.entities.spells;

public class Fire extends Spell {
    public Fire(int damage, int costMana) {
        super(damage, costMana);
    }

    public String toString() {
        return "Fire " + super.toString();
    }
}
