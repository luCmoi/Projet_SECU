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
        Arbre arbre = new Arbre();
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            compteur = 0;
            for (String mot : texteSplit) {
                compteur++;
                String trad = dechiffre(mot, "" + i);
                if (i == 12){
                    System.out.println(trad);
                }
                if (arbre.chercheMot(trad)){
                    retour += trad+" ";
                    if (compteur == texteSplit.length-1){
                        Toolkit.getDefaultToolkit().beep();
                        return retour;
                    }
                }else {
                    System.out.println(i);
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