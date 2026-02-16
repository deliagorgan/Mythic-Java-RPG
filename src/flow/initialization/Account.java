package flow.initialization;

import java.util.*;
import flow.entities.characters.Character;

public class Account {

    //nu putem lasa clasa Information privata entru ca aceasta ar pute afi astfel
    //instantiata doar din interiorul clasei Acooult iar noi avem nevoie sa fie instantiata din
    //clasa JsonInput
    public static class Information{
        private Credentials credJucator;
        private SortedSet<String> listaJocuri;
        private String numeJucator;
        private String taraJucator;

        public Information(Credentials credentials, SortedSet<String> listaJocuri, String numeJucator, String taraJucator) {
            this.credJucator = credentials;
            this.numeJucator = numeJucator;
            this.taraJucator = taraJucator;
            this.listaJocuri = new TreeSet<>();
            if (listaJocuri != null) {
                this.listaJocuri.addAll(listaJocuri);
            }
        }

        public Information (InformationBuilder builder){
            this.credJucator = builder.credJucator;
            this.numeJucator = builder.numeJucator;
            this.taraJucator = builder.taraJucator;
            this.listaJocuri = new TreeSet<>();
            listaJocuri.addAll(builder.listaJocuri);
        }

        public static class InformationBuilder{
            private Credentials credJucator;
            private SortedSet<String> listaJocuri;
            private String numeJucator;
            private String taraJucator;

            public InformationBuilder setCredJucator(Credentials credJucator) {
                this.credJucator = credJucator;
                return this;
            }

            public InformationBuilder setNumeJucator(String numeJucator) {
                this.numeJucator = numeJucator;
                return this;
            }

            public InformationBuilder setTaraJucator(String taraJucator) {
                this.taraJucator = taraJucator;
                return this;
            }

            public InformationBuilder setListaJocuri(SortedSet<String> listaJocuri) {
                if(listaJocuri == null)
                    listaJocuri = null;
                else
                    this.listaJocuri =new TreeSet<String>(listaJocuri);
                return this;
            }

            public Information build(){
                return new Information(this);
            }
        }

        public void adaugareJocuri(String numeJoc)
        {
            listaJocuri.add(numeJoc);
        }
        public Credentials getCredJucator() {
            return credJucator;
        }

        public void setCredJucator(Credentials credJucator) {
            this.credJucator = credJucator;
        }

        public SortedSet<String> getListaJocuri() {
            return listaJocuri;
        }

        public void setListaJocuri(SortedSet<String> listaJocuri) {
            this.listaJocuri = listaJocuri;
        }

        public String getNumeJucator() {
            return numeJucator;
        }

        public void setNumeJucator(String numeJucator) {
            this.numeJucator = numeJucator;
        }

        public String getTaraJucator() {
            return taraJucator;
        }

        public void setTaraJucator(String taraJucator) {
            this.taraJucator = taraJucator;
        }

        @Override
        public String toString() {
            return "Information{" +
                    "credJucator=" + credJucator +
                    ", listaJocuri=" + listaJocuri +
                    ", numeJucator='" + numeJucator + '\'' +
                    ", taraJucator='" + taraJucator + '\'' +
                    '}';
        }
    }

    private Information infoJucator;
    private ArrayList<Character> listaPersonaje;
    private int nrJocuri;

    public Account(ArrayList<Character> listaPersonaje, int nrJocuri, Information infoJucator) {
        this.infoJucator = infoJucator;
        this.listaPersonaje = new ArrayList<>();
        this.listaPersonaje.addAll(listaPersonaje);
        this.nrJocuri = nrJocuri;
    }


    public ArrayList<Character> getListaPersonaje() {
        return listaPersonaje;
    }

    public int getNrJocuri() {
        return nrJocuri;
    }
    public void setNrJocuri(int nrJocuri) {
        this.nrJocuri = nrJocuri;
    }

    //returnez un deep copy pentru a evita sa dau acces direct la campurile din clasa Information

    public Information getInfoJucator() {
        if (infoJucator == null) {
            return null;
        }
        Information info = new Information.InformationBuilder()
                .setCredJucator(infoJucator.getCredJucator())
                .setListaJocuri(infoJucator.getListaJocuri())
                .setNumeJucator(infoJucator.getNumeJucator())
                .setTaraJucator(infoJucator.getTaraJucator())
                .build();

        return info;
    }

    @Override
    public String toString() {
        return "Account{" +
                "infoJucator=" + infoJucator.toString() +
                ", listaPersonaje=" + listaPersonaje +
                ", nrJocuri=" + nrJocuri +
                '}';
    }
}
