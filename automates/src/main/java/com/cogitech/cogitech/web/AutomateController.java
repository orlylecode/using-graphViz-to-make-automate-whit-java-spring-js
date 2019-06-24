package com.cogitech.cogitech.web;

import com.cogitech.cogitech.entities.Automates;
import com.cogitech.cogitech.entities.Transitions;
import com.cogitech.cogitech.services.AutomateServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class AutomateController{

    @Autowired
    private AutomateServices automateServices = new AutomateServices();
    @RequestMapping(value = "/automate",method = RequestMethod.GET)
    public String affiche(Model model,
                        @RequestParam(name = "alphabet",defaultValue = "") String alphabet,
                        @RequestParam(name = "etatsInit",defaultValue = "") String etatsInit,
                        @RequestParam(name = "etatsFinaux",defaultValue = "") String etatsFinaux,
                          @RequestParam(name = "transitions",defaultValue = "") String transitions,
                          @RequestParam(name = "etats", defaultValue = "") String etats){
        alphabet = alphabet.replaceAll(" ","");
        etatsInit = etatsInit.replaceAll(" ","");
        etatsFinaux= etatsFinaux.replaceAll(" ","");
        transitions= transitions.replaceAll(" ","");
        etats= etats.replaceAll(" ","");

        List<String> lAlphebt = Arrays.asList( alphabet.split(",") );
        List<String>  lEtatsInit = Arrays.asList( etatsInit.split(",") );
        List<String> lEtatsFinaux = Arrays.asList( etatsFinaux.split(",") );
        List<String> lEtats = Arrays.asList( etats.split(",") );
        List<String> lTrans = Arrays.asList( transitions.split(";") );
        List<Transitions> lTransition =new ArrayList<Transitions>();

        List<String> l = Arrays.asList();


        for (String s : lTrans) {
            l = Arrays.asList(s.split(","));
            if (l.size() >= 2){
                if (alphabet.contains(l.get(1))){
                    Transitions t = new Transitions(l.get(0) ,l.get(1) ,l.get(2));
                    lTransition.add(t);
                }else {
                    Transitions t = new Transitions(l.get(0) ,"Є" ,l.get(2));
                    lTransition.add(t);
                }

            }

        }

        Automates automates = new Automates(lTransition,lAlphebt,lEtats,lEtatsInit,lEtatsFinaux);


        model.addAttribute("graphAutomate",automateServices.Afficher(automates,"Initial"));
        model.addAttribute("alphabet",alphabet);
        model.addAttribute("etatsInit",etatsInit);
        model.addAttribute("etatsFinaux",etatsFinaux);
        model.addAttribute("transitions",transitions);
        model.addAttribute("etats",etats);
        model.addAttribute("minimiser"," graph {}");
        model.addAttribute("determiniser"," graph {}");
        model.addAttribute("completer"," graph {}");
        model.addAttribute("graphAutomate1"," graph {}");
        model.addAttribute("graphAutomate2"," graph {}");
        model.addAttribute("graphAutomateU"," graph {}");
        model.addAttribute("trans",automateServices.transition(automates));
        model.addAttribute("nature",automateServices.isDeterminiser(automates));

        return "automate";
    }

    @RequestMapping(value = "/completer",method = RequestMethod.GET)
    public String completer(Model model,
                          @RequestParam(name = "alphabet",defaultValue = "") String alphabet,
                          @RequestParam(name = "etatsInit",defaultValue = "") String etatsInit,
                          @RequestParam(name = "etatsFinaux",defaultValue = "") String etatsFinaux,
                          @RequestParam(name = "transitions",defaultValue = "") String transitions,
                          @RequestParam(name = "etats", defaultValue = "") String etats){
        alphabet = alphabet.replaceAll(" ","");
        etatsInit = etatsInit.replaceAll(" ","");
       etatsFinaux= etatsFinaux.replaceAll(" ","");
       transitions= transitions.replaceAll(" ","");
        etats= etats.replaceAll(" ","");

        List<String> lAlphebt = Arrays.asList( alphabet.split(",") );
        List<String>  lEtatsInit = Arrays.asList( etatsInit.split(",") );
        List<String> lEtatsFinaux = Arrays.asList( etatsFinaux.split(",") );
        List<String> lEtats = Arrays.asList( etats.split(",") );
        List<String> lTrans = Arrays.asList( transitions.split(";") );
        List<Transitions> lTransition =new ArrayList<Transitions>();

        List<String> l = Arrays.asList();


        for (String s : lTrans) {
            l = Arrays.asList(s.split(","));
            if (l.size() >= 2){
                if (alphabet.contains(l.get(1))){
                    Transitions t = new Transitions(l.get(0) ,l.get(1) ,l.get(2));
                    lTransition.add(t);
                }else {
                    Transitions t = new Transitions(l.get(0) ,"Є" ,l.get(2));
                    lTransition.add(t);
                }

            }

        }


        Automates automates = new Automates(lTransition,lAlphebt,lEtats,lEtatsInit,lEtatsFinaux);

        model.addAttribute("graphAutomate",automateServices.Afficher(automates,"Initial"));
        String message = automateServices.isDeterminiser(automates);

        if (message.equals("AFD")){
            model.addAttribute("completer",automateServices.Afficher(automateServices.completude(automates),"AFDC"));
        }else {
            model.addAttribute("nature"," Erreur l'automate est un "+message +" on ne peut que completer un AFD");
            model.addAttribute("completer", " graph {}");
        }



        model.addAttribute("alphabet",alphabet);
        model.addAttribute("etatsInit",etatsInit);
        model.addAttribute("etatsFinaux",etatsFinaux);
        model.addAttribute("transitions",transitions);
        model.addAttribute("etats",etats);
        model.addAttribute("minimiser"," graph {}");
        model.addAttribute("determiniser"," graph {}");
        model.addAttribute("graphAutomate1"," graph {}");
        model.addAttribute("graphAutomate2"," graph {}");
        model.addAttribute("graphAutomateU"," graph {}");
        model.addAttribute("trans",automateServices.transition(automates));
        return "automate";
    }


    @RequestMapping(value = "/reconnaissance",method = RequestMethod.GET)
    public String reconnaissance(Model model,
                                 @RequestParam(name = "texte",defaultValue = "") String texte,
                         @RequestParam(name = "alphabet",defaultValue = "") String alphabet,
                                 @RequestParam(name = "separateur",defaultValue = " ") String separateur,
                         @RequestParam(name = "etatsInit",defaultValue = "") String etatsInit,
                         @RequestParam(name = "etatsFinaux",defaultValue = "") String etatsFinaux,
                         @RequestParam(name = "transitions",defaultValue = "") String transitions,
                         @RequestParam(name = "etats", defaultValue = "") String etats){
        alphabet = alphabet.replaceAll(" ","");
        etatsInit = etatsInit.replaceAll(" ","");
        etatsFinaux= etatsFinaux.replaceAll(" ","");
        transitions= transitions.replaceAll(" ","");
        etats= etats.replaceAll(" ","");

        List<String> lAlphebt = Arrays.asList( alphabet.split(",") );
        List<String>  lEtatsInit = Arrays.asList( etatsInit.split(",") );
        List<String> lEtatsFinaux = Arrays.asList( etatsFinaux.split(",") );
        List<String> lEtats = Arrays.asList( etats.split(",") );
        List<String> lTrans = Arrays.asList( transitions.split(";") );
        List<Transitions> lTransition =new ArrayList<Transitions>();

        List<String> l = Arrays.asList();


        for (String s : lTrans) {
            l = Arrays.asList(s.split(","));
            if (l.size() >= 2){
                if (alphabet.contains(l.get(1))){
                    Transitions t = new Transitions(l.get(0) ,l.get(1) ,l.get(2));
                    lTransition.add(t);
                }else {
                    Transitions t = new Transitions(l.get(0) ,"Є" ,l.get(2));
                    lTransition.add(t);
                }
            }

        }

        Automates automates = new Automates(lTransition,lAlphebt,lEtats,lEtatsInit,lEtatsFinaux);

        System.out.println(texte);

        model.addAttribute("graphAutomate",automateServices.Afficher(automates , "Initial"));
        model.addAttribute("alphabet",alphabet);
        model.addAttribute("texte",texte);
        model.addAttribute("etatsInit",etatsInit);
        model.addAttribute("etatsFinaux",etatsFinaux);
        model.addAttribute("transitions",transitions);
        model.addAttribute("etats",etats);
        model.addAttribute("nature",automateServices.isDeterminiser(automates));
        model.addAttribute("mots",automateServices.isText(texte,separateur,automates).get(1));
        model.addAttribute("color",automateServices.isText(texte,separateur,automates).get(0));
        model.addAttribute("separateur",separateur);
        model.addAttribute("completer"," graph {}");
        model.addAttribute("minimiser"," graph {}");
        model.addAttribute("determiniser"," graph {}");
        model.addAttribute("trans",automateServices.transition(automates));
        model.addAttribute("graphAutomate1"," graph {}");
        model.addAttribute("graphAutomate2"," graph {}");
        model.addAttribute("graphAutomateU"," graph {}");
        return "automate";
    }


    @RequestMapping(value = "/minimiser",method = RequestMethod.GET)
    public String minimiser(Model model,
                          @RequestParam(name = "alphabet",defaultValue = "") String alphabet,
                          @RequestParam(name = "etatsInit",defaultValue = "") String etatsInit,
                          @RequestParam(name = "etatsFinaux",defaultValue = "") String etatsFinaux,
                          @RequestParam(name = "transitions",defaultValue = "") String transitions,
                          @RequestParam(name = "etats", defaultValue = "") String etats){
        alphabet = alphabet.replaceAll(" ","");
        etatsInit = etatsInit.replaceAll(" ","");
        etatsFinaux= etatsFinaux.replaceAll(" ","");
        transitions= transitions.replaceAll(" ","");
        etats= etats.replaceAll(" ","");

        List<String> lAlphebt = Arrays.asList( alphabet.split(",") );
        List<String>  lEtatsInit = Arrays.asList( etatsInit.split(",") );
        List<String> lEtatsFinaux = Arrays.asList( etatsFinaux.split(",") );
        List<String> lEtats = Arrays.asList( etats.split(",") );
        List<String> lTrans = Arrays.asList( transitions.split(";") );
        List<Transitions> lTransition =new ArrayList<Transitions>();

        List<String> l = Arrays.asList();



        for (String s : lTrans) {
            l = Arrays.asList(s.split(","));
            if (l.size() >= 2){
                if (alphabet.contains(l.get(1))){
                    Transitions t = new Transitions(l.get(0) ,l.get(1) ,l.get(2));
                    lTransition.add(t);
                }else {
                    Transitions t = new Transitions(l.get(0) ,"Є" ,l.get(2));
                    lTransition.add(t);
                }

            }

        }

        Automates automatesM1 = new Automates(lTransition,lAlphebt,lEtats,lEtatsInit,lEtatsFinaux);

        String message = automateServices.isDeterminiser(automatesM1);


        if (message.equals("AFDC")){
            Automates automatesM = automateServices.minimiser(automatesM1);
             //a = automateServices.Afficher(automates,"Minimisee");
              //automateServices.purifier(automatesM);
            List<Transitions> g = new CopyOnWriteArrayList<>(automatesM.getTransitions());
            for (Transitions transitionsM : g) {
                if (transitionsM.getEtatAriv().equals("")) {
                    g.remove(transitionsM);
                    automatesM.setTransitions(g);
                }
            }
            model.addAttribute("minimiser",automateServices.Afficher(automatesM,"minimiser"));
            System.out.println(automateServices.Afficher(automatesM,"tets"));

        }else {
            model.addAttribute("nature"," Erreur l'automate est un "+message +" on ne peut que minimiser un AFDC");
            model.addAttribute("minimiser", " graph {label=\"test\"}");
        }



        model.addAttribute("graphAutomate",automateServices.Afficher(automatesM1,"Initial"));
        model.addAttribute("alphabet",alphabet);
        model.addAttribute("etatsInit",etatsInit);
        model.addAttribute("etatsFinaux",etatsFinaux);
        model.addAttribute("transitions",transitions);
        model.addAttribute("completer"," graph {}");
        model.addAttribute("etats",etats);
        model.addAttribute("completer"," graph {}");
        model.addAttribute("determiniser"," graph {}");
        model.addAttribute("graphAutomate1"," graph {}");
        model.addAttribute("graphAutomate2"," graph {}");
        model.addAttribute("graphAutomateU"," graph {}");
        return "automate";
    }



    @RequestMapping(value = "/determiniser",method = RequestMethod.GET)
    public String determiniser (Model model,
                          @RequestParam(name = "alphabet",defaultValue = "") String alphabet,
                          @RequestParam(name = "etatsInit",defaultValue = "") String etatsInit,
                          @RequestParam(name = "etatsFinaux",defaultValue = "") String etatsFinaux,
                          @RequestParam(name = "transitions",defaultValue = "") String transitions,
                          @RequestParam(name = "etats", defaultValue = "") String etats){
        alphabet = alphabet.replaceAll(" ","");
        etatsInit = etatsInit.replaceAll(" ","");
        etatsFinaux= etatsFinaux.replaceAll(" ","");
        transitions= transitions.replaceAll(" ","");
        etats= etats.replaceAll(" ","");

        List<String> lAlphebt = Arrays.asList( alphabet.split(",") );
        List<String>  lEtatsInit = Arrays.asList( etatsInit.split(",") );
        List<String> lEtatsFinaux = Arrays.asList( etatsFinaux.split(",") );
        List<String> lEtats = Arrays.asList( etats.split(",") );
        List<String> lTrans = Arrays.asList( transitions.split(";") );
        List<Transitions> lTransition =new ArrayList<Transitions>();

        List<String> l = Arrays.asList();


        for (String s : lTrans) {
            l = Arrays.asList(s.split(","));
            if (l.size() >= 2){
                if (alphabet.contains(l.get(1))){
                    Transitions t = new Transitions(l.get(0) ,l.get(1) ,l.get(2));
                    lTransition.add(t);
                }else {
                    Transitions t = new Transitions(l.get(0) ,"Є" ,l.get(2));
                    lTransition.add(t);
                }

            }

        }

        Automates automates = new Automates(lTransition,lAlphebt,lEtats,lEtatsInit,lEtatsFinaux);

        //Automates automates = automateServices.determiniser(automates1);

        //System.out.println(automateServices.Afficher(automates ,"Init"));

        model.addAttribute("graphAutomate",automateServices.Afficher(automates,"Initial"));
        model.addAttribute("determiniser",automateServices.Afficher(automateServices.determiniser(automates),"determinisee"));
        model.addAttribute("alphabet",alphabet);
        model.addAttribute("etatsInit",etatsInit);
        model.addAttribute("etatsFinaux",etatsFinaux);
        model.addAttribute("transitions",transitions);
        model.addAttribute("etats",etats);
        model.addAttribute("minimiser"," graph {}");
        model.addAttribute("completer"," graph {}");
        model.addAttribute("trans",automateServices.transition(automates));
        model.addAttribute("graphAutomate1"," graph {}");
        model.addAttribute("graphAutomate2"," graph {}");
        model.addAttribute("graphAutomateU"," graph {}");
        return "automate";
    }






    @RequestMapping(value = "/union",method = RequestMethod.GET)
    public String union(Model model,
                          @RequestParam(name = "alphabet1",defaultValue = "") String alphabet1,
                          @RequestParam(name = "etatsInit1",defaultValue = "") String etatsInit1,
                          @RequestParam(name = "etatsFinaux1",defaultValue = "") String etatsFinaux1,
                          @RequestParam(name = "transitions1",defaultValue = "") String transitions1,
                          @RequestParam(name = "etats1", defaultValue = "") String etats1,
                          @RequestParam(name = "alphabet2",defaultValue = "") String alphabet2,
                          @RequestParam(name = "etatsInit2",defaultValue = "") String etatsInit2,
                          @RequestParam(name = "etatsFinaux2",defaultValue = "") String etatsFinaux2,
                          @RequestParam(name = "transitions2",defaultValue = "") String transitions2,
                          @RequestParam(name = "etats2", defaultValue = "") String etats2
    ){

        /////////////////////////////////////////////////////////////////////////////////////////////22222222222222222222222222222222/////////////////////////////////

        alphabet2 = alphabet2.replaceAll(" ","");
        etatsInit2 = etatsInit2.replaceAll(" ","");
        etatsFinaux2= etatsFinaux2.replaceAll(" ","");
        transitions2= transitions2.replaceAll(" ","");
        etats2= etats2.replaceAll(" ","");

        List<String> lAlphebt2 = Arrays.asList( alphabet2.split(",") );
        List<String>  lEtatsInit2 = Arrays.asList( etatsInit2.split(",") );
        List<String> lEtatsFinaux2 = Arrays.asList( etatsFinaux2.split(",") );
        List<String> lEtats2 = Arrays.asList( etats2.split(",") );
        List<String> lTrans2 = Arrays.asList( transitions2.split(";") );
        List<Transitions> lTransition2 =new ArrayList<Transitions>();

        List<String> l2 = Arrays.asList();


        for (String s : lTrans2) {
            l2 = Arrays.asList(s.split(","));
            if (l2.size() >= 2){
                if (alphabet2.contains(l2.get(1))){
                    Transitions t2 = new Transitions(l2.get(0) ,l2.get(1) ,l2.get(2));
                    lTransition2.add(t2);
                }else {
                    Transitions t2 = new Transitions(l2.get(0) ,"Є" ,l2.get(2));
                    lTransition2.add(t2);
                }

            }

        }

        Automates automates2 = new Automates(lTransition2,lAlphebt2,lEtats2,lEtatsInit2,lEtatsFinaux2);

        System.out.println(automateServices.Afficher(automates2,"aut2"));
        model.addAttribute("graphAutomateU",automateServices.Afficher(automates2,"automate2"));
        model.addAttribute("alphabet2",alphabet2);
        model.addAttribute("etatsInit2",etatsInit2);
        model.addAttribute("etatsFinaux2",etatsFinaux2);
        model.addAttribute("transitions2",transitions2);
        model.addAttribute("etats2",etats2);
        model.addAttribute("minimiser2"," graph {}");
        model.addAttribute("determiniser2"," graph {}");
        model.addAttribute("completer2"," graph {}");

        model.addAttribute("trans2","");
        model.addAttribute("nature2","");




        ///////////////////////////////////////////111111111111111111111111111111111//////////////////////////////////////



        alphabet1 = alphabet1.replaceAll(" ","");
        etatsInit1 = etatsInit1.replaceAll(" ","");
        etatsFinaux1= etatsFinaux1.replaceAll(" ","");
        transitions1= transitions1.replaceAll(" ","");
        etats1= etats1.replaceAll(" ","");

        List<String> lAlphebt1 = Arrays.asList( alphabet1.split(",") );
        List<String>  lEtatsInit1 = Arrays.asList( etatsInit1.split(",") );
        List<String> lEtatsFinaux1 = Arrays.asList( etatsFinaux1.split(",") );
        List<String> lEtats1 = Arrays.asList( etats1.split(",") );
        List<String> lTrans1 = Arrays.asList( transitions1.split(";") );
        List<Transitions> lTransition1 =new ArrayList<Transitions>();

        List<String> l1 = Arrays.asList();


        for (String s : lTrans1) {
            l1 = Arrays.asList(s.split(","));
            if (l1.size() >= 2){
                if (alphabet1.contains(l1.get(1))){
                    Transitions t1= new Transitions(l1.get(0) ,l1.get(1) ,l1.get(2));
                    lTransition1.add(t1);
                }else {
                    Transitions t1 = new Transitions(l1.get(0) ,"Є" ,l1.get(2));
                    lTransition1.add(t1);
                }

            }

        }

        Automates automates1 = new Automates(lTransition1,lAlphebt1,lEtats1,lEtatsInit1,lEtatsFinaux1);


        model.addAttribute("graphAutomate1",automateServices.Afficher(automates1,"automate1"));
        model.addAttribute("alphabet1",alphabet1);
        model.addAttribute("etatsInit1",etatsInit1);
        model.addAttribute("etatsFinaux1",etatsFinaux1);
        model.addAttribute("transitions1",transitions1);
        model.addAttribute("etats1",etats1);
        model.addAttribute("minimiser1"," graph {}");
        model.addAttribute("determiniser1"," graph {}");
        model.addAttribute("completer1"," graph {}");

        model.addAttribute("trans1","");
        model.addAttribute("nature1","");


////////////////////////////////////////////////////UNION///////////////////////////////////////////////////////////////


        Automates automates = new Automates();

        model.addAttribute("graphAutomateU",automateServices.Afficher(automates,"UNION"));
        model.addAttribute("alphabetU",automates.getAlphabet());
        model.addAttribute("etatsInitU",automates.getEtatInit());
        model.addAttribute("etatsFinauxU",automates.getEtatFinal());
        model.addAttribute("transitionsU",automates.getTransitions());
        model.addAttribute("etatsU",automates.getEtats());
        model.addAttribute("minimiser"," graph {}");
        model.addAttribute("determiniser"," graph {}");
        model.addAttribute("completer"," graph {}");
        model.addAttribute("graphAutomate"," graph {}");
        model.addAttribute("trans",automateServices.transition(automates));
        model.addAttribute("nature",automateServices.isDeterminiser(automates));


        return "automate";
    }

}
