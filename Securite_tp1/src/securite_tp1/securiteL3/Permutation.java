package securiteL3;

import java.util.*;

public class Permutation implements Code {
	
	class Tuple{
		
		int nb;
		char lettre;
		
		public Tuple(char lettre, int nb){
			this.nb = nb;
			this.lettre = lettre;		
		}
		
		public Tuple(char lettre){
			this.lettre = lettre;
			this.nb = 0;
		}
	}
	
	ArrayList<Tuple> occ;
	LinkedList<Character> tab =  new LinkedList<Character>();
	Arbre a;
	
	public static void tableauA(LinkedList<Character> tab3){
		tab3.add('e');
		tab3.add('s');
		tab3.add('a');
		tab3.add('i');
		tab3.add('t');
		tab3.add('n');
		tab3.add('r');
		tab3.add('u');
		tab3.add('l');
		tab3.add('o');
		tab3.add('d');
		tab3.add('c');
		tab3.add('p');
		tab3.add('m');
		tab3.add('v');
		tab3.add('q');
		tab3.add('f');
		tab3.add('b');
		tab3.add('g');
		tab3.add('h');
		tab3.add('j');
		tab3.add('x');
		tab3.add('y');
		tab3.add('z');
		tab3.add('w');
		tab3.add('k');
	}

	public static boolean verif(String cle) {
        boolean[] tab = new boolean[TAILLE_ALPHABET];
        for (int i = 0; i < cle.length(); i++) {
            if (cle.charAt(i) < DEBUT_ALPHABET_ASCII || cle.charAt(i) > FIN_ALPHABET_ASCII) {
                return false;
            }
            tab[cle.charAt(i) - DEBUT_ALPHABET_ASCII] = true;
        }
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            if (!tab[i]) return false;
        }
        return true;
    }

    public static void triBulleDecroissant(ArrayList<Tuple> list) {
        int longueur = list.size();
        int tampon = 0;
        char tampon2 = ' ';
        boolean permut;
        do {
            permut = false;
            for (int i = 0; i < longueur - 1; i++) {
                if (list.get(i).nb < list.get(i + 1).nb) {
                    tampon = list.get(i).nb;
                    tampon2 = list.get(i).lettre;
                    list.get(i).nb = list.get(i+1).nb;
                    list.get(i).lettre = list.get(i+1).lettre;
                    list.get(i+1).nb = tampon;
                    list.get(i+1).lettre = tampon2;
                    permut = true;
                }
            }
        } while (permut);
    }

    @Override
    public String chiffre(String s, String cle) {
        StringBuilder result = new StringBuilder("");
        if (cle.length() == TAILLE_ALPHABET && verif(cle)) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\n' || c == ' ') result.append(c);
                else if (c <= FIN_ALPHABET_ASCII || c >= DEBUT_ALPHABET_ASCII) {
                    int cle_char = (cle.charAt(c - DEBUT_ALPHABET_ASCII) - c);
                    result.append(chiffre(c, cle_char));
                } else {
                    System.err.print("Lettre attendue de a à z ou un espacement.");
                    System.exit(0);
                    return "";
                }
            }
        } else {
            System.err.println("Votre clé n'est pas bonne");
            System.exit(1);
        }
        return result.toString();
    }

    @Override
    public String dechiffre(String s, String cle) {
        StringBuilder result = new StringBuilder("");
        if (cle.length() == TAILLE_ALPHABET && verif(cle)) {
            char[] tab = new char[TAILLE_ALPHABET];
            for(int i= 0; i< cle.length();i++){
                int a = cle.charAt(i)-DEBUT_ALPHABET_ASCII;
                tab[a] = (char)(DEBUT_ALPHABET_ASCII+i);
            }
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\n' || c == ' ') result.append(c);
                else {
                    result.append(dechiffre(c, c - tab[c-DEBUT_ALPHABET_ASCII]));
                }
            }
        } else {
            System.err.println("Votre clé n'est pas bonne");
            System.exit(1);
        }
        return result.toString();
    }

    @Override
    public String decrypt(String... args) {
        String s = args[0];
        tableauA(tab);
        a = new Arbre();
        occ = new ArrayList<Tuple>();
        for(int i = 0;i<TAILLE_ALPHABET;i++){
            occ.add(new Tuple((char)(DEBUT_ALPHABET_ASCII+i)));
        }
        for (Character c : s.toCharArray()) {
            if (c == ' ' || c == '\n') {
                continue;
            }
            if (!in_alpha(c)) {
                System.err.println("Erreur :" + c + "caractère non autorisé");
                System.exit(1);
            }
            occ.get(c - DEBUT_ALPHABET_ASCII).nb += 1;
        }
        triBulleDecroissant(occ);
        LinkedList<TupleC> lifo = new LinkedList<TupleC>();
        Stack<Character> tmp = new Stack<Character>();
        boolean lettre = true;
        int f = 0;
        int z = 0;
        while(lifo.size()<TAILLE_ALPHABET){
            Tuple p = occ.get(f);
            if(tab.isEmpty()){
                System.err.println("Le texte est mal encrypter");
                System.exit(-1);
            }
            char lettreP = tab.pop();
            int deb = -1, fin = -1;
            lifo.add(new TupleC(p.lettre,lettreP));
            ArrayList<Tuple> liste = new ArrayList<Tuple>();
            for(int i = 0;i < s.length();i++){
                if(s.charAt(i) == ' ' || i == s.length()-1 || s.charAt(i) == '\n'){
                    if(!liste.isEmpty()){
                        if(!verifMot(fin+1,liste,lifo)){
                            lettre = false;
                        }
                    }
                    deb = -1;
                    fin = -1;
                    liste.clear();
                }
                else{
                    if(deb == -1){
                        deb = i;
                    }
                    fin += 1;
                    if(s.charAt(i) == p.lettre){
                        liste.add(new Tuple(lettreP,fin));
                    }
                    else{
                        for(int k = 0;k < lifo.size();k++){
                            if(s.charAt(i) == lifo.get(k).lettre1){
                                liste.add(new Tuple(lifo.get(k).lettre2,fin));
                            }
                        }
                    }
                }
            }
            if(lettre){
                f++;
                while(!tmp.isEmpty()){
                    tab.push(tmp.pop());
                }
            }
            else{
                tmp.push(lifo.removeLast().lettre2);
            }
            lettre = true;
            z++;
        }
        String mot = "";
        for(int j = 0;j < TAILLE_ALPHABET;j++){
            for(int i = 0;i < lifo.size();i++){
                if(lifo.get(i).lettre2 == (char)(j+DEBUT_ALPHABET_ASCII)){
                    mot += lifo.get(i).lettre1;
                }
            }
        }
        return dechiffre(s, mot);
    }

    public boolean verifMot(int taille, ArrayList<Tuple> liste, LinkedList<TupleC> lifo){
    	String mot = "";
    	boolean change = false;
    	for(int i = 0;i < taille;i++){
    		for(int j = 0; j < liste.size();j++){
    			if(liste.get(j).nb == i){
    				mot += liste.get(j).lettre;
    				change = true;
    			}
    		}
    		if(!change){
    			mot += '.'; 
    		}
    		change = false;
    	}
    	return (a.listeMots2(mot,lifo));
    }


}
