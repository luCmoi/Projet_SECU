import java.util.Set;

public class Noeud {

    Noeud listeNoeud[] = null;
    boolean peut_finir = false;


    public Noeud(boolean fin) {
        peut_finir = fin;
    }

    public Noeud addFils(char c, boolean fin) {
        if (listeNoeud == null) listeNoeud = new Noeud[26];
        int indice = (c - 97);
        Noeud n = new Noeud(fin);
        if (listeNoeud[indice] == null) listeNoeud[indice] = n;
        else listeNoeud[indice].setPeut_finir(fin);
        return listeNoeud[indice];

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
                    if (this.listeNoeud == null) listeNoeud = new Noeud[26];
                    listeNoeud[c - 97] = new Noeud(false);
                    j = listeNoeud[c - 97].addChar(ligne, j + 1);
            }
        } while (true);

    }

    public Noeud getFils(char c) {
        if (listeNoeud == null) return null;
        int indice = (c - 97);
        return listeNoeud[indice];

    }

    public Set<String> getMot(char[] pattern, String mot, int profondeur, Set<String> liste) {
        if (profondeur == pattern.length - 1) {
            if (pattern[profondeur] == '.') {
                if (listeNoeud != null) {
                    for (int i = 0; i < 26; i++) {
                        if (this.listeNoeud[i] != null && this.listeNoeud[i].peut_finir) {
                            liste.add(mot + (char) ('a' + i));
                        }
                    }
                }
            } else {
                if (listeNoeud != null && this.listeNoeud[pattern[profondeur] - 97] != null && this.listeNoeud[pattern[profondeur] - 97].peut_finir)
                    liste.add(mot + pattern[profondeur]);
            }
            return liste;
        }
        if (pattern[profondeur] == '.') {
            for (int i = 0; i < 26; i++) {
                if (listeNoeud != null && listeNoeud[i] != null)
                    liste.addAll(listeNoeud[i].getMot(pattern, mot + (char) ('a' + i), profondeur + 1, liste));
            }
        } else {
            if (listeNoeud != null && listeNoeud[pattern[profondeur] - 97] != null)
                liste.addAll(listeNoeud[pattern[profondeur] - 97].getMot(pattern, mot + pattern[profondeur], profondeur + 1, liste));
        }
        return liste;
    }


    public void setPeut_finir(boolean peut_finir) {
        this.peut_finir |= peut_finir;
    }

}
