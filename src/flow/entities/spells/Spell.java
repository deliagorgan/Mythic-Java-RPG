package flow.entities.spells;

import flow.Visitor;
import flow.entities.characters.Entity;

import javax.swing.*;

public abstract class Spell implements Visitor<Entity> {
    public int damage;
    public int costMana;

    public Spell(int damage, int costMana) {
        this.damage = damage;
        this.costMana = costMana;
    }

    public String toString() {
        return " damage oferit: " + damage + ", cost mana: " + costMana;
    }

    public void visit(Entity entity) {
        if (this instanceof Fire && entity.getImunitateFoc() == true){
            JOptionPane.showMessageDialog(null, "Imunitate la Foc! Nu s-a aplicat damage!!",
                    "Imunitate", JOptionPane.WARNING_MESSAGE);
            /*System.out.println(" este imun la abilitatea de tip Fire. Niciun efect aplicat.");*/
        } else if (this instanceof Ice && entity.getImunitateGheata() == true){
            JOptionPane.showMessageDialog(null, "Imunitate la Gheata! Nu s-a aplicat damage!!",
                    "Imunitate", JOptionPane.WARNING_MESSAGE);
            /*System.out.println(" este imun la abilitatea de tip Ice. Niciun efect aplicat.");*/
        } else if (this instanceof Earth && entity.getImunitatePamant() == true){
            JOptionPane.showMessageDialog(null, "Imunitate la Pamant! Nu s-a aplicat damage!!",
                    "Imunitate", JOptionPane.WARNING_MESSAGE);
            /*System.out.println(" este imun la abilitatea de tip Earth. Niciun efect aplicat." );*/
        } else {
            entity.setViata(Math.max(entity.getViata() - this.damage, 0));
            System.out.println(" a primit " + damage + " damage de tip " + this.getClass().getSimpleName() + ". Viata rămasă: " + entity.getViata());
        }
    }
}
