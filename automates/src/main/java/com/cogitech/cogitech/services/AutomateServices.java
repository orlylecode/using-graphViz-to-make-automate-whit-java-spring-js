package com.cogitech.cogitech.services;

import com.cogitech.cogitech.entities.Automates;
import com.cogitech.cogitech.entities.EtatCorrespondance;
import com.cogitech.cogitech.entities.Transitions;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AutomateServices{
   // private Automates automates = new Automates();


    //determination de la nature de l'automate
    public String isDeterminiser (Automates automates) {

        String nature = "AFD";

        /************************************************************************************
         liste de transition sortante si elle contient deux valeurs identique , L'automate
         est non deterministe
         */
        if (automates != null) {
            if (automates.getTransitions() != null) {

                List<List<String>> transitionSortantes = new ArrayList<List<String>>();

                for (String etat : automates.getEtats()) {
                    for (String s : automates.getAlphabet()) {
                        if (ifTransitions(etat, s, automates) != null) {
                            nature = "AFDC";
                        }else {
                            nature="AFD";
                        }
                    }
                }

                for (Transitions transitions : automates.getTransitions()) {
                    List<String> transitionSortante = new ArrayList<String>();
                    transitionSortante.add(transitions.getEtatDep());
                    transitionSortante.add(transitions.getSymbole());

                    if (transitionSortantes.contains(transitionSortante)) {
                        nature = "AFN";
                    }
                    transitionSortantes.add(transitionSortante);
                }

                for (Transitions transitions : automates.getTransitions()) {
                    List<String> symboleUtilise = new ArrayList<String>();

                    symboleUtilise.add(transitions.getSymbole());

                    if (symboleUtilise.contains("Є")) {
                        nature = "Є-AFN";
                    }
                }




            } else {
                nature = "ERROR:aucune transition defini";
            }
        }
        return nature;
    }

    public Automates determiniser (Automates automates) {
        int i =0;
        Automates determinise = new Automates();

        List<Transitions> transitions = new CopyOnWriteArrayList<>();
        List<List<String>> etats = new CopyOnWriteArrayList<List<String>>();
        List<List<String>> etatsTemp = new CopyOnWriteArrayList<List<String>>();
        List<String> etatsCible = new CopyOnWriteArrayList<String>();
        List<String> e = new CopyOnWriteArrayList<String>();
        List<String> f = new CopyOnWriteArrayList<String>();
        if (isDeterminiser(automates) == "AFN") {
            etats.add(automates.getEtatInit());
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
           do{
                for (List<String> list : etats) {
                    for (String etat : list) {
                        for (String symbole : automates.getAlphabet()) {
                            etatsCible = etatsCible(etat, symbole, automates);
                            if (!etatsTemp.contains(etatsCible)){
                                etatsTemp.add(etatsCible);
                            }

                           Transitions t= new Transitions(listToString(list), symbole, listToString(etatsCible));
                            if (!determinise.getTransitions().contains(t)){
                            transitions.add(new Transitions(listToString(list), symbole, listToString(etatsCible)));
                            e=determinise.getEtats();
                            e.removeAll(list);
                            e.add(listToString(list));

                            determinise.setEtats(e);

                            etats=etatsTemp;
                            ////////////////////////////////////////////////////////
                            determinise.setEtatInit((List<String>)etats.get(0));
                            determinise.setAlphabet(automates.getAlphabet());
                            determinise.setTransitions(transitions);
                            determinise.setEtatFinal(f);

                            }
                        }
                    }
                }

                ////////////////////////////////////////////////////////////////////////////////////////
                for (String s : automates.getEtatFinal()) {
                    f.add(listToString(EtatAriv(s, etats)));
                }

                purifier(determinise);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
               System.out.println(Afficher(determinise ,"determiniser"));
            }while (!etatArriv(determinise));
        }
        if (isDeterminiser(automates) == "Є-AFN") {

        }

        if (isDeterminiser(automates) == "AFD") {
            determinise = automates;
        }

        if (isDeterminiser(automates) == "AFDC") {
            determinise = automates;
        }
        //purifier(determinise);
        System.out.println(Afficher(determinise, "determiniser"));
        return determinise;
    }

    public void purifier(Automates automates){
        //List<String> e = new CopyOnWriteArrayList<String>();
        for (Transitions transitions : automates.getTransitions()) {
            if (transitions.getEtatAriv().equals("")) {
                List<Transitions> g = automates.getTransitions();
                g.remove(transitions);
                automates.setTransitions(g);
            }
        }
        /*for (String s : automates.getEtats()) {
            for (String s1 : automates.getAlphabet()) {
                if (ifTransitions(s, s1, automates) == null) {
                    e = automates.getEtats();
                    e.remove(s);
                    automates.setEtats(e);
                }
            }
        }*/
    }

    public boolean etatArriv(Automates automates){
        boolean etatArriv=true;
        for (Transitions transitions : automates.getTransitions()) {
            if (transitions.getEtatAriv().equals("")) {
                etatArriv = false;
            }
        }
        return etatArriv;
    }
public List<String> etatsCible(String etatDep ,String symbole , Automates automates){
        List<String> etatsCible = new ArrayList<String>();
    for (Transitions transitions : automates.getTransitions()) {
        if (transitions.getEtatDep().equals(etatDep) && transitions.getSymbole().equals(symbole)) {
            etatsCible.add(transitions.getEtatAriv());
        }
    }

    return etatsCible;
}
    public Automates completude(Automates automates){
        Transitions transitions = new Transitions();
        List<String> etats = new LinkedList<String>(automates.getEtats());

        int verrou =0 ;

        for (String etat : automates.getEtats()) {
            for (String symbole : automates.getAlphabet()) {
                transitions = ifTransitions(etat, symbole, automates);
                if (transitions == null) {
                    if (verrou==0){
                        etats.add("puit");
                        automates.setEtats(etats);
                        verrou=1;
                    }
                    List<Transitions> listTrans = automates.getTransitions();
                    listTrans.add(new Transitions(etat, symbole, "puit"));
                    automates.setTransitions(listTrans);
                }
            }
        }
        return automates;
    }

    public List<String> distinguable (List<EtatCorrespondance> etatCorrespondances, int index, String symbole) {
        List<String> etats = new ArrayList<String>();
        for (EtatCorrespondance etatCorrespondance : etatCorrespondances) {
            if (etatCorrespondance.getCorrespondance() == index && etatCorrespondance.getSymbole().equals(symbole)) {
                etats.add(etatCorrespondance.getEtats());
            }
        }
        return etats;
    }

    public List<List<String>> isStable (List<List<String>> partition, Automates automates) {
        System.out.println("test1 :" + afficherPartition(partition));

        List<List<String>> p1 = new ArrayList<List<String>>();

        List<EtatCorrespondance> etatCorrespondance = new ArrayList<EtatCorrespondance>();


        for (List<String> classeEqui : partition) {
            //System.out.println("test9");
            for (String etat : classeEqui) {
                //System.out.println("test8");
                for (String symbole : automates.getAlphabet()) {
                    //System.out.println("test7");
                    Transitions ifTransitions = ifTransitions(etat, symbole, automates);
                    for (List<String> classeEqui1 : partition) {
                        //System.out.println("test6");
                        if (ifTransitions != null) {
                            //System.out.println("test5");
                            if (classeEqui1.contains(ifTransitions.getEtatAriv())) {
                                //System.out.println("test4");
                                etatCorrespondance.add(new EtatCorrespondance(etat, partition.indexOf(classeEqui1), symbole));
                            }

                        }
                    }
                }
            }

            List<Integer> indexs = new ArrayList<Integer>();
            for (EtatCorrespondance etatCorrespondance1 : etatCorrespondance) {
                // System.out.println("test2");
                if (!indexs.contains(etatCorrespondance1.getCorrespondance())) {

                    List<String> ensemble = distinguable(etatCorrespondance, etatCorrespondance1.getCorrespondance(), etatCorrespondance1.getSymbole());
                    p1.add(ensemble);
                    System.out.println("test3 :" + afficherPartition(mouveDedondance(p1)));
                }
                indexs.add(etatCorrespondance1.getCorrespondance());
            }
            indexs.clear();
            etatCorrespondance.clear();
        }
        return mouveDedondance(p1);
    }

    //le principe reste le meme
    public Automates minimiser (Automates automates) {
        List<List<String>> p1 = new ArrayList<List<String>>();

        List<List<String>> p0 = new ArrayList<List<String>>();

        if (automates != null) {
            if (isDeterminiser(automates).equals("AFDC")) {
                List<String> f = automates.getEtatFinal();
                //QF = Q \ F
                List<String> qf = new LinkedList<String>(automates.getEtats());
               // qf = automates.getEtats();
                qf.removeAll(f);
                //intialisation des classe d'equivalences

                p0.add(f);
                p0.add(qf);
                if (!f.equals(qf)) {
                    //System.out.println( afficherPartition(p0) );

/*
                    do {
                        p1.clear();
                        p1.addAll(isStable(p0 ,automates));

                        p0.clear();
                        p0.addAll(p1);

                        System.out.println( afficherPartition(p1) );
                      //  System.out.println( afficherPartition(isStable(p0 ,automates)) );

                        System.out.println("je suis encore la");
                    }while ( isStable(p1,automates).remove(p1));
*/


                   for (int i = 0; i < 100; i++) {

                        p1.clear();
                        p1.addAll(isStable(p0, automates));

                        p0.clear();
                        p0.addAll(p1);

                        System.out.println(afficherPartition(p1));
                        //  System.out.println( afficherPartition(isStable(p0 ,automates)) );

                        System.out.println("je suis encore la");
                    }
                }
            }
        }
        System.out.println(afficherPartition(p1));
        return ensembleToAutomate(p1,automates);
    }

    public List<List<String>> mouveDedondance (List<List<String>> lists) {

        List<List<String>> l = new ArrayList<List<String>>();

        for (List<String> strings : lists) {

            for (List<String> strings1 : lists) {
                if (!strings.equals(strings1)) {
                    strings.removeAll(strings1);
                    strings1.removeAll(strings);
                }
            }
            Set<String> set1 = new HashSet<String>(strings);
            List<String> strings1 = new ArrayList<>(set1);
            if (!l.contains(strings1)){
                l.add(strings1);
            }

            if (strings.size() == 0) {
                lists.remove(strings);
            }
        }
        lists = l;
        return lists;
    }

    public Automates ensembleToAutomate(List<List<String>> lists, Automates automates){
        List<Transitions> transitions = new ArrayList<Transitions>();
        List<String> alphabet = automates.getAlphabet();
        List<String> etatFinal =new ArrayList<>();
        List<String> etats = new ArrayList<>();
        List<String> etatInit = new ArrayList<>();

        for (String s : automates.getEtatInit()) {
            etatInit.add(listToString(EtatAriv(s, lists)));
        }

        for (String s : automates.getEtatFinal()) {
            etatFinal.add(listToString(EtatAriv(s, lists)));
        }

        for (List<String> strings : lists) {
            etats.add(listToString(strings));
            for (String s : automates.getAlphabet()) {
                Transitions transitions1 = ifTransitions(strings.get(0),s,automates);
                if (transitions1 != null){
                    Transitions t = new Transitions(listToString(EtatAriv(strings.get(0),lists)),s, listToString(EtatAriv(transitions1.getEtatAriv() ,lists )) );
                    transitions.add(t);
                }

            }
        }
        Automates automates1 = new Automates(transitions,alphabet,etats,etatInit,etatFinal);
        return automates1;
    }


public List<String> EtatAriv(String s , List<List<String>> strings){
        List<String> retour = new ArrayList<String>();
        int i = -1;
    for (List<String> strings1 : strings) {
        if (strings1.contains(s)) {
            i = strings.indexOf(strings1);
        }
    }
    if (i!=-1){
        return strings.get(i);

    }else return new ArrayList<String>();
}

    public String listToString (List<String> strings){
        String text="";
        for (String s : strings) {
            text = text + s;
        }
        return text;
    }

    public List<String> addToSet (Set<String> set, List<String> strings) {
        for (String s : set) {
            strings.add(s);
        }
        return strings;
    }

    public String afficherPartition (List<List<String>> lists) {
        String partition = "{ ";
        for (List<String> strings : lists) {
            partition = partition + "{";
            for (String s : strings) {
                partition = partition + s + " , ";
            }
            partition = partition + "} ; ";
        }
        partition = partition + "}";
        return partition;
    }


    public String afficherEtatsCo (Set<EtatCorrespondance> correspondances) {
        String partition = "{ ";
        for (EtatCorrespondance strings : correspondances) {
            partition = partition + "{" + strings.getEtats() + " , " + strings.getSymbole() + " , " + strings.getCorrespondance();

            partition = partition + "} ; ";
        }
        partition = partition + "}";
        return partition;
    }

    public Transitions ifTransitions (String etat, String symbole, Automates automates) {
        Transitions trans = null;
        for (Transitions transitions : automates.getTransitions()) {
            if (transitions.getEtatDep().equals(etat) && transitions.getSymbole().equals(symbole)) {
                trans = transitions;
                // System.out.println(trans.getEtatDep()+"-->"+trans.getEtatAriv());
            }
        }
        return trans;
    }


    public String isReconnized (String mot, Automates automates) {
        String isReconnized = "";
        if (isDeterminiser(automates).equals("AFD") || isDeterminiser(automates).equals("AFDC")) {

            char ch[] = mot.toCharArray();
            int taille = mot.length();

            String etatCourant = automates.getEtatInit().get(0);

            for (int i = 0; i < taille; i++) {

                String symboleCourant = String.valueOf(ch[i]);

                if (ifTransitions(etatCourant, symboleCourant, automates) != null) {

                    Transitions transitions = ifTransitions(etatCourant, symboleCourant, automates);
                    etatCourant = transitions.getEtatAriv();
                    symboleCourant = String.valueOf(ch[i]);

                    if (symboleCourant.equals(String.valueOf(ch[taille - 1]))) {
                        if (automates.getEtatFinal().contains(etatCourant)) {
                            isReconnized = "true";
                        } else isReconnized = "false";
                    }

                } else {
                    //System.out.println(ch[taille-1]);
                    isReconnized = "false";
                }

                if (!automates.getAlphabet().contains(String.valueOf(ch[i]))) {
                    isReconnized = "false";
                }
            }
        }

        return isReconnized;
    }

    public List<String> isText (String text, String sepateur, Automates automates) {
        List<String> strings = new ArrayList<String>();
        String message = "mots non reconnus :";
        String color = "";
        String mots[] = text.split(sepateur);
        int taille = mots.length;

        for (int i = 0; i < taille; i++) {
            if (isReconnized(mots[i], automates).equals("true")) {
                message = message + " " + mots[i] + " ; ";
                color = color + " <span style=\"color:#761c19\"> " + mots[i] + " </span>";
            } else {
                color = color + " <span style=\"color:#2e6da4\"> " + mots[i] + " </span> ";
            }
        }
        strings.add(color);
        strings.add(message);
        return strings;
        // message;
    }

    // methodes suplementaires
    public String Afficher (Automates automates, String nom) {

        String init = "";
        String fin = "";
        String alphet = "";
        String transitions = "";
        for (String s : automates.getEtatInit()) {
            init = init + s + " [fontcolor=white, style=filled, fillcolor=black]\n";
        }

        for (String s : automates.getEtatFinal()) {
            fin = fin + s + " [peripheries=2 , fontcolor=white, style=filled, fillcolor=red]\n";
        }


        for (String s : automates.getEtats()) {
            alphet = alphet + s + " [fontcolor=white, style=filled, fillcolor=blue]\n";
        }

        for (Transitions s : automates.getTransitions()) {
            transitions = transitions + s.getEtatDep() + " -> " + s.getEtatAriv() + "[label=\" " + s.getSymbole() + "\"]\n";
        }

        return "\n" + "digraph G {      label = \"" + nom +
                " \";\n\n" + alphet + fin + init + transitions + "}\n";
    }

/*
    public Transitions sameTrans(Automates automates){
       List<Transitions> transitions = new ArrayList<Transitions>();
        automates.getEtats().forEach(etat -> {
            automates.getAlphabet().forEach(symbole -> {
                transitions.add(ifTransitions(etat,automates));

            });
        });
        transitions.forEach(transitions1 -> {
            if (transitions1.getEtatAriv().equals(transitions.ne))
        });
    }*/

    public String transition (Automates automates) {
        String transitions = "";
        if (automates.getTransitions() == null) return "aucune transition ";
        else {
            for (Transitions transitions1 : automates.getTransitions()) {

                transitions = transitions + transitions1.getEtatDep() + "====" + transitions1.getSymbole() + "===>" + transitions1.getEtatAriv() + "\n";
            }
            return transitions;
        }

    }
}
