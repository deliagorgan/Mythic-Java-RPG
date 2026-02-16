package flow.entities.characters;


import java.util.Random;

public class Rogue extends Character {
    public Rogue(String cname, Integer experience, int lvl) {
        super(cname, experience, lvl);
        this.putere = lvl;
        this.dexteritate = 3 * lvl; //atribut principat
        this.carisma = lvl;
        super.imunitateFoc = false;
        super.imunitateGheata = false;
        super.imunitatePamant = true;
    }

    @Override
    public void receiveDamage(int damage) {
        if (carisma + putere > dexteritate)
            if (super.sansa())
                damage = damage / 2;
        super.viata -= damage;
    }

    @Override
    public int getDamage() {
        int d;
        //d = dexteritate / 100 + 1;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        d = random.nextInt(3) + (int)Math.log(dexteritate + 1);
        if(super.sansa())
            d = d * 2;
        return d;
    }
}
