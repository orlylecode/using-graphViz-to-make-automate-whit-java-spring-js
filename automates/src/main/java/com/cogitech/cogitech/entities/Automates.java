package com.cogitech.cogitech.entities;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Automates {
        //fonction de transitions
        private List<Transitions> transitions =new ArrayList<Transitions>();
        //alphabet du de l'automate
        private List<String> alphabet =new ArrayList<String>();
        //ensemble des etats
        private List<String> etats =new ArrayList<String>();
        //ensemble des etats initiaux
        private List<String> etatInit =new ArrayList<String>();
        //ensemble des etats finaux
        private List<String> etatFinal =new ArrayList<String>();

    public Automates (List<Transitions> transitions, List<String> alphabet, List<String> etats, List<String> etatInit, List<String> etatFinal) {
        this.transitions = transitions ;
        this.alphabet = alphabet;
        this.etats = etats;
        this.etatInit = etatInit;
        this.etatFinal = etatFinal;
    }

    public Automates ( ) {
        super();
    }

    public List<Transitions> getTransitions ( ) {
        return transitions;
    }

    public void setTransitions (List<Transitions> transitions) {
        this.transitions = transitions;
    }

    public List<String> getAlphabet ( ) {
        return alphabet;
    }

    public void setAlphabet (List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public List<String> getEtats ( ) {
        return etats;
    }

    public void setEtats (List<String> etats) {
        this.etats = etats;
    }

    public List<String> getEtatInit ( ) {
        return etatInit;
    }

    public void setEtatInit (List<String> etatInit) {
        this.etatInit = etatInit;
    }

    public List<String> getEtatFinal ( ) {
        return etatFinal;
    }

    public void setEtatFinal (List<String> etatFinal) {
        this.etatFinal = etatFinal;
    }
}
