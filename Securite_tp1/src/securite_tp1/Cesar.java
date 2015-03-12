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
        String [][] texteSplit = new String[t.length][];
        for (int i = 0; i < t.length; i++) {
            texteSplit[i] = t[i].split(" ");
        }
        String retour = "";
        String phrase = "";
        int compteur;
        long start = System.currentTimeMillis();
        Arbre arbre = new Arbre(0);
        long end = System.currentTimeMillis();
        System.err.println("Creation Arbre time: " + ((end - start)) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            compteur = 0;
            for (String[] ligne : texteSplit){
                for (String mot : ligne) {
                    compteur++;
                    String trad = dechiffre(mot, "" + i);
                    System.out.println(trad);
                    if (arbre.chercheMot(trad) && !trad.equals("")){
                        retour += trad+" ";
                        if (compteur == texteSplit.length){
                            end = System.currentTimeMillis();
                            System.err.println("Fin decrypt Cesar time: " + ((end - start)) + " ms");
                            Toolkit.getDefaultToolkit().beep();
                            return retour;
                        }
                    }else {
                        retour = "";
                        break;
                    }
                }
            }

        }
        return null;
    }
}

/**
 */