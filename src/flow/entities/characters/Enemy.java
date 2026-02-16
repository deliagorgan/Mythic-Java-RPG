package flow.entities.characters;

import java.util.Random;

public class Enemy extends Entity {

    int level;
    int damage;

    public Enemy(int level) {
        // se aleg valori random pt damage mana etc
        this.level = level;
        // generez valorile random ale manei si vietii inamicului in functie de level ul playerului
        // pentru a putea fi relevante pe tot parcursul jocului
        Random rand = new Random();

        rand.setSeed(System.currentTimeMillis());

        /*
        super.mana = rand.nextInt(6) + level * 15;
        super.manaMax = super.mana;

        super.viata = rand.nextInt(6) + level * 30;
        super.viataMax = super.viata;*/

        super.mana = rand.nextInt(10) + (int)Math.log(Math.exp(1) + level) * 10 + 10;
        super.manaMax = super.mana;

        super.viata = rand.nextInt(10) + (int)Math.log(Math.exp(1) + level) * 10 + 10;
        super.viataMax = super.viata;

        damage = rand.nextInt(3) + (int)Math.log(Math.exp(1) + level + 1);
    }

    @Override
    public void receiveDamage(int damage) {
        if(super.sansa()){
            damage = damage / 2;
            System.out.println();
            System.out.println("Damage injumatatit!");
            System.out.println();
        }

        super.viata -= damage;

        if (super.viata <= 0) {
            super.viata = 0;
        }
    }

    @Override
    public int getDamage() {
        int d = level;
        if(super.sansa()){
            d = d * 2;
            System.out.println();
            System.out.println("Damage dublat!");
            System.out.println();
        }


        return d;
    }
}
