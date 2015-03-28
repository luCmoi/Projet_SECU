package securiteL3;

public interface Code {


    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int MARGE_D_ERREUR = 30; // On autorise 30% de mot not présent dans le lexique ou mal écrit
    static final int FIN_ALPHABET_ASCII = 122;
    static final int TAILLE_ALPHABET = 26;
    static final char[] FREQUENCE_FRANCAIS = {'e','a','s','i','t','n','r','u','l','o','d','c','p','m','v','q','f','b','g','h','j','x','y','z','w','k'};

    public default char chiffre(char c, int cle) {

        if (c == '\n' || c == ' ') return c;
        if (!in_alpha(c)){
            System.err.println("Erreur :"+c+"caractère interdit");
            System.exit(1);
        }
        if (c <= FIN_ALPHABET_ASCII || c >= DEBUT_ALPHABET_ASCII) {
            char d = (char) (c + cle);
            return (d > FIN_ALPHABET_ASCII) ? (char) (d - TAILLE_ALPHABET) : d;
        } else {
            System.err.print("Lettre attendue de a à z ou un espacement.");
            System.exit(0);
            return 0;
        }
    }

    public String chiffre(String s, String cle);

    public default char dechiffre(char c, int cle) {
        if (c == '\n' || c == ' ') return c;
        if (!in_alpha(c)){
            System.err.println("Erreur :"+c+"caractère interdit");
            System.exit(1);
        }
        char d = (char) (c - cle);
        return (d < DEBUT_ALPHABET_ASCII) ? (char) (d + TAILLE_ALPHABET) : d;
    }

    public String dechiffre(String s, String cle);

    public String decrypt(String... s);

    public default boolean in_alpha(char c) {
        return c >= DEBUT_ALPHABET_ASCII && c <= FIN_ALPHABET_ASCII;
    }
}
