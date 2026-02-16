package flow.map;

import flow.exceptii.ImpossibleMove;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.sqrt;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private int lungimeTabla;
    private int latimeTabla;
    private Character persJocCurent;
    private static Cell celulaCurenta;
    private static Cell celulaAnterioara;

    private static Grid matrice;

    private Grid(int lungimeTabla, int latimeTabla) {
        super();
        this.lungimeTabla = lungimeTabla;
        this.latimeTabla = latimeTabla;
        //initializez coordonatele celulei jucator cu (-1,-1)
        this.celulaCurenta = new Cell(-1, -1, Cell.CellEntityType.PLAYER);
        //initializez celula care va retine ce se afla in celula in care s emuta jucatorul cu -1, -1 si void
        this.celulaAnterioara = new Cell(-1, -1, Cell.CellEntityType.VOID);

        for(int i = 0; i < latimeTabla; i++) {
            ArrayList<Cell> rand = new ArrayList<>();
            for(int j = 0; j < lungimeTabla; j++) {
                rand.add(new Cell(i, j, Cell.CellEntityType.VOID));
            }

            super.add(rand);
        }
    }

    public int getLungimeTabla() {
        return lungimeTabla;
    }

    public void setLungimeTabla(int lungimeTabla) {
        this.lungimeTabla = lungimeTabla;
    }

    public int getLatimeTabla() {
        return latimeTabla;
    }

    public void setLatimeTabla(int latimeTabla) {
        this.latimeTabla = latimeTabla;
    }

    public Character getPersJocCurent() {
        return persJocCurent;
    }

    public void setPersJocCurent(Character persJocCurent) {
        this.persJocCurent = persJocCurent;
    }

    public Cell getCelulaCurenta() {
        return celulaCurenta;
    }

    public void setCelulaCurenta(Cell celulaCurenta) {
        this.celulaCurenta = celulaCurenta;
    }

    public Cell getCelulaAnterioara() {
        return celulaAnterioara;
    }

    public static Grid generareHarta(int lungimeTabla, int latimeTabla) {
        Random random = new Random();

        if (lungimeTabla > 10 || latimeTabla > 10) {
            throw new IllegalArgumentException("lungimeTabla prea mare sau latimeTabla prea mare");
        }

        if (lungimeTabla * latimeTabla < 7) {
            throw new IllegalArgumentException("dimensiune prea mica");
        }

        int numarScanctuare = 2 + random.nextInt((int) sqrt(lungimeTabla * latimeTabla));
        int numarInamici = 4 + random.nextInt((int) (lungimeTabla * latimeTabla) / 6);

        matrice = new Grid(lungimeTabla, latimeTabla);


        int i, j;

        // setare portal
        i = random.nextInt(latimeTabla);
        j = random.nextInt(lungimeTabla);

        matrice.get(i).set(j, new Cell(i, j, Cell.CellEntityType.PORTAL));

        // setare celula curenta
        i = random.nextInt(latimeTabla);
        j = random.nextInt(lungimeTabla);

        while (matrice.get(i).get(j).getTipEntitate() != Cell.CellEntityType.VOID) {
            i = random.nextInt(latimeTabla);
            j = random.nextInt(lungimeTabla);
        }

        matrice.celulaCurenta.setX(i);
        matrice.celulaCurenta.setY(j);
        matrice.get(i).set(j, matrice.celulaCurenta);

        // setare sanctuare
        for (int a  = 0; a < numarScanctuare; a++) {
            i = random.nextInt(latimeTabla);
            j = random.nextInt(lungimeTabla);

            while (matrice.get(i).get(j).getTipEntitate() != Cell.CellEntityType.VOID) {
                i = random.nextInt(latimeTabla);
                j = random.nextInt(lungimeTabla);
            }

            matrice.get(i).set(j, new Cell(i, j, Cell.CellEntityType.SANCTUARY));
            matrice.get(i).get(j).experienta = random.nextInt(1) + 3;


        }

        // setare inamici
        for (int a  = 0; a < numarInamici; a++) {
            i = random.nextInt(latimeTabla);
            j = random.nextInt(lungimeTabla);

            while (matrice.get(i).get(j).getTipEntitate() != Cell.CellEntityType.VOID) {
                i = random.nextInt(latimeTabla);
                j = random.nextInt(lungimeTabla);
            }

            matrice.get(i).set(j, new Cell(i, j, Cell.CellEntityType.ENEMY));
            matrice.get(i).get(j).experienta = random.nextInt(3) + 3;
        }

        return (Grid)matrice;
    }

    public void goNorth() throws ImpossibleMove {
        Cell infoCelAux;
        int coordXCelulaCurenta = matrice.celulaCurenta.getX();
        int coordYCelulaCurenta = matrice.celulaCurenta.getY();
        if(matrice.celulaCurenta.getX() == 0) {
            throw new ImpossibleMove("nord");
        }
        else {
            if (matrice.celulaAnterioara == null) {
                //retin informatiile celulei pe care se va muta playerul
                celulaAnterioara = new Cell(coordXCelulaCurenta - 1, coordYCelulaCurenta, matrice.get(coordXCelulaCurenta - 1).get(coordYCelulaCurenta).getTipEntitate());
                //mut playerul
                matrice.get(coordXCelulaCurenta - 1).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.PLAYER);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.VOID);
                //matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setEsteVizitat(true);
                //schimb informatiile despre celula pe care se afla playerul
                celulaCurenta.setX(coordXCelulaCurenta - 1);
                celulaCurenta.setEsteVizitat(true);
            }
            else {
                //retin informatiile celulei pe care se afla playerul intr o variabila auxiliara
                infoCelAux = celulaAnterioara.clone();
                //schimb informatiile cu informatiile din noua celula pe care urmeaza sa se mute
                celulaAnterioara.setX(coordXCelulaCurenta - 1);
                celulaAnterioara.setY(coordYCelulaCurenta);
                celulaAnterioara.setTipEntitate(matrice.get(coordXCelulaCurenta - 1).get(coordYCelulaCurenta).getTipEntitate());
                //mut playerul in matrice
                matrice.get(coordXCelulaCurenta - 1).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.PLAYER);
                //schimb informatiile casutei pe care se afla playerul in trecut
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(infoCelAux.getTipEntitate());
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setEsteVizitat(true);
                //schimb informatiile despre celula care retine playerul
                celulaCurenta.setX(coordXCelulaCurenta - 1);
                celulaCurenta.setEsteVizitat(true);
            }

        }

    }

    public void goSouth() throws ImpossibleMove {
        Cell infoCelAux;
        int coordXCelulaCurenta = matrice.celulaCurenta.getX();
        int coordYCelulaCurenta = matrice.celulaCurenta.getY();
        if (coordXCelulaCurenta == matrice.latimeTabla - 1) {
            throw new ImpossibleMove("sud");
        } else {
            if (matrice.celulaAnterioara == null) {
                celulaAnterioara = new Cell(coordXCelulaCurenta + 1, coordYCelulaCurenta, matrice.get(coordXCelulaCurenta + 1).get(coordYCelulaCurenta).getTipEntitate());
                matrice.get(coordXCelulaCurenta + 1).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.PLAYER);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.VOID);
                celulaCurenta.setX(coordXCelulaCurenta + 1);
            } else {
                infoCelAux = celulaAnterioara.clone();
                celulaAnterioara.setX(coordXCelulaCurenta + 1);
                celulaAnterioara.setY(coordYCelulaCurenta);
                celulaAnterioara.setTipEntitate(matrice.get(coordXCelulaCurenta + 1).get(coordYCelulaCurenta).getTipEntitate());
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setEsteVizitat(true);
                matrice.get(coordXCelulaCurenta + 1).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.PLAYER);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(infoCelAux.getTipEntitate());
                celulaCurenta.setX(coordXCelulaCurenta + 1);
            }
            celulaCurenta.setEsteVizitat(true);
        }
    }

    public void goEast() throws ImpossibleMove {
        Cell infoCelAux;
        int coordXCelulaCurenta = matrice.celulaCurenta.getX();
        int coordYCelulaCurenta = matrice.celulaCurenta.getY();
        if (coordYCelulaCurenta == matrice.lungimeTabla - 1) {
            throw new ImpossibleMove("est");
        } else {
            if (matrice.celulaAnterioara == null) {
                celulaAnterioara = new Cell(coordXCelulaCurenta, coordYCelulaCurenta + 1, matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta + 1).getTipEntitate());
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta + 1).setTipEntitate(Cell.CellEntityType.PLAYER);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.VOID);
                celulaCurenta.setY(coordYCelulaCurenta + 1);
            } else {
                infoCelAux = celulaAnterioara.clone();
                celulaAnterioara.setX(coordXCelulaCurenta);
                celulaAnterioara.setY(coordYCelulaCurenta + 1);
                celulaAnterioara.setTipEntitate(matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta + 1).getTipEntitate());
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setEsteVizitat(true);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta + 1).setTipEntitate(Cell.CellEntityType.PLAYER);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(infoCelAux.getTipEntitate());
                celulaCurenta.setY(coordYCelulaCurenta + 1);
            }
            celulaCurenta.setEsteVizitat(true);
        }
    }

    public void goWest() throws ImpossibleMove {
        Cell infoCelAux;
        int coordXCelulaCurenta = matrice.celulaCurenta.getX();
        int coordYCelulaCurenta = matrice.celulaCurenta.getY();
        if (coordYCelulaCurenta == 0) {
            throw new ImpossibleMove("vest");
        } else {
            if (matrice.celulaAnterioara == null) {
                celulaAnterioara = new Cell(coordXCelulaCurenta, coordYCelulaCurenta - 1, matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta - 1).getTipEntitate());
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta - 1).setTipEntitate(Cell.CellEntityType.PLAYER);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(Cell.CellEntityType.VOID);
                celulaCurenta.setY(coordYCelulaCurenta - 1);
            } else {
                infoCelAux = celulaAnterioara.clone();
                celulaAnterioara.setX(coordXCelulaCurenta);
                celulaAnterioara.setY(coordYCelulaCurenta - 1);
                celulaAnterioara.setTipEntitate(matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta - 1).getTipEntitate());
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setEsteVizitat(true);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta - 1).setTipEntitate(Cell.CellEntityType.PLAYER);
                matrice.get(coordXCelulaCurenta).get(coordYCelulaCurenta).setTipEntitate(infoCelAux.getTipEntitate());
                celulaCurenta.setY(coordYCelulaCurenta - 1);
            }
            celulaCurenta.setEsteVizitat(true);
        }
    }

    public void afisare() {
        for (ArrayList<Cell> a : matrice) {
            for (Cell c : a) {
                System.out.print(c.getTipEntitateString() + " ");
            }
            System.out.println();
        }
        System.out.printf("(%d %d)\n ", matrice.celulaCurenta.getX(), matrice.celulaCurenta.getY());
    }


}
