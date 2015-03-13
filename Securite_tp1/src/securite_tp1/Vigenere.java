public class Vigenere implements Code {

    private String cle;
    private int mod = 0;

    public Vigenere(String cle) {
        this.cle = cle;
    }


    @Override
    public String chiffre(String s, String cle) {
        String res="";
        for (int i = 0; i < s.length(); i++) {
            char c  = s.charAt(i);
            if (c == ' ') res += ' ';
            else if (c == '\n') res += '\n';
            else{
                res += chiffre(c, this.cle.charAt(mod)-97);
                mod++;
                mod = mod %this.cle.length();
            }
        }
        return res;
    }

    public String chiffreText(String text) {
        String tab[] = text.split("\n");
        String res = "";
        for (String s : tab) {
            //s = s.trim();
            res += this.chiffre(s, "") + "\n";
            System.out.println(s);

        }
        return res.substring(0, res.length() - 1);
    }

    @Override
    public String dechiffre(String s, String cle) {
        String res="";
        for (int i = 0; i < s.length(); i++) {
            char c  = s.charAt(i);
            if (c == ' ') res += ' ';
            else if (c == '\n') res += '\n';
            else{
                res += dechiffre(c, this.cle.charAt(mod)-97);
                mod++;
                mod = mod %this.cle.length();
            }
        }
        return res;
    }

    public String dechiffreText(String text) {
        String tab[] = text.split("\n");
        String res = "";
        for (String s : tab) {
            res += this.dechiffre(s, "") + "\n";
        }
        return res.substring(0, res.length() - 1);
    }

    @Override
    public String decrypt(String s) {
        return null;
    }
}
