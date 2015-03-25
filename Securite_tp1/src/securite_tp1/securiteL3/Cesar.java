package securiteL3;


public class Cesar implements Code {

    @Override
    public String chiffre(String texte, String cle) {
        StringBuilder retour = new StringBuilder("");
        for (Character c : texte.toCharArray()) {
            retour.append(chiffre(c, Integer.parseInt(cle)));
        }
        return retour.toString();
    }

    @Override
    public String dechiffre(String texte, String cle) {
        StringBuilder retour = new StringBuilder("");
        for (Character c : texte.toCharArray()) {
            retour.append(dechiffre(c, Integer.parseInt(cle)));
        }
        return retour.toString();
    }

    public String decrypt(String... args) {
        String[][] texteSplit = splitation(args[0]);
        int cle = -1;
        switch (Integer.parseInt(args[1])) {
            case 1:
                cle = decryptMot(texteSplit, args[2]);
                break;
            case 2:
                cle = decryptFreq(texteSplit);
                break;
            case 3:
                cle = decryptFB(texteSplit);
                break;
        }
        if (cle != -1) {
            return dechiffre(args[0], "" + cle);
        } else {
            System.err.println("Le texte n'a pas pu être décrypté.");
        }
        return null;
    }

    public int decryptMot(String[][] texteSplit, String motConnu) {
        int[] ecarts = new int[motConnu.length()];
        Arbre arbre = new Arbre();
        char pred = motConnu.charAt(0);
        for (int i = 1; i < motConnu.length(); i++) {
            ecarts[i] = motConnu.charAt(i) - pred;
            pred = motConnu.charAt(i);
        }
        ecarts[0] = pred - motConnu.charAt(0);
        boolean mauvaisMot;
        int decallage;
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
                        if (decallage < 0) {
                            decallage += TAILLE_ALPHABET;
                        }
                        //String texteRetour = decryptParcour(arbre, texteSplit, decallage);
                        //if (texteRetour != null) {
                        //    return decallage;
                        //}
                        return decryptParcour(arbre, texteSplit, decallage);
                    }
                }
            }
        }
        return -1;
    }

    public int decryptFreq(String[][] texteSplit) {
        Arbre arbre = new Arbre();
        for (char lettre : FREQUENCE_FRANCAIS) {
            int cle = get_freq(texteSplit, lettre);
            /*String texteRetour = decryptParcour(arbre, texteSplit, cle);
            if (texteRetour != null) {
                return cle;
            }*/
            int boucle = decryptParcour(arbre, texteSplit, cle);
            if (boucle != -1) {
                return boucle;
            }
        }
        return -1;
    }

    public int decryptFB(String[][] texteSplit) {
        Arbre arbre = new Arbre();
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            /*String texteRetour = decryptParcour(arbre, texteSplit, i);
            if (texteRetour != null) {
                return i;
            }*/
            int boucle = decryptParcour(arbre, texteSplit, i);
            if (boucle != -1) {
                return boucle;
            }
        }
        return -1;
    }

    public String[][] splitation(String texte) {
        String[] lignes = texte.split("\n");
        String[][] texteSplit = new String[lignes.length][];
        for (int i = 0; i < lignes.length; i++) {
            texteSplit[i] = lignes[i].split(" ");
        }
        return texteSplit;
    }

    public int decryptParcour(Arbre arbre, String[][] texteSplit, int cle) {
        /*StringBuilder retour = new StringBuilder("");
        boolean debutLigne;
        for (String[] ligne : texteSplit) {
            debutLigne = true;
            for (String mot : ligne) {
                String trad = dechiffre(mot, "" + cle);
                if (arbre.chercheMot(trad) && !trad.equals("")) {
                    if (debutLigne) {
                        debutLigne = false;
                        if (!retour.toString().equals("")) retour.append("\n");
                    }
                    retour.append(trad);
                    retour.append(" ");
                } else {
                    return null;
                }
            }
        }
        if (!retour.equals("")) {
            Toolkit.getDefaultToolkit().beep();
            return retour.substring(0, retour.length() - 1);
        }
        return null;*/
        for (String[] ligne : texteSplit) {
            for (String mot : ligne) {
                String trad = dechiffre(mot, "" + cle);
                if (!arbre.chercheMot(trad) || trad.equals("")) {
                    return -1;
                }
            }
        }
        return cle;
    }

    char get_freq(String[][] s, char lettre) {
        int[] tab = new int[TAILLE_ALPHABET];
        for (String[] ligne : s) {
            for (String mot : ligne) {
                for (int i = 0; i < mot.length(); i++) {
                    tab[mot.charAt(i) - DEBUT_ALPHABET_ASCII]++;
                }
            }
        }
        int max = 0;
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            if (tab[i] > tab[max]) max = i;
        }
        int cle = (max - (lettre - DEBUT_ALPHABET_ASCII));
        return (cle < 0) ? (char) (cle + TAILLE_ALPHABET) : (char) cle;
    }
}