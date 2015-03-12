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
        String[] texteSplit = texte.split(" ");
        String retour = "";
        int compteur;
        long start = System.currentTimeMillis();
        Arbre arbre = new Arbre(0);
        long end = System.currentTimeMillis();
        System.err.println("Creation Arbre time: " + ((end - start)) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            compteur = 0;
            for (String mot : texteSplit) {
                compteur++;
                String trad = dechiffre(mot, "" + i);
                if (arbre.chercheMot(trad)){
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
        return null;
    }
}

/**
 */