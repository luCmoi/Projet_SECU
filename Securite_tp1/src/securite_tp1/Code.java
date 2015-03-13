public interface Code {


    static final int DEBUT_ALPHABET_ASCII = 97;
    static final int FIN_ALPHABET_ASCII = 122;
    static final int TAILLE_ALPHABET = 26;

    public default char chiffre(char c, int cle) {
        if (c == '\n' || c == ' ') return c;
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
        char d = (char) (c - cle);
        return (d < DEBUT_ALPHABET_ASCII) ? (char) (d + TAILLE_ALPHABET) : d;
    }


    public default String dechiffreText(String text, String cle) {
        String tab[] = text.split("\n");
        String res = "";
        for (String s : tab) {
            res += this.dechiffre(s, cle) + "\n";
        }
        return res.substring(0, res.length() - 1);
    }

    public default String chiffreText(String text, String cle) {
        String tab[] = text.split("\n");
        String res = "";
        for (String s : tab) {
            res += this.chiffre(s, cle) + "\n";
        }
        return res.substring(0, res.length() - 1);
    }

    public String dechiffre(String s, String cle);

    public String decrypt(String s);

}
