public class Cesar implements Code {


    @Override
    public char chiffre(char c, int cle) {
        if (c != ' ') {
            assert c <= FIN_ALPHABET_ASCII : "Lettre attendue de a à z ou un espacement.";
            assert c >= DEBUT_ALPHABET_ASCII : "Lettre attendue de a à z ou un espacement.";
            return (char) (DEBUT_ALPHABET_ASCII + ((c + cle - DEBUT_ALPHABET_ASCII) % TAILLE_ALPHABET));
        } else {
            return c;
        }
    }

    @Override
    public String chiffre(String texte, String cle) {
        String retour = "";
        for (Character c : texte.toCharArray()) {
            retour += chiffre(c, (int) cle.charAt(0));
        }
        return retour;

    }

    @Override
    public char dechiffre(char c, int cle) {
        if (c != ' ') {
            assert c <= FIN_ALPHABET_ASCII : "Lettre attendue de a à z ou un espacement.";
            assert c >= DEBUT_ALPHABET_ASCII : "Lettre attendue de a à z ou un espacement.";
            if (c - cle < DEBUT_ALPHABET_ASCII) {
                return (char) ((FIN_ALPHABET_ASCII + 1) - (DEBUT_ALPHABET_ASCII - c + cle));
            } else {
                return (char) (c - cle);
            }
        } else {
            return ' ';
        }
    }

    @Override
    public String dechiffre(String texte, String cle) {
        String retour = "";
        for (Character c : texte.toCharArray()) {
            retour += dechiffre(c, (int) cle.charAt(0));
        }
        return retour;
    }

    @Override
    public String decrypt(String texte) {
        String[] texteSplit = texte.split(" ");
        String retour = "";
        int compteur;
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            compteur = 0;
            for (String mot : texteSplit) {
                compteur++;
                String trad = dechiffre(mot, "" + i);
                /*if (Arbre.cherche(trad)){
                    retour += trad+" ";
                    if (compteur == texteSplit.length-1){
                        Toolkit.getDefaultToolkit().beep();
                        return retour;
                    }
                }else {
                    retour = "";
                    break;
                }*/
            }
        }
        return null;
    }
}

/**
 */