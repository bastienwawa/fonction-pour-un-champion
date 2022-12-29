import extensions.File;
class AfficheAscii extends Program {
    void affNomJeu(String file){
        file = toLowerCase(file);
        file = "../ressources/" + file + ".txt";
        File f = newFile(file);
        while(ready(f)){
            println(readLine(f));
        }
    }
}