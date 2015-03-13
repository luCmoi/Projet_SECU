import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;

public class Arbre {

    Noeud racine[] = new Noeud[26];

    Arbre() {
        for (int i = 0; i < 26; i++) {
            racine[i] = new Noeud(false);
        }
        String fichier = "./lexiqueA.txt";
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                int i = ligne.charAt(0) - 97;
                this.racine[i].addChar(ligne, 1);

            }
            br.close();
        } catch (Exception r) {
            r.printStackTrace();
        }
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    void addMot(String s) {
        s = removeAccent(s);
        s = s.toLowerCase();
        int indice = (s.charAt(0) - 97);
        Noeud tmp = racine[indice];
        for (int i = 1; i < s.length(); i++) {
            tmp = tmp.addFils(s.charAt(i), false);
        }
        tmp.setPeut_finir(true);
    }

    boolean chercheMot(String s) {
        byte indice = (byte) (s.charAt(0) - 97);
        Noeud tmp = racine[indice];
        for (int i = 1; i < s.length(); i++) {
            tmp = tmp.getFils(s.charAt(i));
            if (tmp == null) return false;
        }
        return tmp.peut_finir;
    }

}
