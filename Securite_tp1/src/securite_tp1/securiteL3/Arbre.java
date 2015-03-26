package securiteL3;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;

public class Arbre {

    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int TAILLE_ALPHABET = 26;

    Noeud racine[] = new Noeud[TAILLE_ALPHABET];


    public Arbre() {
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            racine[i] = new Noeud(false);
        }
        String fichier = "./lexiqueA.txt";
        int read, N = 1024 * 8;
        char[] buffer = new char[N];
        StringBuilder text = new StringBuilder();

        try {
            FileReader fr = new FileReader(fichier);
            BufferedReader br = new BufferedReader(fr);
            while (true) {
                read = br.read(buffer, 0, N);
                text.append(buffer, 0, read);
                if (read < N) {
                    break;
                }
            }
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }
        StringTokenizer st = new StringTokenizer(text.toString(), " \n");
        byte i = 0;
        while (st.hasMoreTokens()) {
            this.racine[i].addChar(st.nextToken().toCharArray(), 1);
            i++;
        }
    }

    boolean listeMots2(String pattern, LinkedList<Permutation.TupleC> lifo) {
        char[] mot = new char[pattern.length()];
        for (int i = 0; i < mot.length; i++) {
            mot[i] = pattern.charAt(i);
        }
        return listeMots_rec2(mot, "", 0, new HashSet<>(),lifo);
    }

    boolean chercheMot(String s) {
        byte indice = (byte) (s.charAt(0) - DEBUT_ALPHABET_ASCII);
        Noeud tmp = racine[indice];
        for (int i = 1; i < s.length(); i++) {
            tmp = tmp.getFils(s.charAt(i));
            if (tmp == null) return false;
        }
        return tmp.peut_finir;
    }

    private boolean listeMots_rec2(char[] pattern, String mot, int profondeur, Set<String> liste, LinkedList<Permutation.TupleC> lifo) {
        boolean in = false;
        if(pattern.length == 1){
            return racine[pattern[profondeur] - 97].peut_finir;
        }
        if (pattern[profondeur] == '.') {
            for (int i = 0; i < TAILLE_ALPHABET; i++) {
                in |= racine[i].is_mot_possible(pattern, mot + (char) (DEBUT_ALPHABET_ASCII + i), profondeur + 1, liste,lifo);
            }
        } else {
            in = racine[pattern[profondeur] - 97].is_mot_possible(pattern, mot + pattern[profondeur], profondeur + 1, liste, lifo);
        }
        return in;
    }
}
