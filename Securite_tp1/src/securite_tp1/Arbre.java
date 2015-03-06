import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.Normalizer;

public class Arbre /*implements Serializable */ {

    //private  static  final  long serialVersionUID =  1350092881346723535L;

    Noeud racine[] = new Noeud[26];


    public Arbre() {
        for (int i = 0; i < 26; i++) {
            racine[i] = new Noeud(false);
        }
    }

    /*public static Arbre ArbreFromSerialize(String fichier){
        Arbre arbre;
        try{
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            arbre = (Arbre) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException | ClassNotFoundException i) {
            i.printStackTrace();
            return null;
        }
        return arbre;
    }

    public String toString() {
        StringBuffer sb =  new StringBuffer() ;
        for (Noeud i : racine)
            sb.append(i);
        return sb.toString();
    }*/

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String fichier = "/media/data/git/ProjetL3/SECU/Renaud/TP1/Renaud_GuiGui_Cyril/Securite_tp1/lexique.txt";
        Arbre arbre = new Arbre();
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String t[] = ligne.split("-");
                for (String s : t) {
                    arbre.addMot(s);
                }
            }
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }


        long end = System.currentTimeMillis();

        long total = 0;
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            int i = 0;
            while ((ligne = br.readLine()) != null) {
                String t[] = ligne.split("-");
                for (String s : t) {
                    i++;
                    s = removeAccent(s);
                    s = s.toLowerCase();
                    long Cs = System.currentTimeMillis();
                    boolean a = arbre.chercheMot(s);
                    long Ce = System.currentTimeMillis();
                    total += ((Ce - Cs));
                    if (!a) System.out.println(s + " :" + a);
                }
            }
            br.close();
            System.out.println("total :"+new DecimalFormat("#.#######").format((double)total/(double)1000));
            System.out.println("moyenne :"+new DecimalFormat("#.#######").format((double)total/(double)i));
        } catch (Exception r) {
            System.out.println(r.toString());
        }

        System.err.println("time: " + ((end - start)) + " ms");


        boolean a = arbre.chercheMot("renaud");
        System.out.println("renaud" + " :" + a);
        a = arbre.chercheMot("theo");
        System.out.println("theo" + " :" + a);
        a = arbre.chercheMot("verre");
        System.out.println("verre" + " :" + a);
        a = arbre.chercheMot("livre");
        System.out.println("livre" + " :" + a);
        a = arbre.chercheMot("maison");
        System.out.println("maison" + " :" + a);

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
