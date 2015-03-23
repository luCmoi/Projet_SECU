package securiteL3;

import java.util.LinkedList;
import java.util.Set;

public class Noeud {

    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int TAILLE_ALPHABET = 26;

    Noeud listeNoeud[] = null;
    boolean peut_finir = false;


    public Noeud(boolean fin) {
        peut_finir = fin;
    }

    public int addChar(String ligne, int i) {
        int j = i;
        char c;
        do {
            c = ligne.charAt(j);
            switch (c) {
                case '.':
                    j++;
                    return j;
                case '!':
                    j++;
                    this.setPeut_finir(true);
                    break;
                default:
                    if (this.listeNoeud == null) listeNoeud = new Noeud[TAILLE_ALPHABET];
                    listeNoeud[c - DEBUT_ALPHABET_ASCII] = new Noeud(false);
                    j = listeNoeud[c - DEBUT_ALPHABET_ASCII].addChar(ligne, j + 1);
            }
        } while (true);

    }

    public Noeud getFils(char c) {
        if (listeNoeud == null) return null;
        int indice = (c - DEBUT_ALPHABET_ASCII);
        return listeNoeud[indice];

    }

    public boolean is_mot_possible(char[] pattern, String mot, int profondeur, Set<String> liste, LinkedList<TupleC> lifo) {
        boolean in = false;
        if (profondeur == pattern.length - 1) {
            if (pattern[profondeur] == '.') {
                if (listeNoeud != null) {
                    boolean verif = true;
                    for (int i = 0; i < TAILLE_ALPHABET; i++) {
                        for(int j = 0;j < lifo.size();j++){
                            if((char) (DEBUT_ALPHABET_ASCII + i) == lifo.get(j).lettre2) verif = false;
                        }
                        if (this.listeNoeud[i] != null && this.listeNoeud[i].peut_finir && verif) {
                            in = true;
                        }
                        verif = true;
                    }
                }
            } else {
                if (listeNoeud != null && this.listeNoeud[pattern[profondeur] - DEBUT_ALPHABET_ASCII] != null && this.listeNoeud[pattern[profondeur] - DEBUT_ALPHABET_ASCII].peut_finir)
                    in = true;
            }
            return in;
        }
        if (pattern[profondeur] == '.') {
            boolean verif = true;
            for (int i = 0; i < TAILLE_ALPHABET; i++) {
                for(int j = 0;j < lifo.size();j++){
                    if((char) (DEBUT_ALPHABET_ASCII + i) == lifo.get(j).lettre2) verif = false;
                }
                if (listeNoeud != null && listeNoeud[i] != null)
                    in |= listeNoeud[i].is_mot_possible(pattern, mot + (char) (DEBUT_ALPHABET_ASCII + i), profondeur + 1, liste,lifo);
                verif = true;
            }
        } else {
            if (listeNoeud != null && listeNoeud[pattern[profondeur] - DEBUT_ALPHABET_ASCII] != null)
                in |= listeNoeud[pattern[profondeur] - DEBUT_ALPHABET_ASCII].is_mot_possible(pattern, mot + pattern[profondeur], profondeur + 1, liste,lifo);
        }
        return in;
    }

    public void setPeut_finir(boolean peut_finir) {
        this.peut_finir |= peut_finir;
    }

}
