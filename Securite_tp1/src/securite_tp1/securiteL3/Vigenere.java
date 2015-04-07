package securiteL3;

import java.util.StringTokenizer;

public class Vigenere implements Code {

    private String cle;
    private int mod = 0;
    private int taille_cle;

    public Vigenere(String cle) {
        this.cle = cle;
        this.taille_cle = cle.length();
    }


    @Override
    public String chiffre(String s, String cle) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') res.append(' ');
            else if (c == '\n') res.append('\n');
            else {
                res.append(chiffre(c, this.cle.charAt(mod++) - 97));
                mod %= this.cle.length();
            }
        }
        return res.toString();
    }

    @Override
    public String dechiffre(String s, String cle) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') res.append(' ');
            else if (c == '\n') res.append('\n');
            else {
                res.append(dechiffre(c, this.cle.charAt(mod++) - 97));
                mod %= this.cle.length();
            }
        }
        return res.toString();
    }

    @Override
    public String decrypt(String... args) {
        String[] text;
        StringTokenizer st = new StringTokenizer(args[0].trim(), " \n");
        text = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            text[i] = st.nextToken();
            i++;
        }
        sans_taille(args[0], text);
        System.err.println(this.cle);
        return dechiffre(args[0], this.cle);
    }

    public String decrypt(String s, String cle) {
        int taille = Integer.parseInt(cle);
        this.cle = avec_taille(s, taille);
        System.err.println(this.cle);
        return dechiffre(s, this.cle);
    }



    String avec_taille(String s, int taille) {
        int max;
        StringBuilder key = new StringBuilder();
        int[][] tab_freq = new int[taille][TAILLE_ALPHABET];
        int i = 0;
        for (Character c : s.toCharArray()) {
            if (c == ' ' || c == '\n') {
                continue;
            }
            if (!in_alpha(c)) {
                System.err.println("Erreur :" + c + "caractère non autorisé");
                System.exit(1);
            }
            tab_freq[i % taille][c - DEBUT_ALPHABET_ASCII]++;
            i++;
        }
        for (i = 0; i < taille; i++) {
            max = 0;
            for (int j = 0; j < TAILLE_ALPHABET; j++) {
                if (tab_freq[i][j] > tab_freq[i][max]) max = j;
            }
            int c = (char) ((DEBUT_ALPHABET_ASCII + max - FREQUENCE_FRANCAIS[0]) + DEBUT_ALPHABET_ASCII);
            if (c < DEBUT_ALPHABET_ASCII) c += TAILLE_ALPHABET;
            key.append((char) c);
        }
        return key.toString();

    }

    void sans_taille(String s, String[] tab) {
        this.taille_cle = 1;
        int marge = 0;
        int length = tab.length;
        Arbre arbre = new Arbre();
        boolean finish = false;
        while (!finish) {
            this.cle = avec_taille(s, this.taille_cle);
            finish = true;
            for (String mot : tab) {
                if (mot.equals("") || mot.equals(" ")) continue;
                String a = dechiffre(mot, this.cle);
                if (!arbre.chercheMot(a)){
                    marge++;
                    //System.err.println("ratio :"+(length / marge));
                    if((length / marge) >= MARGE_D_ERREUR) {
                        //marge=0;
                        finish = false;
                        break;
                    }
                }
                if (this.taille_cle >= s.length()/20){
                    System.out.println("Erreur Taille de la clé trop grande");
                    System.exit(1);
                }
            }
            //System.err.println(marge);
            this.mod = 0;
            marge = 0;
            this.taille_cle++;
        }

    }


}
