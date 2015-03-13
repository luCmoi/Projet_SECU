public class Permutation implements Code {


    
    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int FIN_ALPHABET_ASCII = 122;
    static final int TAILLE_ALPHABET = 26;
    
    @Override
    public static char chiffre(char c, int cle) {
        if(c == '\n')return c;
        if (c != ' ') {
            if (c <=FIN_ALPHABET_ASCII || c >= DEBUT_ALPHABET_ASCII) {
                char d = (char) (c + cle);
                return (d > FIN_ALPHABET_ASCII) ? (char) (d - TAILLE_ALPHABET) : d;
            }else {
                System.err.print("Lettre attendue de a à z ou un espacement.");
                System.exit(0);
            }
        }
        return ' ';
    }
    
    public static boolean verif(String cle){
        boolean[] tab = new boolean[26];
        for(int i = 0;i < cle.length();i++){
            if(cle.charAt(i) < 97 || cle.charAt(i) > 122){
                return false;
            }
            tab[cle.charAt(i)-97] = true;
        }
        for(int i = 0;i < 26;i++){
            if(!tab[i]) return false;
        }
        return true;
    }

    @Override
    public static String chiffre(String s, String cle) {
        char[] tab = new char[26];
        String result = "";
        if(cle.length() == 26 && verif(cle)){
            for(int i = 0;i < s.length();i++){
                if(s.charAt(i) == ' '){
                    result+=' ';
                }
                else if(s.charAt(i) == '\n'){
                    result+='\n';
                }
                else{
                    if((int)s.charAt(i) < 97 || (int)s.charAt(i) > 122){
                        System.err.println("Votre texte n'est pas bien ecrit");
                        System.exit(1);
                    }
                    int a = (cle.charAt(s.charAt(i)-97)-s.charAt(i));
                    result+= chiffre(s.charAt(i),a);
                }
            }
            System.out.println("resultat : " + result);
        }
        else{
            System.err.println("Votre clé n'est pas bonne");
            System.exit(1);
        }
        return result;
    }

    @Override
    public static char dechiffre(char c, int cle) {
        if(c == '\n')return c;
        if (c != ' ') {
            char d = (char) (c - cle);
            return (d < DEBUT_ALPHABET_ASCII) ? (char) (d + TAILLE_ALPHABET) : d;
        }
        return ' ';
    }

    @Override
    public static String dechiffre(String s, String cle) {
        int[] tab = new int[26];
        boolean espace = true;
        String result = "";
        if(cle.length() == 26 && verif(cle)){
            for(int i = 0;i < s.length();i++){
                if(s.charAt(i) == ' '){
                    result+=' ';
                }
                else if(s.charAt(i) == '\n'){
                    result+='\n';
                }
                else{
                    if((int)s.charAt(i) < 97 || (int)s.charAt(i) > 122){
                        System.err.println("Votre texte n'est pas bien encoder");
                        System.exit(1);
                    }
                    int a = (s.charAt(i)-cle.charAt(s.charAt(i)-97));
                    result+= dechiffre(s.charAt(i),a);
                }
            }
        }
        else{
            System.err.println("Votre clé n'est pas bonne");
            System.exit(1);
        }
        System.out.println("resultat : " + result);
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
        for(int i = 0;i < s.length();i++){
            switch (s.charAt(i)) {
                case 'a':  tab[0] += 1;
                    break;
                case 'b':  tab[1] += 1;
                    break;
                case 'c':  tab[2] += 1;
                    break;
                case 'd':  tab[3] += 1;
                    break;
                case 'e':   tab[4] += 1;
                    break;
                case 'f':   tab[5] += 1;
                    break;
                case 'g':   tab[6] += 1;
                    break;
                case 'h':   tab[7] += 1;
                    break;
                case 'i':   tab[8] += 1;
                    break;
                case 'j':   tab[9] += 1;
                    break;
                case 'k':   tab[10] += 1;
                    break;
                case 'l':   tab[11] += 1;
                    break;
                case 'm':   tab[12] += 1;
                    break;
                case 'n':   tab[13] += 1;
                    break;
                case 'o':   tab[14] += 1;
                    break;
                case 'p':   tab[15] += 1;
                    break;
                case 'q':   tab[16] += 1;
                    break;
                case 'r':   tab[17] += 1;
                    break;
                case 's':   tab[18] += 1;
                    break;
                case 't':   tab[19] += 1;
                    break;
                case 'u':   tab[20] += 1;
                    break;
                case 'v':   tab[21] += 1;
                    break;
                case 'w':   tab[22] += 1;
                    break;
                case 'x':   tab[23] += 1;
                    break;
                case 'y':   tab[24] += 1;
                    break;
                case 'z':   tab[25] += 1;
                    break;
                default:
                    break;
            }
        }
        triBulleDecroissant(tab,tab2);
        for(int i = 0;i < tab.length; i++){
            System.out.println("case : " + tab[i] + " ; " + tab2[i]);
        }
        return null;
    }




    public static void triBulleDecroissant(int tableau[],char tableau2[]) {
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
                    tableau2[i+1] = tampon2;
                    permut = true;
                }
            }
        } while (permut);
    }
}
