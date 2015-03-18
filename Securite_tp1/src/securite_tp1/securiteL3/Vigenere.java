package securiteL3;

public class Vigenere implements Code {

    private String cle;
    private int mod = 0;

    public Vigenere(String cle) {
        this.cle = cle;
    }


    @Override
    public String chiffre(String s, String cle) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') res += ' ';
            else if (c == '\n') res += '\n';
            else {
                res += chiffre(c, this.cle.charAt(mod) - 97);
                mod++;
                mod = mod % this.cle.length();
            }
        }
        return res;
    }

    @Override
    public String dechiffre(String s, String cle) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') res += ' ';
            else if (c == '\n') res += '\n';
            else {
                res += dechiffre(c, this.cle.charAt(mod) - 97);
                mod++;
                mod = mod % this.cle.length();
            }
        }
        return res;
    }

    @Override
    public String decrypt(String s) {
        /*String[] tab = split_text(s, 6);
        this.cle = get_full_key(tab, 6);

        return dechiffreText(s, this.cle);*/
        return null;
    }

    public String decrypt(String s, String cle) {
        int taille = Integer.parseInt(cle);
        String[] tab = split(s, taille);
        this.cle = get_full_key(tab);
        return dechiffreText(s, this.cle);
    }

    String[] split(String s, int taille){
        String[] tab = new String[taille];
        s =s.replaceAll("[ \n]", "");
        for (int i = 0; i < s.length(); i++) {
            tab[i%taille] += s.charAt(i);
        }
        return tab;
    }

    char get_freq(String s){
        int [] tab = new int[TAILLE_ALPHABET];
        for (int i = 0; i < s.length(); i++) {
            tab[s.charAt(i) - DEBUT_ALPHABET_ASCII]++;
        }
        int max = 0;
        for (int i = 0; i < TAILLE_ALPHABET; i++) {
            if(tab[i]>tab[max]) max = i;
        }
        return (char) ((DEBUT_ALPHABET_ASCII+max-'e')+'a');
    }

    String get_full_key(String[] tab){
        String key = "";
        for (String i : tab) {
            key+= get_freq(i);
        }
        return key;
    }



}
