import extensions.CSVFile;

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
    CSVFile fichier = loadCSV("./questions.csv", ';');
    final int rowFichier = rowCount(fichier);
    final int columnFichier = columnCount(fichier);

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
    }

    Question creerTabQuestion() {
        Question q = new Question();
        boolean[] tabTemp = new boolean[rowFichier-1];
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
            r_row = (int) (random()*(rowFichier-1));
            if(q.passe[r_row] == false){
                check = true;
                q.passe[r_row] = true;
            }
        }

        q.domaine = getCell(fichier, r_row, 1);
        q.question = getCell(fichier, r_row, 2);
        q.rep_ok = getCell(fichier, r_row, 3);       
        q.rep_no1 = getCell(fichier, r_row, 4);
        q.rep_no2 = getCell(fichier,r_row, 5);
        q.rep_no3 = getCell(fichier, r_row, 6);
        return q;
    }

    Joueur creerJoueur(){
        Joueur j = new Joueur();
        println("Veuillez entrez votre nom :");
        j.nom = readString();
        println("Veuillez entrez votre prénom :");
        j.prenom = readString();
        j.symbole = ' ';
        String symb = "▪♫☻♣♦♠♥";
        while(j.symbole == ' '){
            println("Veuillez entrez le symbole désiré :"); 
            for(int i = 0 ; i<length(symb);i++){ //a) ▪   b) ♫   c) ☻   d) ♣   e) ♦   f) ♠   g) ♥   
                char lett = (char)('a'+ i);
                print(lett + ") ");
                text("red");
                print(charAt(symb, i) + "   ");
                reset();
            }
            println();
            char sTemp = readChar();
            if(sTemp == 'a'){
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
        }
        j.pos = 0;
        j.erreur = 0;
        return j;    
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

    Question afficheQuestion(Question q){
        q = creerQuestion(q);
        println("Domaine : " + q.domaine);
        println("Question : " + q.question);
        return q;
    }

    Joueur reponseQuestion(Question q, Joueur j){
        String[] rep = new String[]{q.rep_ok,q.rep_no1,q.rep_no2,q.rep_no3};
        melangerTab(rep);
        String af_que = "";
        af_que += "a) " + rep[0] + "    b) " + rep[1] + "    c) " + rep[2] + "    d) " + rep[3];
        println(af_que);
        char entre;
        do{
            entre = readChar();
            if(rep[entre-'a'] != q.rep_ok){
                j.erreur += 1;
                println("Mauvaise réponse, veuillez réessayer : ");
            } else {
                j.pos += 1;
            }
        } while(rep[entre-'a'] != q.rep_ok);
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

    void algorithm(){
        clearScreen();
        Joueur j = creerJoueur();
        boolean[] r_case = new boolean[10];
        r_case = creerRandCase();
        Question question = new Question();
        question = creerTabQuestion();
        boolean continuee = true;
        while(j.pos < 10 && continuee){
            clearScreen();
            affichePlateau(j, r_case);
            question = afficheQuestion(question);
            j = reponseQuestion(question, j);

        }
        clearScreen();
        println("Bravo à vous, " + j.prenom + ".")
        println("Vous êtes arrivé à la fin avec un total de " + j.erreur + " fautes.");
        println();
    }
}

