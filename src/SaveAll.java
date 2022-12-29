class SaveAll extends Program{
    String[] sauvegardeJ(Joueur j, boolean[] r_case){
        String[] saveJ = new String[15];
        saveJ[0] = j.nom;
        saveJ[1] = j.prenom;
        saveJ[2] = "" + j.symbole;
        saveJ[3] = "" + j.pos;
        saveJ[4] = "" + j.erreur;
        for(int i = 0; i<length(r_case);i++){
            if(r_case[i] == true){
                saveJ[i+5] = "true";
            } else {
                saveJ[i+5] = "false";
            }
        }
        return saveJ;
    }
}