class test extends Program {
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
        String[] tab = new String[]{"test1","test2","test3","test4"};
        println(tab['a'-'a']);
        println(tab['b'-'a']);
        println(tab['c'-'a']);
        println(tab['d'-'a']);
    }
}