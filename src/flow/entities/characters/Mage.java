package flow.entities.characters;


import java.util.Random;

public class Mage extends Character {

    public Mage(String cname, Integer experience, int lvl) {
        super(cname, experience, lvl);
        this.putere = lvl;
        this.dexteritate = lvl;
        this.carisma = lvl * 3; //atribut principal
        super.imunitateFoc = false;
        super.imunitateGheata = true;
        super.imunitatePamant = false;
    }

    @Override
    public void receiveDamage(int damage) {
        if (dexteritate + putere > carisma)
            if (super.sansa())
                damage = damage / 2;

        super.viata -= damage;
    }

    @Override
    public int getDamage() {
        int d;
        //d = carisma / 100 + 1;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        d = random.nextInt(3) + (int)Math.log(carisma + 1);
        if(super.sansa())
            d = d * 2;
        return d;
    }
}
