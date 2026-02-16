package flow;

import flow.exceptii.AccountNotFound;
import flow.exceptii.ImpossibleMove;
import flow.exceptii.IncorrectPassword;
import flow.exceptii.InvalidCommandException;
import flow.map.Cell;

import java.util.Random;

public class Test {
    public static void main(String[] args){

        boolean test = true;
        if(test){
            Game joc = Game.getInstace(5, 5);
            joc.getHartaJoc().get(0).get(0).setTipEntitate(Cell.CellEntityType.PLAYER);
            joc.getHartaJoc().get(0).get(1).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(0).get(2).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(0).get(3).setTipEntitate(Cell.CellEntityType.SANCTUARY);
            joc.getHartaJoc().get(0).get(4).setTipEntitate(Cell.CellEntityType.VOID);

            joc.getHartaJoc().get(1).get(0).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(1).get(1).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(1).get(2).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(1).get(3).setTipEntitate(Cell.CellEntityType.SANCTUARY);
            joc.getHartaJoc().get(1).get(4).setTipEntitate(Cell.CellEntityType.VOID);

            joc.getHartaJoc().get(2).get(0).setTipEntitate(Cell.CellEntityType.SANCTUARY);
            joc.getHartaJoc().get(2).get(1).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(2).get(2).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(2).get(3).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(2).get(4).setTipEntitate(Cell.CellEntityType.VOID);

            joc.getHartaJoc().get(3).get(0).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(3).get(1).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(3).get(2).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(3).get(3).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(3).get(4).setTipEntitate(Cell.CellEntityType.ENEMY);

            joc.getHartaJoc().get(4).get(0).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(4).get(1).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(4).get(2).setTipEntitate(Cell.CellEntityType.VOID);
            joc.getHartaJoc().get(4).get(3).setTipEntitate(Cell.CellEntityType.SANCTUARY);
            joc.getHartaJoc().get(4).get(4).setTipEntitate(Cell.CellEntityType.PORTAL);

            joc.getHartaJoc().setCelulaCurenta(joc.getHartaJoc().get(0).get(0));
            joc.run();

            //joc.getHartaJoc().afisare();

            while (!joc.isStopped()) {

                try {
                    joc.mutare();
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //joc.getHartaJoc().afisare();

            }
        }else {
            Random r = new Random();

            Game joc = Game.getInstace(r.nextInt(8) + 3, r.nextInt(8) + 3);

            joc.run();



            //joc.getHartaJoc().afisare();

            while (!joc.isStopped()) {

                try {
                    joc.mutare();
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                //joc.getHartaJoc().afisare();

            }
        }


    }
}