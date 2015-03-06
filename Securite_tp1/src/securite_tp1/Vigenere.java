public class Vigenere implements Code {

    private String cle;

    public Vigenere(String cle) {
        this.cle = cle;
    }


    @Override
    public String chiffre(String s, String cle) {
        String mot = "";
        for (int i = 0; i < s.length(); i++) {
            mot += chiffre(s.charAt(i), ((this.cle.charAt(i % this.cle.length())) % DEBUT_ALPHABET_ASCII));
        }
        return mot;
    }

    public String chiffreText(String text) {
        String tab[] = text.split(" ");
        String res = "";
        for (String s : tab) {
            s = s.trim();
            res += this.chiffre(s, "") + " ";
        }
        return res.substring(0, res.length() - 1);
    }

    @Override
    public String dechiffre(String s, String cle) {
        String mot = "";
        for (int i = 0; i < s.length(); i++) {
            mot += dechiffre(s.charAt(i), (int) (this.cle.charAt(i % this.cle.length())) % DEBUT_ALPHABET_ASCII);
        }
        return mot;
    }

    public String dechiffreText(String text) {
        String tab[] = text.split(" ");
        String res = "";
        for (String s : tab) {
            s = s.trim();
            res += this.dechiffre(s, "") + " ";
        }
        return res.substring(0, res.length() - 1);
    }

    @Override
    public String decrypt(String s) {
        return null;
    }
}
