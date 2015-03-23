package securiteL3;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Arbre {

    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int TAILLE_ALPHABET = 26;

    Noeud racine[] = new Noeud[TAILLE_ALPHABET];


    public Arbre() {
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            racine[i] = new Noeud(false);
        }
        String fichier = "./lexiqueA.txt";
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                int i = ligne.charAt(0) - DEBUT_ALPHABET_ASCII;
                this.racine[i].addChar(ligne, 1);

            }
            br.close();
        } catch (Exception r) {
            r.printStackTrace();
        }
    }

    boolean listeMots2(String pattern, LinkedList<TupleC> lifo) {
        char[] mot = new char[pattern.length()];
        for (int i = 0; i < mot.length; i++) {
            mot[i] = pattern.charAt(i);
        }
        return listeMots_rec2(mot, "", 0, lifo);
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

    private boolean listeMots_rec2(char[] pattern, String mot, int profondeur, LinkedList<TupleC> lifo) {
        if (pattern[profondeur] == '.') {
            for (int i = 0; i < TAILLE_ALPHABET; i++) {
                return racine[i].getMot2(pattern, mot + (char) (DEBUT_ALPHABET_ASCII + i), profondeur + 1, lifo);
            }
        } else {
            return racine[pattern[profondeur] - DEBUT_ALPHABET_ASCII].getMot2(pattern, mot + pattern[profondeur], profondeur + 1, lifo);
        }
        return false;
    }
}
