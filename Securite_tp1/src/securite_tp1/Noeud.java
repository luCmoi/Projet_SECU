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

    public Noeud getFils(char c) {
        if (listeNoeud == null) return null;
        int indice = (c - 97);
        return listeNoeud[indice];

    }

    public void setPeut_finir(boolean peut_finir) {
        this.peut_finir |= peut_finir;
    }

}
