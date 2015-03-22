package securiteL3;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Arbre {

    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int FIN_ALPHABET_ASCII = 122;
    static final int TAILLE_ALPHABET = 26;

    Noeud racine[] = new Noeud[TAILLE_ALPHABET];


    public Arbre() {
        for (int i = 0; i < 26; i++) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean in_alpha(char c){
        return c>=DEBUT_ALPHABET_ASCII && c<=FIN_ALPHABET_ASCII;
    }
    Set<String> listeMots(String pattern) {
        char[] mot = new char[pattern.length()];
        for (int i = 0; i < mot.length; i++) {
            mot[i] = pattern.charAt(i);
        }
        return listeMots_rec(mot, "", 0, new HashSet<>());
    }

    private Set<String> listeMots_rec(char[] pattern, String mot, int profondeur, Set<String> liste) {
        if (pattern[profondeur] == '.') {
            for (int i = 0; i < TAILLE_ALPHABET; i++) {
                liste.addAll(racine[i].getMot(pattern, mot + (char) (DEBUT_ALPHABET_ASCII + i), profondeur + 1, liste));
            }
        } else {
            liste.addAll(racine[pattern[profondeur] - DEBUT_ALPHABET_ASCII].getMot(pattern, mot + pattern[profondeur], profondeur + 1, liste));
        }
        return liste;
    }

    boolean chercheMot(String s) {
        byte indice = (byte) (s.charAt(0) - DEBUT_ALPHABET_ASCII);
        Noeud tmp = racine[indice];
        for (int i = 1; i < s.length(); i++) {
            if(! in_alpha(s.charAt(i))) return false;
            tmp = tmp.getFils(s.charAt(i));
            if (tmp == null) return false;
        }
        return tmp.peut_finir;
    }
}
