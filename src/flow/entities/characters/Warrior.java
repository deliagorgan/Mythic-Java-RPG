package flow.entities.characters;

import java.util.Random;

public class Warrior extends Character {
    public Warrior(String cname, Integer experience, int lvl) {
        super(cname, experience, lvl);
        this.putere = lvl * 3; //atribut principal
        this.dexteritate = lvl;
        this.carisma = lvl;
        super.imunitateFoc = true; //imunitate la foc
        super.imunitateGheata = false;
        super.imunitatePamant = false;
    }

    @Override
    public void receiveDamage(int damage) {
        if (dexteritate + carisma > putere)
            if (super.sansa())
                damage = damage / 2;

        super.viata -= damage;
    }

    @Override
    public int getDamage() {
        int d;
        //d = putere / 100 + 1;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        d = random.nextInt(3) + (int)Math.log(putere + 1);
        if(super.sansa())
            d = d * 2;
        return d;
    }
}
