package flow;

import flow.entities.characters.*;
import flow.entities.characters.Character;
import flow.entities.spells.Earth;
import flow.entities.spells.Fire;
import flow.entities.spells.Spell;
import flow.exceptii.*;
import flow.initialization.Account;
import flow.initialization.JsonInput;
import flow.map.Cell;
import flow.map.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static flow.map.Cell.CellEntityType.*;
import static java.lang.Thread.sleep;

public class Game extends JFrame {
    private static JFrame frameAlegereJucator;
    private static JFrame frameLupta;
    private static JFrame frameAbilitati;
    private ArrayList<Account> listaConturi;
    private Grid hartaJoc;
    private Account contCurent;
    private Entity inamic;
    private boolean inLupta = false;

    private Character player;

    private boolean stopped;

    private static Game game = null;

    int inaltimeButonJucator = 50;

    JPanel infoJucator = new JPanel();
    JPanel jucatori = new JPanel();


    public boolean isStopped() {
        return stopped;
    }

    private Game(int lungimeTable, int latimeTable) {
        hartaJoc = Grid.generareHarta(lungimeTable, latimeTable);
        hartaJoc.afisare();
        listaConturi = new ArrayList<>();
        stopped = false;
        System.out.println(lungimeTable + " " + latimeTable);
        logareGUI();
    }

    public static Game getInstace(int lungimeTable, int latimeTable) {
        if (game == null) {
            game = new Game(lungimeTable, latimeTable);
        }
        return game;
    }


    public void logareGUI(){
        JFrame frameLogare = new JFrame("Logare");
        frameLogare.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLogare.setSize(400, 200); // Setează dimensiunea ferestrei
        frameLogare.setLocationRelativeTo(null); // Centrarea ferestrei

        // Setează layout-ul la GridBagLayout
        frameLogare.setLayout(new GridBagLayout());

        JLabel labelEmail = new JLabel("Introduce e-mailul:");
        JTextField emailText = new JTextField(20);

        JLabel labelParola = new JLabel("Introduce parola: ");
        JPasswordField parolaText = new JPasswordField(20);

        JButton buttonLogare = new JButton("Login");

        emailText.setPreferredSize(new Dimension(200, 25));
        parolaText.setPreferredSize(new Dimension(200, 25));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        frameLogare.add(labelEmail, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        frameLogare.add(emailText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frameLogare.add(labelParola, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        frameLogare.add(parolaText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        frameLogare.add(buttonLogare, gbc);

        frameLogare.setVisible(true);

        buttonLogare.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailText.getText();
                char[] parola = parolaText.getPassword();
                if(game.verificareEmail(email) && game.verificareParola(new String(parola))){
                    frameLogare.dispose();

                    // se afiseaza jucatorii disponibili
                    afisareJucatori();
                }
            }
        });
    }

    public boolean verificareEmail(String email){
        listaConturi = JsonInput.deserializeAccounts();
        for (Account cont : listaConturi) {
            if (email.equals(cont.getInfoJucator().getCredJucator().getEmail())) {
                contCurent = cont;
                break;
            }
        }
        if(contCurent == null){
            return false;
        }
        return true;
    }

    public void afisareJucatori(){
        frameAlegereJucator = new JFrame("Joc");
        frameAlegereJucator.setTitle("Alegere Jucatori");
        frameAlegereJucator.setPreferredSize(new Dimension(500, 500));
        frameAlegereJucator.setSize(500, 500);
        frameAlegereJucator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAlegereJucator.setLocationRelativeTo(null);
        frameAlegereJucator.setLayout(new BorderLayout());
        frameAlegereJucator.setVisible(true);


        //jucatori.setBackground(Color.YELLOW);

        infoJucator.setPreferredSize(new Dimension(500, 100));

        adaugareInfoPlayer();
        frameAlegereJucator.add(infoJucator, BorderLayout.NORTH);
        adaugareJucatori();

        //frame.add(scrollPane);



    }

    public void adaugareInfoPlayer() {
        infoJucator.removeAll();

        JTextArea username = new JTextArea();
        username.setBounds(0, 0, 500, 100);
        username.setEditable(false);
        username.setFont(new Font("Arial", Font.BOLD, 13));
        username.setText("Email: " + contCurent.getInfoJucator().getCredJucator().getEmail() +
                "\nBine ai venit, " + contCurent.getInfoJucator().getNumeJucator() + "!\n" +
                contCurent.getInfoJucator().getNumeJucator() + ", ai numarul de harti complete: "
                + contCurent.getNrJocuri() + "\nAcestia sunt jucatorii din care iti poti alege! \n\n" +
                "Apasa pe jucatorul cu care doresti sa joci!");


        infoJucator.add(username);
    }

    public void adaugareJucatori() {
        jucatori.removeAll();

        jucatori.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jucatori.setPreferredSize(new Dimension(500, inaltimeButonJucator * contCurent.getListaPersonaje().size()));

        //scrollPane.setViewportView(jucatori);
        //jucatori.add(scrollPane);
        for (Character personaj : contCurent.getListaPersonaje()) {

            JLabel rand1 = new JLabel("Numele personajului: " + personaj.getNumePersonaj());
            JLabel rand2 = new JLabel("Experienta: " + personaj.getExperienta() + "   Nivel: " + personaj.getNivelCurentPersonaj());
            JButton buton = new JButton();
            JPanel panelText = new JPanel(new GridLayout(2, 1, 0, 2));
            panelText.add(rand1);
            panelText.add(rand2);
            buton.setLayout(new BorderLayout());
            buton.add(panelText, BorderLayout.CENTER);
            buton.setPreferredSize(new Dimension(500 - 50, inaltimeButonJucator));

            buton.setBorderPainted(false);

            buton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   player = personaj;
                   player.adaugaViata(player.getViataMax());
                   player.adaugaMana(player.getManaMax());
                   afisareEcranPrincipal();
               }
            });

            jucatori.add(buton);

        }
        frameAlegereJucator.add(jucatori, BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(jucatori);
        scrollPane.setPreferredSize(new Dimension(500, inaltimeButonJucator * contCurent.getListaPersonaje().size()));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        frameAlegereJucator.add(scrollPane, BorderLayout.CENTER);
        frameAlegereJucator.setVisible(true);
        //jucatori.revalidate();
        //jucatori.repaint();
        //jucatori.add(scrollPane);
        //frame.add(scrollPane, BorderLayout.CENTER);
    }

    public void afisareEcranPrincipal() {
        //if (inLupta) return;
        frameAlegereJucator.setEnabled(true);
        frameAlegereJucator.setVisible(true);
        frameAlegereJucator.getContentPane().removeAll();

        frameAlegereJucator.setSize(new Dimension(150 + hartaJoc.getLungimeTabla() * 65, Math.max(hartaJoc.getLatimeTabla() * 65, 350)));

        frameAlegereJucator.setLayout(new BorderLayout());

        JPanel comenzi = new JPanel();
        JPanel harta = new JPanel();

        comenzi.setPreferredSize(new Dimension(150, hartaJoc.getLatimeTabla() * 65));
        harta.setPreferredSize(new Dimension(hartaJoc.getLungimeTabla() * 65,  hartaJoc.getLatimeTabla() * 65));

        afisareHarta(harta);
        afisareComenzi(comenzi);

        frameAlegereJucator.add(comenzi, BorderLayout.WEST);
        frameAlegereJucator.add(harta, BorderLayout.EAST);

        frameAlegereJucator.revalidate();
        frameAlegereJucator.repaint();
    }

    public void afisareComenzi(JPanel comenzi) {
        comenzi.removeAll();

        comenzi.setLayout(new BorderLayout());

        // Panel pentru informațiile jucătorului (sus)
        JPanel infoJucatorPanel = new JPanel();
        infoJucatorPanel.setLayout(new GridLayout(3, 2, 5, 5));
        infoJucatorPanel.setBackground(Color.LIGHT_GRAY);

        JLabel numePersonaj = new JLabel("Numele: " + player.getNumePersonaj());
        JLabel nivelPersonaj = new JLabel("Nivel: " + player.getNivelCurentPersonaj());
        JLabel experientaPersonaj = new JLabel("Experiență: " + player.getExperienta());

        infoJucatorPanel.add(numePersonaj);
        infoJucatorPanel.add(nivelPersonaj);
        infoJucatorPanel.add(experientaPersonaj);

        JPanel manaSanatatePanel = new JPanel();
        manaSanatatePanel.setLayout(new GridLayout(2, 1, 5, 5)); // 2 rânduri
        manaSanatatePanel.setBackground(Color.WHITE);

        JLabel manaLabel = new JLabel("Mana: " + player.getMana() + "/" + player.getManaMax());
        JLabel sanatateLabel = new JLabel("Viata: " + player.getViata() + "/" + player.getViataMax());

        manaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sanatateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        manaSanatatePanel.add(sanatateLabel);
        manaSanatatePanel.add(manaLabel);

        JPanel butoanePanel = new JPanel();
        butoanePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(2, 2, 2, 2); // Spațiere între butoane

        JButton butonNord = new JButton("Nord");
        butonNord.setPreferredSize(new Dimension(100, 20));

        butonNord.setBackground(Color.CYAN); // Fundal Cyan
        butonNord.setOpaque(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        butoanePanel.add(butonNord, gbc);
        butonNord.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    hartaJoc.goNorth();

                } catch (ImpossibleMove ex) {
                    JOptionPane.showMessageDialog(null, "Nu se poate face mutare in sus!",
                            "Imposibil de mutat", JOptionPane.WARNING_MESSAGE);
                }
                verificareCelula();
                System.out.println(hartaJoc.getCelulaCurenta().getX() + " " + hartaJoc.getCelulaCurenta().getY());


            }
        });


        JButton butonSud = new JButton("Sud");
        butonSud.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        butoanePanel.add(butonSud, gbc);
        butonSud.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    hartaJoc.goSouth();

                } catch (ImpossibleMove ex) {
                    JOptionPane.showMessageDialog(null, "Nu se poate face mutare in jos!",
                            "Imposibil de mutat", JOptionPane.WARNING_MESSAGE);
                }
                verificareCelula();
            }
        });

        JButton butonEst = new JButton("Est");
        butonEst.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        butoanePanel.add(butonEst, gbc);
        butonEst.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    hartaJoc.goEast();

                } catch (ImpossibleMove ex) {
                    JOptionPane.showMessageDialog(null, "Nu se poate face mutare la dreapta!",
                            "Imposibil de mutat", JOptionPane.WARNING_MESSAGE);
                }
                verificareCelula();
            }
        });

        JButton butonVest = new JButton("Vest");
        butonVest.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        butoanePanel.add(butonVest, gbc);
        butonVest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                    hartaJoc.goWest();

                } catch (ImpossibleMove ex) {
                    JOptionPane.showMessageDialog(null, "Nu se poate face mutarea la stanga!",
                            "Imposibil de mutat", JOptionPane.WARNING_MESSAGE);
                }
                verificareCelula();
            }
        });

        comenzi.add(infoJucatorPanel, BorderLayout.NORTH);
        comenzi.add(manaSanatatePanel, BorderLayout.CENTER);
        comenzi.add(butoanePanel, BorderLayout.SOUTH);
        comenzi.revalidate();
        comenzi.repaint();

    }

    public void verificareCelula(){
        afisareEcranPrincipal();
        if(hartaJoc.getCelulaAnterioara().getTipEntitate().equals(ENEMY)){
            inamic = new Enemy(player.getNivelCurentPersonaj());
            player.startLupta();
            inamic.startLupta();
            inLupta = true;

            afisareEcranAlegereLupta(inamic);
        } else if (hartaJoc.getCelulaAnterioara().getTipEntitate().equals(PORTAL)){
            frameAlegereJucator.setVisible(false);
            frameAlegereJucator.getContentPane().removeAll();
            afisareEcranFinalLevelTerminat();

            //frameAlegereJucator.dispose();
        } else if(hartaJoc.getCelulaAnterioara().getTipEntitate().equals(SANCTUARY)){
            if(player.getViata() == player.getViataMax() && player.getMana() == player.getManaMax()){
                JOptionPane.showMessageDialog(null,
                        "Ai ajuns pe un sanctuar, dar viata si mana sunt deja maxime!",
                        "Sanctuar", JOptionPane.WARNING_MESSAGE);
            } else {
                Random rand = new Random();
                int viataCurenta = player.getViata();
                int v = rand.nextInt(player.getViataMax() - viataCurenta + 1);
                System.out.println("     +" + v + " pct viata!! :)");
                player.adaugaViata(v);

                int manaCurenta = player.getMana();
                int m =  rand.nextInt(player.getManaMax() - manaCurenta + 1);
                System.out.println("     +" + m + " pct mana!! :)");
                player.adaugaMana(m);

                JOptionPane.showMessageDialog(null, "Ai ajuns pe un sanctuar si ai castigat "
                                + v + " pct de viata si " + m + " pct de mana!",
                        "Sanctuar", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void afisareHarta(JPanel harta) {
        harta.removeAll();

        int lungimeTabla = hartaJoc.getLungimeTabla();
        int latimeTabla = hartaJoc.getLatimeTabla();

        // Setăm layout-ul pentru a permite un control mai detaliat
        harta.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE; // Butoanele nu vor fi redimensionate automat
        gbc.insets = new Insets(2, 2, 2, 2); // Spațiere între butoane

        System.out.println(lungimeTabla);
        System.out.println(latimeTabla);
        ImageIcon iNedescoperit = new ImageIcon("./src/flow/imagini/nevizitat.jpg");
        Image img = iNedescoperit.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iNedescoperit = new ImageIcon(img);

        ImageIcon iPlayer = new ImageIcon("./src/flow/imagini/avatar.jpg");
        img = iPlayer.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iPlayer = new ImageIcon(img);

        ImageIcon iSanctuar = new ImageIcon("./src/flow/imagini/sanctuar.jpg");
        img = iSanctuar.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iSanctuar = new ImageIcon(img);

        ImageIcon iPortal = new ImageIcon("./src/flow/imagini/portal.jpg");
        img = iPortal.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iPortal = new ImageIcon(img);

        ImageIcon iInamic = new ImageIcon("./src/flow/imagini/lupta.jpg");
        img = iInamic.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iInamic = new ImageIcon(img);

        ImageIcon iVoid = new ImageIcon("./src/flow/imagini/void.jpg");
        img = iVoid.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iVoid = new ImageIcon(img);

        // Crearea matricei de butoane
        for (int i = 0; i < latimeTabla; i++) {
            for (int j = 0; j < lungimeTabla; j++) {
                Cell casuta = hartaJoc.get(i).get(j);
                JButton buton = null;
                if(casuta.getTipEntitate().equals(PLAYER))
                    buton = new JButton(iPlayer);
                else if(casuta.isEsteVizitat() == false)
                    buton = new JButton(iNedescoperit);
                else if(casuta.getTipEntitate().equals(SANCTUARY))
                    buton = new JButton(iSanctuar);
                else if(casuta.getTipEntitate().equals(VOID))
                    buton = new JButton(iVoid);
                else if(casuta.getTipEntitate().equals(ENEMY)) {
                    buton = new JButton(iInamic);

                    /*inamic = new Enemy(player.getNivelCurentPersonaj());
                    player.startLupta();
                    inamic.startLupta();
                    inLupta = true;

                    afisareEcranAlegereLupta(inamic);*/

                }


                else if(casuta.getTipEntitate().equals(PORTAL))
                    buton = new JButton(iPortal);

                buton.setBorderPainted(false);
                buton.setContentAreaFilled(false);
                // Setăm dimensiunea explicită a butonului
                buton.setPreferredSize(new Dimension(50, 50));

                buton.addActionListener(e -> System.out.println(casuta));

                // Configurăm poziția butonului în grilă
                gbc.gridx = j;
                gbc.gridy = i;

                harta.add(buton, gbc);
            }
        }
        harta.revalidate();
        harta.repaint();
    }

    public boolean verificareParola(String parola){
        if (!parola.equals(contCurent.getInfoJucator().getCredJucator().getPassword())) {
            return false;
        }
        return true;
    }


    //FEREASTRA PENTRU ALEGEREA MODULUI DE ATAC
    public void afisareEcranAlegereLupta(Entity inamic) {
        // se pune fereastra cu harta sa fie inactiva
        frameAlegereJucator.setEnabled(false); // inutil pentru ca se creaza oricum alt frame


        frameLupta = new JFrame("Atac");
        frameLupta.setTitle("Alegere mod atac");
        frameLupta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLupta.setPreferredSize(new Dimension(500, 500));
        frameLupta.setSize(500, 500);
        frameLupta.setLocationRelativeTo(null);

        // impart frame ul principal in 3 coloane si un rand
        // prima coloana pentru imaginea jucatorului si informatii mana si viata
        // a doua coloana pentru butoanele prin care alege modul de atac
        // a treia coloana pentru imaginea inamicului si informatii mana si viata
        frameLupta.setLayout(new GridLayout(1, 3, 0, 0));

        JPanel panelJucator = new JPanel();
        JPanel panelAlegereModAtac = new JPanel();
        JPanel panelInamic = new JPanel();

        panelJucator.setPreferredSize(new Dimension(200, 500));
        panelAlegereModAtac.setPreferredSize(new Dimension(100, 500));
        panelInamic.setPreferredSize(new Dimension(200, 500));

        afisareJucator(panelJucator);
        afisareModAtac(panelAlegereModAtac);
        afisareInamic(panelInamic, inamic);

        frameLupta.add(panelJucator);
        frameLupta.add(panelAlegereModAtac);
        frameLupta.add(panelInamic);
        frameLupta.pack();
        frameLupta.setVisible(true);
    }

    public void afisareJucator(JPanel panelJucator) {
        // impart panelul jucatorului in 2 randuri si o coloana
        // primul rand pentru poza
        // al doilea rand pentru informatii viata si mana
        panelJucator.setLayout(new BorderLayout(5, 5));

        //construiesc panelul pentru imaginea jucatorului
        JPanel pozaJucator = new JPanel();
        pozaJucator.setPreferredSize(new Dimension(200, 400));

        String path = null;
        if(player instanceof Warrior)
            path = "./src/flow/imagini/warrior.jpg";
        else if (player instanceof Mage)
            path = "./src/flow/imagini/mage.jpg";
        else path = "./src/flow/imagini/rogue.jpg";
        ImageIcon imagineJucator = new ImageIcon(path);
        Image img = imagineJucator.getImage().getScaledInstance(200, 400, Image.SCALE_SMOOTH);
        imagineJucator = new ImageIcon(img);

        JLabel labelJucator = new JLabel(imagineJucator);
        pozaJucator.add(labelJucator);
        panelJucator.add(pozaJucator, BorderLayout.CENTER);

        //construiesc panelul pentru informatiile despre jucator
        JPanel infoJucator = new JPanel();
        infoJucator.setPreferredSize(new Dimension(200, 100));

        JLabel viata = new JLabel("Viata player: " + player.getViata() + "/" + player.getViataMax());
        JLabel mana = new JLabel("Mana player: " + player.getMana() + "/" + player.getManaMax());
        infoJucator.add(viata);
        infoJucator.add(mana);
        panelJucator.add(infoJucator, BorderLayout.SOUTH);

    }

    public void afisareModAtac(JPanel panelAlegereModAtac) {
        panelAlegereModAtac.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        JButton butonAtac = new JButton();
        butonAtac.setLayout(new BorderLayout(5, 5));
        JLabel l1 = new JLabel("Atac", JLabel.CENTER);
        JLabel l2 = new JLabel("Normal", JLabel.CENTER);
        butonAtac.add(l1, BorderLayout.NORTH);
        butonAtac.add(l2, BorderLayout.SOUTH);
        butonAtac.setPreferredSize(new Dimension(100, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        // buton pentru atac normal
        butonAtac.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int damage = player.getDamage();

                inamic.receiveDamage(damage);
                JOptionPane.showMessageDialog(null, "Ai atacat inamicul prin atac normal! I-ai dat damage " + damage +
                                "!\n Sunt 50% sanse ca damge-ul sa fie injumatatit!",
                            "Atac player", JOptionPane.WARNING_MESSAGE);
                frameLupta.dispose();
                frameLupta = null;
                afisareEcranAlegereLupta(inamic);

                if(inamic.getViata() <= 0){
                    JOptionPane.showMessageDialog(null, "Inamicul a fost invins",
                            "Lupta gata", JOptionPane.WARNING_MESSAGE);
                    frameLupta.setVisible(false);

                    frameLupta.dispose();
                    frameLupta = null;
                    player.castigaLupta();
                    inLupta = false;

                    try{
                        hartaJoc.goNorth();
                    } catch (ImpossibleMove ex) {
                        try {
                            hartaJoc.goSouth();
                        } catch (ImpossibleMove exc) {
                            try {
                                hartaJoc.goEast();
                            } catch (ImpossibleMove impossibleMove) {
                                try {
                                    hartaJoc.goWest();
                                } catch (ImpossibleMove move) {
                                    throw new RuntimeException(move);
                                }
                            }
                        }
                    }
                    afisareEcranPrincipal();
                } else{
                    atacInamic(inamic);
                    frameLupta.dispose();
                    frameLupta = null;
                    afisareEcranAlegereLupta(inamic);
                }

                if (player.getViata() <= 0) {
                    System.out.println();
                    System.out.println("Ai fost invins! ");
                    System.out.println("GAME OVER!!! :(((");
                    System.out.println();
                    inLupta = false;
                    afisareEcranFinalPierdere();
                    //lupta = false;
                    player = null;

                    Random rand = new Random();

                    //Grid.generareHarta(rand.nextInt(8) + 3, rand.nextInt(8) + 3);
                    //alegereCaracter();
                }

            }
        });

        panelAlegereModAtac.add(butonAtac, gbc);

        // nu incape tot scrisul pe buton asa ca atrebuit sa l compun din 2 randuri
        JButton butonAbilitate = new JButton();
        butonAbilitate.setLayout(new BorderLayout(5, 5));
        l1 = new JLabel("Foloseste", JLabel.CENTER);
        l2 = new JLabel("Abilitate", JLabel.CENTER);
        butonAbilitate.add(l1, BorderLayout.NORTH);
        butonAbilitate.add(l2, BorderLayout.SOUTH);
        butonAbilitate.setPreferredSize(new Dimension(100, 50));
        gbc.gridy = 1; // Linie următoare

        butonAbilitate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // se verifica daca playerul are destula mana pentru orice spell
                if(player.abilitati.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Nu mai ai abilitati pe care le poti folosi!",
                            "Abilitati indisponibile", JOptionPane.WARNING_MESSAGE);
                } else {
                    int manaMinim = 10000;
                    for (Spell s: player.abilitati) {
                        manaMinim = Math.min(manaMinim, s.costMana);
                    }
                    if (manaMinim <= player.getMana()) {
                        afisareEcranAlegereAbilitate();
                    } else {

                        JOptionPane.showMessageDialog(null, "Nu ai destula mana pentru niciun spell. Ai nevoie de minim: " + manaMinim + "!",
                                "Mana insuficient", JOptionPane.WARNING_MESSAGE);
                        JPanel panel = new JPanel();
                        panel.setPreferredSize(new Dimension(100, 500));
                        afisareModAtac(panel);
                    }
                }
            }
        });
        panelAlegereModAtac.add(butonAbilitate, gbc);
    }

    public void atacInamic(Entity inamic) {
        if (inamic.sansa() && !inamic.abilitati.isEmpty()) {
            // alege random un spell
            Random rand = new Random();
            rand.setSeed(System.currentTimeMillis());

            int pozitie = rand.nextInt(inamic.abilitati.size());

            if (inamic.abilitati.get(pozitie).costMana <= inamic.mana) {

               /* inamic.folosireAbilitate(player, inamic.abilitati.get(pozitie));*/
                Spell abilitate = inamic.abilitati.get(pozitie);
                inamic.setMana(inamic.getMana() - abilitate.costMana);
                player.accept(abilitate);
                JOptionPane.showMessageDialog(null, "Inamicul te a atacat prin abilitatea "
                                + inamic.abilitati.get(pozitie).toString(),
                        "Atac inamic", JOptionPane.WARNING_MESSAGE);
                inamic.abilitati.remove(abilitate);

            } else {
                JOptionPane.showMessageDialog(null,
                        "Inamicul nu are destula mana pentru a folosi abilitatea "
                                + inamic.abilitati.get(pozitie).toString(),
                        "Atac inamic", JOptionPane.WARNING_MESSAGE);
            }



        } else {
            int damage = inamic.getDamage();
            JOptionPane.showMessageDialog(null, "Inamicul te a atacat prin atac normal!" +
                            " Ti-a dat damage " + damage + "!",
                    "Atac inamic", JOptionPane.WARNING_MESSAGE);
            player.receiveDamage(damage);
        }
    }

    public void afisareEcranAlegereAbilitate(){

        // se seteaza frameul de atac ca fiind inactiv
        frameLupta.setEnabled(false);

        frameAbilitati = new JFrame("Abilitati");
        frameAbilitati.setTitle("Alegere Abilitate");
        frameAbilitati.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameAbilitati.setPreferredSize(new Dimension(500, 500));
        frameAbilitati.setSize(500, 500);
        frameAbilitati.setLocationRelativeTo(null);

        frameAbilitati.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        //frame3.setLayout(new GridLayout(0, 1, 10, 10));

        for (Spell s : player.abilitati) {
            JPanel abilitatePanel = new JPanel();
            abilitatePanel.setLayout(new BorderLayout(5, 5));
            abilitatePanel.setPreferredSize(new Dimension(150, 150));

            String path = null;
            if (s instanceof Earth)
                path = "./src/flow/imagini/earth.jpg";
            else if (s instanceof Fire)
                path = "./src/flow/imagini/fire.jpg";
            else
                path = "./src/flow/imagini/ice.jpg";

            ImageIcon imagineAbil = new ImageIcon(path);
            Image img = imagineAbil.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagineAbil = new ImageIcon(img);

            JButton butonImagine = new JButton(imagineAbil);
            butonImagine.setPreferredSize(new Dimension(100, 100));
            butonImagine.setFocusPainted(false);

            butonImagine.addActionListener(e -> {
                if (player.getMana() < s.costMana) {
                    JOptionPane.showMessageDialog(frameAbilitati, "Nu ai destula mana pentru abilitatea: " + s.getClass().getSimpleName(),
                            "Mana insuficient", JOptionPane.WARNING_MESSAGE);
                    frameAbilitati.setVisible(false);
                    //DACA NU ARE SUFICIENTA MANA PENTRU ABILITATEA ALEASA ATUNCI VA FOLOSI ATAC NORMAL
                } else{
                    player.abilitati.remove(s);
                    player.setMana(player.getMana() - s.costMana);
                    inamic.accept(s);
                    /*player.folosireAbilitate(inamic, s);*/
                    JOptionPane.showMessageDialog(null, "Ai atacat folosind abilitatea:  " + s.toString(),
                            "Atac player", JOptionPane.WARNING_MESSAGE);
                    frameAbilitati.setVisible(false);
                    frameAbilitati.dispose();
                    frameLupta.dispose();
                    frameLupta = null;
                    afisareEcranAlegereLupta(inamic);
                    if(inamic.getViata() <= 0){
                        JOptionPane.showMessageDialog(null, "Inamicul a fost invins",
                                "Lupta gata", JOptionPane.WARNING_MESSAGE);
                        inLupta = false;
                        frameLupta.setVisible(false);
                        frameLupta.dispose();
                        frameLupta = null;
                        player.castigaLupta();

                        try{
                            hartaJoc.goNorth();
                        } catch (ImpossibleMove ex) {
                            try {
                                hartaJoc.goSouth();
                            } catch (ImpossibleMove exc) {
                                try {
                                    hartaJoc.goEast();
                                } catch (ImpossibleMove impossibleMove) {
                                    try {
                                        hartaJoc.goWest();
                                    } catch (ImpossibleMove move) {
                                        throw new RuntimeException(move);
                                    }
                                }
                            }
                        }
                        afisareEcranPrincipal();
                    } else {
                        atacInamic(inamic);
                        if(player.getViata() <= 0) {
                            JOptionPane.showMessageDialog(null, "Ai fost invins!",
                                    "Jocul s a terminat", JOptionPane.WARNING_MESSAGE);
                            inLupta = false;
                            afisareEcranFinalPierdere();
                            //lupta = false;
                            player = null;

                        }

                    }

                }
            });

            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            JLabel manaLabel = new JLabel("Cost Mana: " + s.costMana);
            JLabel damageLabel = new JLabel("Damage: " + s.damage);
            manaLabel.setHorizontalAlignment(SwingConstants.CENTER);
            damageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoPanel.add(manaLabel);
            infoPanel.add(damageLabel);


            abilitatePanel.add(butonImagine, BorderLayout.CENTER);
            abilitatePanel.add(infoPanel, BorderLayout.SOUTH);

            frameAbilitati.add(abilitatePanel);
        }

        frameAbilitati.pack();
        frameAbilitati.setVisible(true);

    }

    public void afisareInamic(JPanel panelInamic, Entity inamic) {
        panelInamic.setLayout(new BorderLayout(5, 5));

        //construiesc panelul pentru imaginea jucatorului
        JPanel pozaJucator = new JPanel();
        pozaJucator.setPreferredSize(new Dimension(200, 400));

        ImageIcon imagineJucator = new ImageIcon("./src/flow/imagini/imgWarrior.jpg");
        Image img = imagineJucator.getImage().getScaledInstance(200, 400, Image.SCALE_SMOOTH);
        imagineJucator = new ImageIcon(img);

        JLabel labelJucator = new JLabel(imagineJucator);
        pozaJucator.add(labelJucator);
        panelInamic.add(pozaJucator, BorderLayout.CENTER);

        //construiesc panelul pentru informatiile despre jucator
        JPanel infoJucator = new JPanel();
        infoJucator.setPreferredSize(new Dimension(200, 100));

        JLabel viata = new JLabel("Viata inamic: " + inamic.getViata() + "/" + inamic.getViataMax());
        JLabel mana = new JLabel("Mana inamic: " + inamic.getMana() + "/" + inamic.getManaMax());
        infoJucator.add(viata);
        infoJucator.add(mana);
        panelInamic.add(infoJucator, BorderLayout.SOUTH);

    }


    public void afisareEcranFinalPierdere(){
        JFrame frameFinalPierdere = new JFrame("Final Pierdere");
        frameFinalPierdere.setTitle("Final Pierdere");
        frameFinalPierdere.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameFinalPierdere.setPreferredSize(new Dimension(500, 700));

        frameFinalPierdere.setLayout(new GridLayout(3, 1));

        JPanel panelImagineJucator = new JPanel();
        panelImagineJucator.setPreferredSize(new Dimension(500, 400));


        String path = null;
        if(player instanceof Warrior)
            path = "./src/flow/imagini/warrior.jpg";
        else if (player instanceof Mage)
            path = "./src/flow/imagini/mage.jpg";
        else path = "./src/flow/imagini/rogue.jpg";
        ImageIcon imagineJucator = new ImageIcon(path);
        Image img = imagineJucator.getImage().getScaledInstance(200, 400, Image.SCALE_SMOOTH);
        imagineJucator = new ImageIcon(img);
        JLabel labelImagine = new JLabel(imagineJucator);
        panelImagineJucator.add(labelImagine);
        frameFinalPierdere.add(panelImagineJucator, BorderLayout.NORTH);

        JPanel panelInfoJucator = new JPanel();
        panelInfoJucator.setPreferredSize(new Dimension(500, 200));

        JTextArea textInfo = new JTextArea("Ai pierdut! Ai sansa sa-ti alegi un nou personaj si sa incepi din nou!\n\n" +
                "Nume jucator: " + player.getNumePersonaj() +
                "\n Tip jucator: " + player.getClass().getSimpleName() + "\n" +
                "Nivel Jucator: " + player.getNivelCurentPersonaj());
        textInfo.setEditable(false);
        panelInfoJucator.add(textInfo);
        frameFinalPierdere.add(panelInfoJucator, BorderLayout.CENTER);

        JPanel butoane = new JPanel();
        butoane.setPreferredSize(new Dimension(500, 100));
        JButton button1 = new JButton("Alege jucator si incepe joc nou");

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frameAlegereJucator.setVisible(false);
                frameAlegereJucator.dispose();
                frameLupta.setVisible(false);
                frameLupta.dispose();
                afisareJucatori();
                Random rand = new Random();
                hartaJoc = Grid.generareHarta(rand.nextInt(8) + 3, rand.nextInt(8) + 3);
                //frameAlegereJucator.getContentPane().removeAll();


                frameFinalPierdere.dispose();
            }
        });
        button1.setPreferredSize(new Dimension(500, 50));

        JButton button2 = new JButton("Exit");
        button2.setPreferredSize(new Dimension(200, 50));

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopped = true;
                player = null;
                System.exit(0);
            }
        });
        butoane.add(button1);
        butoane.add(button2);

        frameFinalPierdere.add(butoane, BorderLayout.SOUTH);

        frameFinalPierdere.pack();
        frameFinalPierdere.setVisible(true);

    }

    public void afisareEcranFinalLevelTerminat(){
        JFrame frameFinalLevelNou = new JFrame("Final LevelNou");
        frameFinalLevelNou.setTitle("Final LevelNou");
        frameFinalLevelNou.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameFinalLevelNou.setPreferredSize(new Dimension(500, 700));

        frameFinalLevelNou.setLayout(new GridLayout(3, 1));

        JPanel panelImagineJucator = new JPanel();
        panelImagineJucator.setPreferredSize(new Dimension(500, 400));


        String path = null;
        if(player instanceof Warrior)
            path = "./src/flow/imagini/warrior.jpg";
        else if (player instanceof Mage)
            path = "./src/flow/imagini/mage.jpg";
        else path = "./src/flow/imagini/rogue.jpg";
        ImageIcon imagineJucator = new ImageIcon(path);
        Image img = imagineJucator.getImage().getScaledInstance(200, 400, Image.SCALE_SMOOTH);
        imagineJucator = new ImageIcon(img);
        JLabel labelImagine = new JLabel(imagineJucator);
        panelImagineJucator.add(labelImagine);
        frameFinalLevelNou.add(panelImagineJucator, BorderLayout.NORTH);

        JPanel panelInfoJucator = new JPanel();
        panelInfoJucator.setPreferredSize(new Dimension(500, 200));

        //modificari care se fac cand jucatorul trece la urmatoarea harta
        contCurent.setNrJocuri(contCurent.getNrJocuri() + 1);
        int nivelPersonaj = player.getNivelCurentPersonaj();
        player.setNivelCurentPersonaj(nivelPersonaj + 1);
        player.setExperienta(player.getExperienta() + nivelPersonaj * 5);

        JTextArea textInfo = new JTextArea("Felicitari! Ai ajuns pe un portal si vei trece la urmatorul nivel!\n" +
                "Ai castigat " + nivelPersonaj * 5 + " xp!\n"+
                "Nume jucator: " + player.getNumePersonaj() +
                "\n Tip jucator: " + player.getClass().getSimpleName() + "\n" +
                "Nivel Jucator: " + player.getNivelCurentPersonaj() +
                "\n\n" + contCurent.getInfoJucator().getNumeJucator() +
                " acum ai " + contCurent.getNrJocuri() + " harti complete!");
        textInfo.setEditable(false);
        panelInfoJucator.add(textInfo);
        frameFinalLevelNou.add(panelInfoJucator, BorderLayout.CENTER);

        JPanel butoane = new JPanel();
        butoane.setPreferredSize(new Dimension(500, 100));
        JButton button1 = new JButton("Treci la urmatoarea harta");

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                Random rand = new Random();

                hartaJoc = Grid.generareHarta(rand.nextInt(8) + 3, rand.nextInt(8) + 3);
                afisareEcranPrincipal();

                frameFinalLevelNou.dispose();
            }
        });
        button1.setPreferredSize(new Dimension(500, 50));

        JButton button2 = new JButton("Exit");
        button2.setPreferredSize(new Dimension(200, 50));

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopped = true;
                System.exit(0);
            }
        });
        butoane.add(button1);
        butoane.add(button2);

        frameFinalLevelNou.add(butoane, BorderLayout.SOUTH);

        frameFinalLevelNou.pack();
        frameFinalLevelNou.setVisible(true);
        frameFinalLevelNou.revalidate();
        frameFinalLevelNou.repaint();
    }

    public Grid getHartaJoc() {
        return hartaJoc;
    }

    public void mutare() throws InvalidCommandException, ImpossibleMove {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nivel viata curenta: " + player.getViata() + " / " + player.getViataMax());
        System.out.println("Nivel mana curenta: " + player.getMana() + " / " + player.getManaMax());
        System.out.println();
        hartaJoc.afisare();

        System.out.println("In ce directie doriti sa mergeti?");
        System.out.println("w - mutare in sus ");
        System.out.println("s - mutare in jos ");
        System.out.println("d - mutare in dreapta");
        System.out.println("a - mutare in stanga");

        System.out.println("exit pentru a iesi din joc");

        String mutare = scanner.nextLine().toLowerCase();

        if (mutare.equals("exit")) {
            //stopped = true;
            player = null;
            alegereCaracter();
            Random rand = new Random();

            Grid.generareHarta(rand.nextInt(8) + 3, rand.nextInt(8) + 3);
            return;
        } else if(mutare.equals("w")) {
            hartaJoc.goNorth();
        } else if(mutare.equals("s")) {
            hartaJoc.goSouth();
        } else if(mutare.equals("d")) {
            hartaJoc.goEast();
        } else if(mutare.equals("a")) {
            hartaJoc.goWest();
        } else {
            throw new InvalidCommandException("");
        }

        if(hartaJoc.getCelulaAnterioara().getTipEntitate() == ENEMY){
            System.out.println("Ai ajuns pe o celula cu un inamic!");

            boolean lupta = true;
            player.startLupta();

            Entity enemy = new Enemy(player.getNivelCurentPersonaj());
            enemy.startLupta();

            while (lupta) {
                /*
                    atac jucator
                 */
                System.out.println("Nivel viata curenta: " + player.getViata() + " / " + player.getViataMax());
                System.out.println("Nivel mana curenta: " + player.getMana() + " / " + player.getManaMax());

                System.out.println("Nivel viata inamic: " + enemy.getViata() + " / " + enemy.getViataMax());
                System.out.println("Nivel mana inamic: " + enemy.getMana() + " / " + enemy.getManaMax());

                boolean poateFolosiAbil = false;

                //verific mai intai daca are abilitati
                //verificare daca jucatorul are destula mana pentru a folosi abilitati
                if(player.abilitati.size() != 0)
                    poateFolosiAbil = true;

                boolean abilitateAleasa = false;
                int abilitate = 0;

                System.out.println(" Ce alegi?");
                System.out.println("1. Ataca inamic");
                if (poateFolosiAbil)
                    System.out.println("2. Foloseste abilitate");

                boolean optiuneAleasa = false;
                int optiune = 0;
                while(optiuneAleasa == false){
                    System.out.println("Introdu numarul optiunii pe care o doresti!");
                    Scanner s = new Scanner(System.in);

                    String nrOptiune = s.nextLine();
                    try{

                        optiune = Integer.parseInt(nrOptiune);
                        if(optiune < 1 || optiune > 2)
                            throw new InexistentOptionNumberException(nrOptiune);
                        else if (optiune == 2 && poateFolosiAbil == false) {
                            throw new InexistentOptionNumberException(nrOptiune);
                        }
                        else {
                            optiuneAleasa = true;
                        }
                    }catch (NumberFormatException e){
                        afisareLiniiLibere();
                        System.out.println("Nu a fost introdus un numar! Introdu numarul optiunii dorite! ");
                    } catch (InexistentOptionNumberException e) {
                        afisareLiniiLibere();
                        System.out.println(e.getMessage());
                    }
                }

                if (optiune == 2){
                    System.out.println("Abilitatile dintre care puteti alege sunt urmatoarele: ");
                    for(int i = 0; i < player.abilitati.size(); i++){
                        System.out.println(i + 1 + ". " + player.abilitati.get(i).toString());
                    }

                    while(abilitateAleasa == false){
                        System.out.println("Introdu numarul abilitatii pe care o doresti!");
                        Scanner s = new Scanner(System.in);

                        String nrAbilitate = s.nextLine();
                        try{

                            optiune = Integer.parseInt(nrAbilitate);
                            if(optiune < 1 || optiune > player.abilitati.size())
                                throw new InexistentAbilityNumberException(nrAbilitate);
                            else {
                                abilitate = optiune - 1;
                                abilitateAleasa = true;
                            }
                        }catch (NumberFormatException e){
                            afisareLiniiLibere();
                            System.out.println("Nu a fost introdus un numar! Introdu numarul abilitatii dorite! ");
                        } catch (InexistentAbilityNumberException e) {
                            afisareLiniiLibere();
                            System.out.println(e.getMessage());
                        }
                    }

                }
                // player alegere abilitate

                if (abilitateAleasa) {

                    Spell s = player.abilitati.get(abilitate);

                    // verificare are mana
                    player.folosireAbilitate(enemy, s);
                } else {
                    int damage = player.getDamage();

                    enemy.receiveDamage(damage);
                }

                /*
                    atac adversar
                */

                // daca payerul a castigat
                if (enemy.getViata() <= 0) {
                    System.out.println("Inamicul a fost invins! Ai castigat runda!");
                    System.out.println();

                    lupta = false;
                    player.castigaLupta();
                    System.out.println("Experienta: " + player.getExperienta());
                    // se seteaza celula ca fiind vida
                    hartaJoc.getCelulaAnterioara().setTipEntitate(VOID);
                } else {
                    if (enemy.sansa()) {
                        // alege random un spell
                        Random rand = new Random();
                        rand.setSeed(System.currentTimeMillis());
                        System.out.println();
                        System.out.println("Inamicul are urmatoarea lista de abilitati: ");
                        for(int i = 0; i < enemy.abilitati.size(); i++){
                            System.out.println(enemy.abilitati.get(i).toString());
                        }

                        int pozitie = rand.nextInt(enemy.abilitati.size());

                        if (enemy.abilitati.get(pozitie).costMana <= enemy.mana) {
                            System.out.println();
                            System.out.println("Inamicul te-a atacat folosind abilitatea "
                                    + enemy.abilitati.get(pozitie).toString());
                            enemy.folosireAbilitate(player, enemy.abilitati.get(pozitie));
                        }
                    } else {
                        int damage = enemy.getDamage();
                        System.out.println();
                        System.out.println("Inamicul te-a atacat prin atac obisnuit!");
                        player.receiveDamage(damage);
                    }
                }
                if (player.getViata() <= 0) {
                    System.out.println();
                    System.out.println("Ai fost invins! ");
                    System.out.println("GAME OVER!!! :(((");
                    System.out.println();
                    lupta = false;
                    player = null;

                    Random rand = new Random();

                    Grid.generareHarta(rand.nextInt(8) + 3, rand.nextInt(8) + 3);
                    alegereCaracter();
                }

            }
        }
        else if(hartaJoc.getCelulaAnterioara().getTipEntitate() == SANCTUARY){
            System.out.println("Au ajuns pe un sanctuar!");
            System.out.println();

            int viataCurenta = player.getViata();
            Random rand = new Random();
            int v = rand.nextInt(player.getViataMax() - viataCurenta + 1);
            System.out.println("     +" + v + " pct viata!! :)");
            player.adaugaViata(v);

            int manaCurenta = player.getMana();
            int m =  rand.nextInt(player.getManaMax() - manaCurenta + 1);
            System.out.println("     +" + m + " pct mana!! :)");
            player.adaugaMana(m);

        }else if(hartaJoc.getCelulaAnterioara().getTipEntitate() == PORTAL) {
            contCurent.setNrJocuri(contCurent.getNrJocuri() + 1);
            int nivelPersonaj = player.getNivelCurentPersonaj();
            player.setNivelCurentPersonaj(nivelPersonaj + 1);
            player.setExperienta(player.getExperienta() + nivelPersonaj * 5);
            afisareLiniiLibere();
            System.out.println("Ai ajuns pe in portal, astfel ai trecut la nivelul urmator! ");
            System.out.println(contCurent.getInfoJucator().getNumeJucator() + " ai numarul de harti complete: "
                    + contCurent.getNrJocuri());
            System.out.println("Caracterul tau " + player.getNumePersonaj() + " are nivelul "
                    + player.getNivelCurentPersonaj() + " si experienta " + player.getExperienta());
            System.out.println("");
            System.out.println("Va fi generata o noua harta!");

            Random rand = new Random();

            Grid.generareHarta(rand.nextInt(8) + 3, rand.nextInt(8) + 3);
        }

    }

    public static void afisareLiniiLibere(){
        for (int i = 0; i < 20; i++)
            System.out.println("");
    }

    public void alegereCaracter(){

        Scanner s = new Scanner(System.in);
        boolean jucatorAles = false;
        int numarPozitie = 0;
        while(jucatorAles == false){
            System.out.println("Acestia sunt jucatorii dintre care iti poti alege: ");
            for (int i = 0; i < contCurent.getListaPersonaje().size(); i++) {
                int nr = i + 1;
                System.out.println(nr + ". " + contCurent.getListaPersonaje().get(i));
            }
            System.out.println("Ce jucator iti alegi? Scrie numarul pozitiei jucatorului cu care doresti sa joci!");
            String nrJucator = s.nextLine();
            try{

                numarPozitie = Integer.parseInt(nrJucator);
                if(numarPozitie < 1 || numarPozitie > contCurent.getListaPersonaje().size())
                    throw new InexistentPlayerNumberException(nrJucator);
                else {
                    jucatorAles = true;
                }
            }catch (NumberFormatException e){
                afisareLiniiLibere();
                System.out.println("Nu a fost introdus un numar! Introdu numarul pozitiei jucatorului cu care doresti sa joci! ");
            } catch (InexistentPlayerNumberException e) {
                afisareLiniiLibere();
                System.out.println(e.getMessage());
            }
            //afisareLiniiLibere();
        }
        afisareLiniiLibere();
        player = contCurent.getListaPersonaje().get(numarPozitie - 1);

        player.adaugaViata(player.getViataMax());
        player.adaugaMana(player.getManaMax());

        System.out.println("Felicitari! Ai ales jucatorul " + player.getNumePersonaj() + "!");
        System.out.println("Nivel jucator :" +  player.getNivelCurentPersonaj());
        System.out.println("Experienta jucator :" +  player.getExperienta());

    }

    //functie pentru autentificare si alegere jucator
    public void run(){

        //populare lista conturi
        listaConturi = JsonInput.deserializeAccounts();

        System.out.println("League of Warriors");
        Scanner s = new Scanner(System.in);

        contCurent = null;

        // Validare email
        while (contCurent == null) {
            try {

                System.out.print("Introduceti adresa de email: ");
                String email = s.nextLine();
                int contor = 0;
                // cautare cont
                for (Account cont : listaConturi) {
                    contor++;
                    if (email.equals(cont.getInfoJucator().getCredJucator().getEmail())) {
                        contCurent = cont;
                        break;
                    }
                }

                if (contCurent == null) {
                    throw new AccountNotFound(email);
                }
            } catch (AccountNotFound e) {
                afisareLiniiLibere();
                System.out.println(e.getMessage());
            }
        }

        // Validare parola
        boolean autentificat = false;
        while (autentificat == false) {
            try {
                System.out.print("Introduceti parola: ");
                String parola = s.nextLine();

                // verificare parola
                if (!parola.equals(contCurent.getInfoJucator().getCredJucator().getPassword())) {
                    throw new IncorrectPassword("");
                }

                autentificat = true;

            } catch (IncorrectPassword e) {
                afisareLiniiLibere();
                System.out.println(e.getMessage());
            }
        }
        afisareLiniiLibere();
        System.out.println("Bine ai revenit " + contCurent.getInfoJucator().getNumeJucator() + "!");
        System.out.println(contCurent.getInfoJucator().getNumeJucator() + " ai numarul de harti complete: "
                + contCurent.getNrJocuri());
        System.out.println("");


        boolean jucatorAles = false;
        int numarPozitie = 0;
        while(jucatorAles == false){
            System.out.println("Acestia sunt jucatorii dintre care iti poti alege: ");
            for (int i = 0; i < contCurent.getListaPersonaje().size(); i++) {
                int nr = i + 1;
                System.out.println(nr + ". " + contCurent.getListaPersonaje().get(i));
            }
            System.out.println("Ce jucator iti alegi? Scrie numarul pozitiei jucatorului cu care doresti sa joci!");
            String nrJucator = s.nextLine();
            try{

                numarPozitie = Integer.parseInt(nrJucator);
                if(numarPozitie < 1 || numarPozitie > contCurent.getListaPersonaje().size())
                    throw new InexistentPlayerNumberException(nrJucator);
                else {
                    jucatorAles = true;
                }
            }catch (NumberFormatException e){
                afisareLiniiLibere();
                System.out.println("Nu a fost introdus un numar! Introdu numarul pozitiei jucatorului cu care doresti sa joci! ");
            } catch (InexistentPlayerNumberException e) {
                afisareLiniiLibere();
                System.out.println(e.getMessage());
            }

        }
        afisareLiniiLibere();
        player = contCurent.getListaPersonaje().get(numarPozitie - 1);

        System.out.println("Felicitari! Ai ales jucatorul " + contCurent.getListaPersonaje().get(numarPozitie - 1).getNumePersonaj() + "!");
        System.out.println("Nivel jucator :" +  contCurent.getListaPersonaje().get(numarPozitie - 1).getNivelCurentPersonaj());
        System.out.println("Experienta jucator :" +  contCurent.getListaPersonaje().get(numarPozitie - 1).getExperienta());
        System.out.println();

    }
}