package securiteL3;

public class Permutation implements Code {

    public static boolean verif(String cle) {
        boolean[] tab = new boolean[TAILLE_ALPHABET];
        for (int i = 0; i < cle.length(); i++) {
            if (cle.charAt(i) < DEBUT_ALPHABET_ASCII || cle.charAt(i) > FIN_ALPHABET_ASCII) {
                return false;
            }
            tab[cle.charAt(i) - DEBUT_ALPHABET_ASCII] = true;
        }
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            if (!tab[i]) return false;
        }
        return true;
    }

    public static void triBulleDecroissant(int tableau[], char tableau2[]) {
        int longueur = tableau.length;
        int tampon = 0;
        char tampon2 = ' ';
        boolean permut;

        do {
            // hypothèse : le tableau est trié
            permut = false;
            for (int i = 0; i < longueur - 1; i++) {
                // Teste si 2 éléments successifs sont dans le bon ordre ou non
                if (tableau[i] < tableau[i + 1]) {
                    // s'ils ne le sont pas, on échange leurs positions
                    tampon = tableau[i];
                    tampon2 = tableau2[i];
                    tableau[i] = tableau[i + 1];
                    tableau2[i] = tableau2[i + 1];
                    tableau[i + 1] = tampon;
                    tableau2[i + 1] = tampon2;
                    permut = true;
                }
            }
        } while (permut);
    }

    @Override
    public String chiffre(String s, String cle) {
        String result = "";
        if (cle.length() == TAILLE_ALPHABET && verif(cle)) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\n' || c == ' ') result += c;
                else if (c <= FIN_ALPHABET_ASCII || c >= DEBUT_ALPHABET_ASCII) {
                    int cle_char = (cle.charAt(c - DEBUT_ALPHABET_ASCII) - c);
                    //System.err.println(cle_char+" "+c+" "+cle.charAt(c-97));
                    result += chiffre(c, cle_char);
                } else {
                    System.err.print("Lettre attendue de a à z ou un espacement.");
                    System.exit(0);
                    return "";
                }
            }
        } else {
            System.err.println("Votre clé n'est pas bonne");
            System.exit(1);
        }
        return result;
    }

    @Override
    public String dechiffre(String s, String cle) {
        String result = "";
        //System.err.println(cle);


        if (cle.length() == TAILLE_ALPHABET && verif(cle)) {
            char[] tab = new char[TAILLE_ALPHABET];
            for(int i= 0; i< cle.length();i++){
                int a = cle.charAt(i)-DEBUT_ALPHABET_ASCII;
                tab[a] = (char)('a'+i);
            }
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '\n' || c == ' ') result += c;
                else {
                    result += dechiffre(c, c - tab[c-DEBUT_ALPHABET_ASCII]);
                }
            }
        } else {
            System.err.println("Votre clé n'est pas bonne");
            System.exit(1);
        }
        return result;
    }

    @Override
    public String decrypt(String s) {
        int[] tab = new int[26];
        char[] tab2 = new char[26];
        tab2[0] = 'a';
        tab2[1] = 'b';
        tab2[2] = 'c';
        tab2[3] = 'd';
        tab2[4] = 'e';
        tab2[5] = 'f';
        tab2[6] = 'g';
        tab2[7] = 'h';
        tab2[8] = 'i';
        tab2[9] = 'j';
        tab2[10] = 'k';
        tab2[11] = 'l';
        tab2[12] = 'm';
        tab2[13] = 'n';
        tab2[14] = 'o';
        tab2[15] = 'p';
        tab2[16] = 'q';
        tab2[17] = 'r';
        tab2[18] = 's';
        tab2[19] = 't';
        tab2[20] = 'u';
        tab2[21] = 'v';
        tab2[22] = 'w';
        tab2[23] = 'x';
        tab2[24] = 'y';
        tab2[25] = 'z';
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'a':
                    tab[0] += 1;
                    break;
                case 'b':
                    tab[1] += 1;
                    break;
                case 'c':
                    tab[2] += 1;
                    break;
                case 'd':
                    tab[3] += 1;
                    break;
                case 'e':
                    tab[4] += 1;
                    break;
                case 'f':
                    tab[5] += 1;
                    break;
                case 'g':
                    tab[6] += 1;
                    break;
                case 'h':
                    tab[7] += 1;
                    break;
                case 'i':
                    tab[8] += 1;
                    break;
                case 'j':
                    tab[9] += 1;
                    break;
                case 'k':
                    tab[10] += 1;
                    break;
                case 'l':
                    tab[11] += 1;
                    break;
                case 'm':
                    tab[12] += 1;
                    break;
                case 'n':
                    tab[13] += 1;
                    break;
                case 'o':
                    tab[14] += 1;
                    break;
                case 'p':
                    tab[15] += 1;
                    break;
                case 'q':
                    tab[16] += 1;
                    break;
                case 'r':
                    tab[17] += 1;
                    break;
                case 's':
                    tab[18] += 1;
                    break;
                case 't':
                    tab[19] += 1;
                    break;
                case 'u':
                    tab[20] += 1;
                    break;
                case 'v':
                    tab[21] += 1;
                    break;
                case 'w':
                    tab[22] += 1;
                    break;
                case 'x':
                    tab[23] += 1;
                    break;
                case 'y':
                    tab[24] += 1;
                    break;
                case 'z':
                    tab[25] += 1;
                    break;
                default:
                    break;
            }
        }
        triBulleDecroissant(tab, tab2);
        for (int i = 0; i < tab.length; i++) {
            System.out.println("case : " + tab[i] + " ; " + tab2[i]);
        }
        return null;
    }
}
