package flow.entities.characters;

import flow.Battle;
import flow.Element;
import flow.Visitor;
import flow.entities.spells.Earth;
import flow.entities.spells.Fire;
import flow.entities.spells.Ice;
import flow.entities.spells.Spell;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Filter;

import static java.lang.Math.max;

public abstract class Entity implements Battle, Element<Entity> {
    int viata, viataMax;
    public ArrayList<Spell> abilitati;
    public int mana, manaMax;
    boolean imunitateFoc, imunitateGheata, imunitatePamant;

    public boolean sansa() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        int num = rand.nextInt(10);

        return num >= 5;
    }

    public int getViata(){
        return viata;
    }

    public void setViata(int viata){
        this.viata = viata;
    }

    public int getViataMax(){
        return viataMax;
    }

    public int getMana(){
        return mana;
    }
    public void setMana(int mana){
        this.mana = mana;
    }

    public int getManaMax(){
        return manaMax;
    }

    public void adaugaViata(int inc) {
        viata += inc;

        if (viata > viataMax) {
            viata = viataMax;
        }
    }

    public void adaugaMana(int inc) {
        mana += inc;

        if (mana > manaMax) {
            mana = manaMax;
        }
    }

    public boolean getImunitateFoc() {
        return imunitateFoc;
    }

    public void setImunitateFoc(boolean imunitateFoc) {
        this.imunitateFoc = imunitateFoc;
    }

    public boolean getImunitateGheata() {
        return imunitateGheata;
    }

    public void setImunitateGheata(boolean imunitateGheata) {
        this.imunitateGheata = imunitateGheata;
    }

    public boolean getImunitatePamant() {
        return imunitatePamant;
    }

    public void setImunitatePamant(boolean imunitatePamant) {
        this.imunitatePamant = imunitatePamant;
    }

    public void startLupta() {
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        abilitati = new ArrayList<>();

        int numar_abilitati = rand.nextInt(4) + 3;

        for (int i = 0; i < numar_abilitati; i++) {
            int tip = rand.nextInt(3);

            int damage = viataMax / 4 + rand.nextInt(2);
            int costMana =  manaMax / 4 + rand.nextInt(2);

            switch (tip) {
                case 0:
                    abilitati.add(new Fire(damage, costMana));
                    break;
                case 1:
                    abilitati.add(new Ice(damage, costMana));
                    break;
                case 2:
                    abilitati.add(new Earth(damage, costMana));
                    break;
            }
        }
    }

    public void folosireAbilitate(Entity e, Spell abilitate) {
        int damage = this.getDamage();

        if (mana < abilitate.costMana) {
            System.out.println("Nu ai destula mana pentru a folosi abilitatea!");
            System.out.println("Vei folosi atac default!!");
            e.receiveDamage(damage);
            return;
        }

        abilitati.remove(abilitate);
        mana = max(mana - abilitate.costMana, 0);

        if (abilitate instanceof Fire && e.imunitateFoc) {
            System.out.println("Nu s a putut folosi abilitatea Fire. Inamicul este imun.");
        }
        else if (abilitate instanceof Ice && e.imunitateGheata) {
            System.out.println("Nu s a putut folosi abilitatea Ice. Inamicul este imun.");
        }
        else if (abilitate instanceof Earth && e.imunitatePamant) {
            System.out.println("Nu s a putut folosi abilitatea Earth. Inamicul este imun.");
        } else {
            damage += abilitate.damage;
        }
        e.receiveDamage(damage);
    }

    public void accept(Visitor<Entity> v){
        v.visit(this);
    }


}
