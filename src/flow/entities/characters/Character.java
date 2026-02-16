package flow.entities.characters;
import java.util.Random;

public abstract class Character extends Entity {
    private String numePersonaj;
    private Integer experienta;
    private int nivelCurentPersonaj;
    public int putere;
    public int carisma;
    public int dexteritate;

    public Character(String numePersonaj, Integer experienta, int nivelCurentPersonaj) {
        this.numePersonaj = numePersonaj;
        this.experienta = experienta;
        this.nivelCurentPersonaj = nivelCurentPersonaj;

        /*
        super.mana = nivelCurentPersonaj * 20;
        super.manaMax = nivelCurentPersonaj * 20;

        super.viata = nivelCurentPersonaj * 30;
        super.viataMax = nivelCurentPersonaj * 30;*/

        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());

        super.mana = rand.nextInt(20) + (int) Math.log(Math.exp(1) + nivelCurentPersonaj) * 10;
        super.manaMax = super.mana;

        super.viata = rand.nextInt(20) + (int)Math.log(Math.exp(1) + nivelCurentPersonaj) * 10;
        super.viataMax = super.viata;
    }

    public String getNumePersonaj(){
        return numePersonaj;
    }

    public void castigaLupta() {
        super.adaugaViata(viata);
        super.adaugaMana(manaMax);
        int xp = (new Random()).nextInt(5) + 5;
        System.out.println("      +" + xp + "xp. :)");
        experienta += xp;
    }


    @Override
    public String toString() {
        return "Numele personajului: " + numePersonaj + "\n" +
                "Experienta: " + experienta + "   " +
                "Nivel: " + nivelCurentPersonaj;
    }

    public int getNivelCurentPersonaj(){
        return nivelCurentPersonaj;
    }

    public void setNivelCurentPersonaj(int nivelCurentPersonaj){
        this.nivelCurentPersonaj = nivelCurentPersonaj;
    }

    public Integer getExperienta(){
        return experienta;
    }

    public void setExperienta(Integer experienta){
        this.experienta = experienta;
    }
}
