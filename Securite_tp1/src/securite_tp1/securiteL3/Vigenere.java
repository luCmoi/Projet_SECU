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
    public String decrypt(String... args) {
        sans_taille(args[0]);
        return dechiffreText(args[0], this.cle);
    }

    public String decrypt(String s, String cle) {
        int taille = Integer.parseInt(cle);
        this.cle = avec_taille(s, taille);
        return dechiffreText(s, this.cle);
    }

    String avec_taille(String s, int taille) {
        int[][] tab_freq = new int[taille][TAILLE_ALPHABET];
        s = s.replaceAll("[ \n]", "");
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            tab_freq[i % taille][c - DEBUT_ALPHABET_ASCII]++;
        }
        int max;
        String key = "";
        for (int i = 0; i < taille; i++) {
            max = 0;
            for (int j = 0; j < TAILLE_ALPHABET; j++) {
                if (tab_freq[i][j] > tab_freq[i][max]) max = j;
            }
            key +=  (char) ((DEBUT_ALPHABET_ASCII + max - 'e') + 'a');
        }

        return key;

    }

    void sans_taille(String s){
        int taille = 1;
        String t2 = s.replaceAll("[ \n]", " ");
        t2 = t2.trim();
        String[] tab = t2.split(" ");
        Arbre arbre = new Arbre();
        boolean finish = false;
        while(!finish){
            this.cle = avec_taille(s, taille);
            finish = true;
            for (String mot : tab){
                if(mot.equals("") || mot.equals(" ")) continue;
                String a = dechiffre(mot,this.cle);
                if (!arbre.chercheMot(a)){
                    finish = false;
                    break;
                }
            }

            this.mod = 0;
            taille++;

        }
    }


}
