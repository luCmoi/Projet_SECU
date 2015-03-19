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
        int[] ecarts = new int[motConnu.length() - 1];
        char pred = motConnu.charAt(0);
        for (int i = 1; i < motConnu.length(); i++) {
            ecarts[i] = motConnu.charAt(i) - pred;
            pred = motConnu.charAt(i);
        }
        ecarts[0] = pred - motConnu.charAt(0);
        boolean mauvaisMot = false;
        int decallage = -1;
        Arbre arbre = new Arbre();
        String retour = "";
        for (String[] ligne : texteSplit) {
            for (String mot : ligne) {
                //On cherche le mot
                mauvaisMot = false;
                if (mot.length() == motConnu.length()) {
                    pred = mot.charAt(0);
                    for (int i = 1; i < motConnu.length(); i++) {
                        if (mot.charAt(i) - pred == ecarts[i]) {
                            mauvaisMot = true;
                            break;
                        }
                    }
                    //On a le mot
                    if (!mauvaisMot) {
                        decallage = mot.charAt(0) - motConnu.charAt(0);
                        if (decallage < DEBUT_ALPHABET_ASCII) {
                            decallage += TAILLE_ALPHABET;
                        }
                        boolean stop = false;
                        for (String[] ligneB : texteSplit) {
                            if (stop) {
                                break;
                            }
                            for (String motB : ligneB) {
                                String trad = dechiffre(motB, "" + decallage);
                                if (arbre.chercheMot(trad) && !trad.equals("")) {
                                    retour += trad + " ";
                                } else {
                                    retour = "";
                                    stop = true;
                                    break;
                                }
                            }
                        }
                        if (!retour.equals("")) {
                            Toolkit.getDefaultToolkit().beep();
                            return retour;
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
        String retour = "";
        Arbre arbre = new Arbre();
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            boolean stop = false;
            for (String[] ligne : texteSplit) {
                if (stop) {
                    break;
                }
                for (String mot : ligne) {
                    String trad = dechiffre(mot, "" + i);
                    if (arbre.chercheMot(trad) && !trad.equals("")) {
                        retour += trad + " ";
                    } else {
                        retour = "";
                        stop = true;
                        break;
                    }
                }
            }
            if (!retour.equals("")) {
                Toolkit.getDefaultToolkit().beep();
                return retour;
            }
        }
        return null;
    }
}