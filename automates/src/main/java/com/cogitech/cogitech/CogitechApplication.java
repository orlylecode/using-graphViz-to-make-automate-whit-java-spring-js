package com.cogitech.cogitech;

import com.cogitech.cogitech.entities.Automates;
import com.cogitech.cogitech.entities.Transitions;
import com.cogitech.cogitech.services.AutomateServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CogitechApplication{

    public static void main (String[] args) {
        SpringApplication.run(CogitechApplication.class, args);

        AutomateServices automateServices = new AutomateServices();
        List<String> alphabet = new ArrayList<>();
        alphabet.add("a");
        alphabet.add("b");

        List<String> etats = new ArrayList<>();
        etats.add("1");
        etats.add("2");
        etats.add("3");
        etats.add("4");
        etats.add("5");
        etats.add("6");
        etats.add("7");
        etats.add("8");

        List<String> etatInit = new ArrayList<>();
        etatInit.add("1");

        List<String> etatFin = new ArrayList<>();
        //etatFin.add("2");
        etatFin.add("8");

        List<Transitions> transitions = new ArrayList<Transitions>();

        transitions.add(new Transitions("1", "a", "3"));
        transitions.add(new Transitions("1", "b", "2"));
        transitions.add(new Transitions("2", "b", "1"));
        transitions.add(new Transitions("2", "a", "4"));
        transitions.add(new Transitions("3", "a", "5"));
        transitions.add(new Transitions("3","b","2"));
        transitions.add(new Transitions("4","a","6"));
        transitions.add(new Transitions("4","b","1"));
        transitions.add(new Transitions("5", "a", "5"));
        transitions.add(new Transitions("5", "b", "8"));
        transitions.add(new Transitions("6", "b", "7"));
        transitions.add(new Transitions("6", "a", "6"));
        transitions.add(new Transitions("7", "a", "7"));
        transitions.add(new Transitions("7", "b", "8"));
        transitions.add(new Transitions("8", "b", "7"));
        transitions.add(new Transitions("8", "a", "8"));

        Automates automates = new Automates(transitions, alphabet, etats, etatInit, etatFin);

        List<String> alphabet2 = new ArrayList<>();
        alphabet2.add("a");
        alphabet2.add("b");
  /*      alphabet2.add("2");
        alphabet2.add("3");
        alphabet2.add("4");
        alphabet2.add("5");
        alphabet2.add("6");
        alphabet2.add("7");
        alphabet2.add("8");
        alphabet2.add("9");*/

        List<String> etats2 = new ArrayList<>();
        etats2.add("1");
        etats2.add("2");
        etats2.add("3");
        etats2.add("4");
         etats2.add("5");
         etats2.add("6");

        List<String> etatInit2 = new ArrayList<>();
        etatInit2.add("1");
        etatInit2.add("2");
        List<String> etatFin2 = new ArrayList<>();
        etatFin2.add("4");
        etatFin2.add("6");
        etatFin2.add("3");

        List<Transitions> transitions2 = new ArrayList<Transitions>();

        transitions2.add(new Transitions("1", "b", "2"));
        transitions2.add(new Transitions("1", "b", "5"));
        transitions2.add(new Transitions("2", "a", "4"));
        transitions2.add(new Transitions("2", "a", "3"));
        transitions2.add(new Transitions("5", "a", "6"));
        //transitions2.add(new Transitions("3", "b", "1"));
        //transitions2.add(new Transitions("4","a","4"));
        //transitions2.add(new Transitions("4","b","4"));
        Automates automates3 = new Automates(transitions2, alphabet2, etats2, etatInit2, etatFin2);/*

        for (Transitions transitions1 : transitions) {
            System.out.println(transitions1.getEtatDep() + "---" + transitions1.getSymbole() + "-->" + transitions1.getEtatAriv());
        }*/

        //System.out.println(automateServices.isDeterminiser(automates));
        //System.out.println(automateServices.isReconnized("bbaaaaaaaaaab", automates));

        //System.out.println(automateServices.isText("bbaaaaaaaaaab bbb abd bbaaaaaaaaaaaaaaaaaaaaaaaaab qwe bbab", " ", automates));
       // System.out.println(automateServices.Afficher(automateServices.minimiser(automates),"minimiser"));

        //System.out.println(automateServices.Afficher(automateServices.determiniser(automates3),"determiniser"));

        //System.out.println(automateServices.Afficher(automateServices.completude(automates2),"minimiser"));
    }
}
