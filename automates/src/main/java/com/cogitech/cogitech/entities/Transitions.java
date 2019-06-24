package com.cogitech.cogitech.entities;

public class Transitions{
    //on considere que les transitions ont pour sens : etatDep ---> etatArivee
    //etat de depart
    private String etatDep;
    // symbole de transition
    private String symbole;
    //etat d'arrivee
    private String etatAriv;

    public Transitions (String etatDep, String symbole, String etatAriv) {
        this.etatDep = etatDep;
        this.symbole = symbole;
        this.etatAriv = etatAriv;
    }

    public Transitions ( ) {
        super();
    }

    public String getEtatDep ( ) {
        return etatDep;
    }

    public void setEtatDep (String etatDep) {
        this.etatDep = etatDep;
    }

    public String getSymbole ( ) {
        return symbole;
    }

    public void setSymbole (String symbole) {
        this.symbole = symbole;
    }

    public String getEtatAriv ( ) {
        return etatAriv;
    }

    public void setEtatAriv (String etatFinal) {
        this.etatAriv = etatFinal;
    }



}
