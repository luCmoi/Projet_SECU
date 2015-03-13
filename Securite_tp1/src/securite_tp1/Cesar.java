import java.awt.*;

public class Cesar implements Code {

    @Override
    public String chiffre(String texte, String cle) {
        String retour = "";
        for (Character c : texte.toCharArray()) {
            retour += chiffre(c, Integer.parseInt(cle));
        }
        return retour;

    }

    @Override
    public String dechiffre(String texte, String cle) {
        String retour = "";
        for (Character c : texte.toCharArray()) {
            retour += dechiffre(c, Integer.parseInt(cle));
        }
        return retour;
    }

    @Override
    public String decrypt(String texte) {
        String[] t = texte.split("\n");
        String[][] texteSplit = new String[t.length][];
        for (int i = 0; i < t.length; i++) {
            texteSplit[i] = t[i].split(" ");
        }
        String retour = "";
        String phrase = "";
        int compteur;
        Arbre arbre = new Arbre();
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            compteur = 0;
            for (String[] ligne : texteSplit) {
                for (String mot : ligne) {
                    compteur++;
                    String trad = dechiffre(mot, "" + i);
                    if (arbre.chercheMot(trad) && !trad.equals("")) {
                        retour += trad + " ";
                        if (compteur == texteSplit.length) {
                            Toolkit.getDefaultToolkit().beep();
                            return retour;
                        }
                    } else {
                        retour = "";
                        break;
                    }
                }
            }

        }
        return null;
    }
}