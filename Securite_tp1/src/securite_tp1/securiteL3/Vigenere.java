package securiteL3;

import java.util.StringTokenizer;

public class Vigenere implements Code {

    private String cle;
    private int mod = 0;

    public Vigenere(String cle) {
        this.cle = cle;
    }


    @Override
    public String chiffre(String s, String cle) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') res.append(' ');
            else if (c == '\n') res.append('\n');
            else {
                res.append(chiffre(c, this.cle.charAt(mod) - 97));
                mod++;
                mod = mod % this.cle.length();
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
                res.append(dechiffre(c, this.cle.charAt(mod) - 97));
                mod++;
                mod = mod % this.cle.length();
            }
        }
        return res.toString();
    }

    @Override
    public String decrypt(String... args) {
        String[] text;
        StringTokenizer st = new StringTokenizer(args[0].trim()," \n");
        text = new String[st.countTokens()];
        int i = 0;
        while(st.hasMoreTokens()) {
            text[i] = st.nextToken();
            i++;
        }
        sans_taille(args[0], text);
        return dechiffreText(args[0], this.cle);
    }

    public String decrypt(String s, String cle) {
        int taille = Integer.parseInt(cle);
        this.cle = avec_taille(s, taille);
        return dechiffreText(s, this.cle);
    }
    public boolean in_alpha(char c){
        return c>=DEBUT_ALPHABET_ASCII && c<=FIN_ALPHABET_ASCII;
    }
    String avec_taille(String s, int taille) {
        int max;
        StringBuilder key = new StringBuilder();
        int[][] tab_freq = new int[taille][TAILLE_ALPHABET];
        int i = 0;
        for (Character c : s.toCharArray()){
            if (c == ' ' || c == '\n'){
                continue;
            }
            if(!in_alpha(c)){
                System.out.println("Erreur :"+c+"caractère non autorisé");
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
            int c = (char) ((DEBUT_ALPHABET_ASCII +max - 'e') + 'a');
            if (c<DEBUT_ALPHABET_ASCII) c += TAILLE_ALPHABET;
            key.append((char) c);
        }
        return key.toString();

    }

    void sans_taille(String s, String[] tab) {
        int taille = 1;
        Arbre arbre = new Arbre();
        boolean finish = false;
        while (!finish) {
            this.cle = avec_taille(s, taille);
            finish = true;
            for (String mot : tab){
                if (mot.equals("") || mot.equals(" ")) continue;
                String a = dechiffre(mot, this.cle);
                if (!arbre.chercheMot(a)) {
                    finish = false;
                    break;
                }
            }
            this.mod = 0;
            taille++;

        }
    }


}
