public interface Code {


    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int FIN_ALPHABET_ASCII = 122;
    static final int TAILLE_ALPHABET = 26;

    public default char chiffre(char c, int cle) {
        char d = (char) (c + cle);
        return (d > FIN_ALPHABET_ASCII) ? (char) (d - TAILLE_ALPHABET) : d;
    }

    public String chiffre(String s, String cle);

    public default char dechiffre(char c, int cle) {
        char d = (char) (c - cle);
        return (d < DEBUT_ALPHABET_ASCII) ? (char) (d + TAILLE_ALPHABET) : d;
    }

    public String dechiffre(String s, String cle);

    public String decrypt(String s);

}
