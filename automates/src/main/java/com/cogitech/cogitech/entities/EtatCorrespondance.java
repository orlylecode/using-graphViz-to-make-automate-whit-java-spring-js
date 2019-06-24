package com.cogitech.cogitech.entities;

public class EtatCorrespondance{
   private String etats ;
   private int correspondance;
    private String symbole ;



    public EtatCorrespondance (String etats, int correspondance, String symbole) {
        this.etats = etats;
        this.correspondance = correspondance;
        this.symbole = symbole;
    }

    public String getEtats ( ) {
        return etats;
    }

    public void setEtats (String etats) {
        this.etats = etats;
    }

    public int getCorrespondance ( ) {
        return correspondance;
    }

    public void setCorrespondance (int correspondance) {
        this.correspondance = correspondance;
    }

    public EtatCorrespondance ( ) {
        super();
    }

    public String getSymbole ( ) {
        return symbole;
    }

    public void setSymbole (String symbole) {
        this.symbole = symbole;
    }
}
