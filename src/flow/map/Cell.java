package flow.map;/*tema1.src.Cell este clasa care va modela un pătrățel (o celulă) din tabla de joc.

Aceasta conține:

Coordonatele pe Ox și Oy în hartă.
Un enum care definește tipul celulei: CellEntityType.
Un indicator al stării căsuței (vizitată sau nevizitată).*/

public class Cell implements Cloneable{
    @Override
    public Cell clone() {
        try {
            Cell clone = (Cell) super.clone();

            if (this.tipEntitate != null) {
                clone.tipEntitate = this.tipEntitate;
            }
            return clone;

        } catch (CloneNotSupportedException e) {
            throw new AssertionError("", e);
        }
    }


    public enum CellEntityType {
        PLAYER,
        VOID,
        ENEMY,
        SANCTUARY,
        PORTAL
    }

    private int x;
    private int y;
    private CellEntityType tipEntitate;
    private boolean esteVizitat;
    public int experienta;

    public boolean isEsteVizitat() {
        return esteVizitat;
    }

    public void setEsteVizitat(boolean esteVizitat) {
        this.esteVizitat = esteVizitat;
    }

    public CellEntityType getTipEntitate() { return tipEntitate; }

    public String getTipEntitateString() {

        /*if (esteVizitat && tipEntitate == CellEntityType.VOID)
            return "V";
        if (!esteVizitat && tipEntitate != CellEntityType.PLAYER)
            return "N";*/

        switch (tipEntitate) {
            case PLAYER: return "P";
            case PORTAL: return "F";
            case SANCTUARY: return "S";
            case ENEMY: return "E";
            case VOID: return "V";
        }

        return "";
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setTipEntitate(CellEntityType tipEntitate) {
        this.tipEntitate = tipEntitate;
    }


    public Cell(int x, int y, CellEntityType tipEntitate) {
        this.x = x;
        this.y = y;
        this.tipEntitate = tipEntitate;
        esteVizitat = false;
    }
}
