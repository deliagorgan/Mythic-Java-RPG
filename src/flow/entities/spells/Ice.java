package flow.entities.spells;

public class Ice extends Spell {
    public Ice(int damage, int costMana) {
        super(damage, costMana);
    }

    public String toString() {
        return "Ice " + super.toString();
    }
}
