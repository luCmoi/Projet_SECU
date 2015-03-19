package securiteL3;

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

    public String decrypt(String... args) {
        switch (Integer.parseInt(args[1])) {
            case 1:
                return decryptMot(args[0], args[2]);
            case 2:
                return "";
            case 3:
                return decryptFB(args[0]);
        }
        return null;
    }

    public String decryptMot(String texte, String motConnu) {
        String[] lignes = texte.split("\n");
        String[][] texteSplit = new String[lignes.length][];
        for (int i = 0; i < lignes.length; i++) {
            texteSplit[i] = lignes[i].split(" ");
        }
        int[] ecarts = new int[motConnu.length()];
        char pred = motConnu.charAt(0);
        for (int i = 1; i < motConnu.length(); i++) {
            ecarts[i] = motConnu.charAt(i) - pred;
            pred = motConnu.charAt(i);
        }
        ecarts[0] = pred - motConnu.charAt(0);
        boolean mauvaisMot = false;
        int decallage = -1;
        for (String[] ligne : texteSplit) {
            for (String mot : ligne) {
                mauvaisMot = false;
                if (mot.length() == motConnu.length()) {
                    pred = mot.charAt(0);
                    for (int i = 1; i < motConnu.length(); i++) {
                        if (mot.charAt(i) - pred == ecarts[i]) {
                            mauvaisMot = true;
                            break;
                        }
                    }
                    if (!mauvaisMot) {
                        decallage = mot.charAt(0) - motConnu.charAt(0);
                        if (decallage < DEBUT_ALPHABET_ASCII) {
                            decallage += TAILLE_ALPHABET;
                        }
                        String texteRetour = decryptParcour(texteSplit,decallage);
                        if (texteRetour != null){
                            return texteRetour;
                        }
                    }
                }
            }
        }
        return null;
    }

    public String decryptFB(String texte) {
        String[] lignes = texte.split("\n");
        String[][] texteSplit = new String[lignes.length][];
        for (int i = 0; i < lignes.length; i++) {
            texteSplit[i] = lignes[i].split(" ");
        }
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            String texteRetour = decryptParcour(texteSplit,i);
            if (texteRetour != null){
                return texteRetour;
            }
        }
        return null;
    }

    public String decryptParcour(String[][] texteSplit, int cle){
        String retour = "";
        Arbre arbre = new Arbre();
        boolean debutLigne = false;
        for (String[] ligne : texteSplit) {
            debutLigne = true;
            for (String mot : ligne) {
                String trad = dechiffre(mot, "" + cle);
                if (arbre.chercheMot(trad) && !trad.equals("")) {
                    if (debutLigne){
                        debutLigne=false;
                        if (retour != "") retour += "\n";
                    }
                    retour += trad + " ";
                } else {
                    return null;
                }
            }
        }
        if (!retour.equals("")) {
            Toolkit.getDefaultToolkit().beep();
            return retour.substring(0,retour.length()-1);
        }
        return null;
    }
}