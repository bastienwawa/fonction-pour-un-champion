import extensions.CSVFile;
import extensions.File;

/*
 /########                                 /##     /##                                                                        
| ##_____/                                | ##    |__/                                                                        
| ##        /######  /#######   /####### /######   /##  /######  /#######               /######   /######  /##   /##  /###### 
| #####    /##__  ##| ##__  ## /##_____/|_  ##_/  | ## /##__  ##| ##__  ##             /##__  ## /##__  ##| ##  | ## /##__  ##
| ##__/   | ##  \ ##| ##  \ ##| ##        | ##    | ##| ##  \ ##| ##  \ ##            | ##  \ ##| ##  \ ##| ##  | ##| ##  \__/
| ##      | ##  | ##| ##  | ##| ##        | ## /##| ##| ##  | ##| ##  | ##            | ##  | ##| ##  | ##| ##  | ##| ##      
| ##      |  ######/| ##  | ##|  #######  |  ####/| ##|  ######/| ##  | ##            | #######/|  ######/|  ######/| ##      
|__/       \______/ |__/  |__/ \_______/   \___/  |__/ \______/ |__/  |__/            | ##____/  \______/  \______/ |__/      
                                                                                      | ##                                    
                                                                                      | ##                                    
                                                                                      |__/                                    
                                                       /##                                         /##                        
                                                      | ##                                        |__/                        
             /##   /## /#######               /#######| #######   /######  /######/####   /######  /##  /######  /#######     
            | ##  | ##| ##__  ##             /##_____/| ##__  ## |____  ##| ##_  ##_  ## /##__  ##| ## /##__  ##| ##__  ##    
            | ##  | ##| ##  \ ##            | ##      | ##  \ ##  /#######| ## \ ## \ ##| ##  \ ##| ##| ##  \ ##| ##  \ ##    
            | ##  | ##| ##  | ##            | ##      | ##  | ## /##__  ##| ## | ## | ##| ##  | ##| ##| ##  | ##| ##  | ##    
            |  ######/| ##  | ##            |  #######| ##  | ##|  #######| ## | ## | ##| #######/| ##|  ######/| ##  | ##    
             \______/ |__/  |__/             \_______/|__/  |__/ \_______/|__/ |__/ |__/| ##____/ |__/ \______/ |__/  |__/    
                                                                                        | ##                                  
                                                                                        | ##                                  
                                                                                        |__/                                

*/

class FonctionPourUnChampion extends Program {
    CSVFile fichierQ = loadCSV("../ressources/questions.csv", ';');
    CSVFile save = loadCSV("../ressources/save.csv", ',');
    AfficheAscii af = new AfficheAscii();
    SaveAll s = new SaveAll();
    final int rowFichierQ = rowCount(fichierQ);
    final int columnFichierQ = columnCount(fichierQ);
    final int rowSave = rowCount(save);
    final int columnSave = columnCount(save);



    void affichePlateau(Joueur j, boolean[] r){
        println();
        /* Exemple du plateau :
        ║     ║     ║     ║     ║     ║     ║     ║     ║     ║     ║
        ╚═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╝
        */
        

        //haies :
        for (int i=0;i<10;i++) {
            if (i == j.pos) {
                print("║  ");
                text("red");
                print(j.symbole);
                reset();
                print("  ");
            } else {
                print("║     ");
            }
        }
        println("║");

        // partie basse du plateau :
        print("╚═════");
        for (int i=1;i<10;i++) {
            print("╩");
            if(r[i] == true) {
                text("green");
                print("═════");
                reset();
            } else {
                print("═════");
            }
        }
        println("╝");

        //Si case aléatoire : 
        if(j.pos != 0 && r[j.pos] == true){
            text("yellow");
            println();
            println("                      Case Aléatoire !");
            reset();
        } else {
            println();
            println();
        }
    }
    
    void afficheMenu(){
        clearScreen();
        af.affNomJeu("menu");
        println();
        int choix;
        do {
            print("Entrez un chiffre pour accéder aux diffèrentes options : ");
            choix = verifInt(readString());
            if(choix == 12081949){
                event(false);
                choix = 0;
                afficheMenu();
            } else if(choix == 1){
                //lance une nouvelle partie
                choix = 0;
                jouer();
            } else if(choix == 2){
                //ouvre un menu pour reprendre une partie
                choix = 0;
                reprendreJouer();
            } else if(choix == 3){
                //Affiche les règles
                choix = 0;
                reglement();
            } else if(choix != 0 && choix != 1 && choix != 2 && choix != 3){
                choix = 0;
                afficheMenu();
            }
        } while(choix < 0 || choix > 3);
    }

    void event(boolean b){
        clearScreen();
        af.affNomJeu("event");
        if(b == true){
            delay(2000);
            clearScreen();
            println();
            text("yellow");
            af.affNomJeu("c_alea");
            reset();
        }
        delay(2000);
    }

    void reglement(){
        clearScreen();
        af.affNomJeu("regle");
        int choixR;
        choixR = verifInt(readString());
        if(choixR == 12081949){
            event(false);
        } else if(choixR != 0){
            reglement();
        }
        afficheMenu();
    }

    Question creerTabQuestion() {
        Question q = new Question();
        boolean[] tabTemp = new boolean[rowFichierQ-1];
        for(int i = 0; i < length(tabTemp); i++){
            tabTemp[i] = false;
        } 
        q.passe = tabTemp;
        q.domaine = "";
        q.question = "";
        q.rep_ok = "";
        q.rep_no1 = "";
        q.rep_no2 = "";
        q.rep_no3= "";
        return q;
    }

    Question creerQuestion(Question q){
        boolean check = false;
        int r_row = 0;
        boolean present;
        while(check == false){
            r_row = (int) (random()*(rowFichierQ-1));
            if(q.passe[r_row] == false){
                check = true;
                q.passe[r_row] = true;
            }
        }

        q.domaine = getCell(fichierQ, r_row, 1);
        q.question = getCell(fichierQ, r_row, 2);
        q.rep_ok = getCell(fichierQ, r_row, 3);       
        q.rep_no1 = getCell(fichierQ, r_row, 4);
        q.rep_no2 = getCell(fichierQ,r_row, 5);
        q.rep_no3 = getCell(fichierQ, r_row, 6);
        return q;
    }

    Joueur creerJoueur(){
        Joueur j = new Joueur();
        println();
        println("Veuillez entrez votre nom : (minimum 3 caractères)");
        do {
            j.nom = readString();
        } while(length(j.nom) < 3);
        println("Veuillez entrez votre prénom : (minimum 3 caractères)");
        do {
            j.prenom = readString();
        } while(length(j.prenom) < 3);
        j.symbole = ' ';
        String symb = "▪♫☻♣♦♠♥";
        while(j.symbole == ' '){
            println();
            char sTemp;
            do{
                println("Veuillez entrez le symbole désiré :"); 
                for(int i = 0 ; i<length(symb);i++){ //a) ▪   b) ♫   c) ☻   d) ♣   e) ♦   f) ♠   g) ♥   
                    char lett = (char)('a'+ i);
                    print(lett + ") ");
                    text("red");
                    print(charAt(symb, i) + "   ");
                    reset();
                }
                sTemp = verifChar(readString());
                if(sTemp == 'ի'){
                    event(false);
                } else if(sTemp == 'a'){
                    j.symbole = charAt(symb, ('a'-'a'));
                } else if(sTemp == 'b'){
                    j.symbole = charAt(symb, ('b'-'a'));
                } else if(sTemp == 'c'){
                    j.symbole = charAt(symb, ('c'-'a'));
                } else if(sTemp == 'd'){
                    j.symbole = charAt(symb, ('d'-'a'));
                } else if(sTemp == 'e'){
                    j.symbole = charAt(symb, ('e'-'a'));
                } else if(sTemp == 'f'){
                    j.symbole = charAt(symb, ('f'-'a'));
                } else if(sTemp == 'g'){
                    j.symbole = charAt(symb, ('g'-'a'));
                } 
            } while(sTemp < 'a' && sTemp > 'g');
        }
        j.pos = 0;
        j.erreur = 0;
        j.stop = false;
        return j;    
    }

    char verifChar(String inter){
        char sF;
        if(length(inter) == 0){
            sF = 'i';
        } else if(equals(inter,"lepers")){
            sF = 'ի';
        } else {
            sF = charAt(inter, 0);
        }
        return sF;
    }
    
    int verifInt(String inter){
        int sF;
        if(length(inter) == 0){
            sF = 999;
        } else if(equals(inter,"lepers")){
            sF = 12081949;
        } else {
            sF = (int)(charAt(inter, 0))-48;
        }
        return sF;
    }
    
    boolean[] creerRandCase(){
        boolean[] r_case = new boolean[10];
        for(int i = 0; i<length(r_case);i++){
            int r_num = (int)(random()*3);
            if(r_num == 1){
                r_case[i] = true;
            } else {
                r_case[i] = false;
            }
        }
        return r_case;
    }

    void afficheQuestion(Question q){
        println();println();
        af.affNomJeu(q.domaine);
        println();println();
        println("      " + q.question);
        println();
    }

    Joueur reponseQuestion(Question q, Joueur j, boolean[] r_case){
        boolean f = false;
        do{
            afficheQuestion(q);
            String[] rep = new String[]{q.rep_ok,q.rep_no1,q.rep_no2,q.rep_no3};
            melangerTab(rep);
            String af_que = "";
            af_que += "  a) " + rep[0] + "    b) " + rep[1] + "    c) " + rep[2] + "    d) " + rep[3];
            print(af_que);
            text("cyan");
            println("    e) Pour quitter.");
            reset();
            char entre;
            do{
                entre = verifChar(readString());
            } while(entre < 'a' || entre > 'e');
            if(entre == 'e'){
                f = true;
                j.stop = true;
            } else if(rep[entre-'a'] == q.rep_ok){
                j.pos += 1;
                f = true;
            } else if(rep[entre-'a'] != q.rep_ok){
                j.erreur += 1;
                clearScreen();
                println("      Mauvaise réponse, veuillez réessayer : ");
                println();
                affichePlateau(j, r_case);
            }
        } while(f == false);
        clearScreen();
        return j;
    }

    void afficheQuestionR(Question q){
        println();println();
        af.affNomJeu(q.domaine);
        println();
        text("purple");
        println("Question Aléatoire :");
        reset();
        println("      " + q.question);
        println();
    }

    Joueur reponseQuestionR(Question q, Joueur j){
        afficheQuestionR(q);
        String[] rep = new String[]{q.rep_ok,q.rep_no1,q.rep_no2,q.rep_no3};
        melangerTab(rep);
        String af_que = "";
        af_que += "  a) " + rep[0] + "    b) " + rep[1] + "    c) " + rep[2] + "    d) " + rep[3];
        println(af_que);
        char entre;
        do{
            entre = verifChar(readString());
        } while(entre < 'a' || entre > 'd');
        if(rep[entre-'a'] != q.rep_ok){
            j.erreur += 1;
            j.pos -= 1;
        }
        return j;
    }

    void melangerTab(String[] tab){
        for (int i = 0; i<20; i++){
            int index1 = random(0, length(tab));
            int index2 = random(0, length(tab));
            String tempVar = tab[index1];
            tab[index1] = tab[index2];
            tab[index2] = tempVar;
        }
    }

    int random(int min, int max){
        return (int)(random()*(max-min))+min;
    }

    void deroulement(Joueur j, Question question, boolean[] r_case, boolean rep, int nb_li){
        boolean continuee = true;
        String[] saveTemp = new String[15];
        int repIntOui;
        if(rep == true){
            repIntOui = 0;
        } else {
            repIntOui = 1;
        }
        String[][] saveFi = new String[rowSave+repIntOui][15];
        while(j.pos < 10 && continuee){
            clearScreen();
            affichePlateau(j, r_case);
            while(j.pos > 0 && r_case[j.pos] == true){
                event(true);
                clearScreen();
                affichePlateau(j, r_case);
                r_case[j.pos] = false;
                int r = (int)(random()*3);
                if(r == 0){
                    println("               Vous allez reculer d'une case !");
                    j.pos -= 1;
                    delay(2000);
                } else if(r == 1 && j.pos < 9){
                    println("               Vous allez avancer d'une case !");
                    j.pos += 1;
                    delay(2000);
                } else if(r == 2){
                    question = creerQuestion(question);
                    j = reponseQuestionR(question, j);
                }
                clearScreen();
                affichePlateau(j, r_case);
            }
            question = creerQuestion(question);
            j = reponseQuestion(question, j, r_case);
            if(j.stop == true && j.pos < 10){
                continuee = false;
                saveTemp = s.sauvegardeJ(j, r_case);
                for(int li = 0; li < rowSave;li++){
                    for(int co = 0; co < columnSave;co++){
                        saveFi[li][co] = getCell(save, li, co);
                    }
                }
                for(int i = 0;i < length(saveTemp);i++){
                    if(rep == true){
                        saveFi[nb_li][i] = saveTemp[i];
                    } else {
                        saveFi[rowSave][i] = saveTemp[i];
                    }
                }
                saveCSV(saveFi, "../ressources/save.csv");
            }
        }
        if(continuee == true){
            println("Bravo à vous, " + j.prenom + ".");
            println("Vous êtes arrivé à la fin avec un total de " + j.erreur + " fautes.");
        } else {
            println("Partie sauvegardé.");
            println("Vous pourrez reprendre votre partie plus tard.");
            println("À bientot");
            println();
        }
    }

    void jouer(){
        Joueur j = creerJoueur();
        boolean[] r_case = creerRandCase();
        Question question = new Question();
        question = creerTabQuestion();
        deroulement(j, question, r_case, false, 0);
    }



    void reprendreJouer(){
        boolean f = false;
        do{
            Joueur j = new Joueur();
            int choixP;
            do{
                clearScreen();
                af.affNomJeu("sauv");
                for(int i = 1;i<rowSave;i++){
                    println("   " + i + ") Nom: " + getCell(save, i, 0) + " , Prénom: " + getCell(save, i, 1) + " , Symbole: " + getCell(save, i, 2) + " , Position: "+ ((int)(charAt(getCell(save, i, 3),0))-47));
                }
                println();
                print("        Veuillez en selectionner une : (Mettre 0 pour revenir au menu) ");
                choixP = verifInt(readString());
            } while(choixP < 0 || choixP > rowSave - 1);
            if(choixP != 0){
                f = true;
                j.nom = getCell(save, choixP, 0);
                j.prenom = getCell(save, choixP, 1);
                j.symbole = charAt(getCell(save, choixP, 2),0);
                j.pos = (int)(charAt(getCell(save, choixP, 3),0))-48;
                j.erreur = (int)(charAt(getCell(save, choixP, 4),0))-48;
                j.stop = false;
                boolean[] r_case = new boolean[10];
                for(int i = 0; i < 10; i++){
                    if(equals(getCell(save, choixP, i+5), "true")){
                        r_case[i] = true;
                    } else {
                        r_case[i] = false;
                    }
                }
                Question question = new Question();
                question = creerTabQuestion();
                deroulement(j, question, r_case, true, choixP);
            } else {
                f = true;
                afficheMenu();
            }
        } while(f == false);
    }

    void init(){
        clearScreen();
        for(int i = 0; i<50; i++){
            println();
        }
    }

    void algorithm(){
        init();
        afficheMenu();
    }
}