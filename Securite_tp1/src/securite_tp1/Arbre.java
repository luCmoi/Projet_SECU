import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Arbre {

    Noeud racine[] = new Noeud[26];


    public Arbre() {
        for (int i = 0; i < 26; i++) {
            racine[i] = new Noeud(false);
        }
        String fichier = "./Securite_tp1/lexique.txt";
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String t[] = ligne.split("-");
                for (String s : t) {
                    addMot(s);
                }
            }
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }
    }

    Arbre(int a){
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
                int i = ligne.charAt(0)-97;
                this.racine[i].addChar(ligne, 1);

            }
            br.close();
        } catch (Exception r) {
            r.printStackTrace();
        }
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

    void tradArbre(){
        try {
            String fichier = "./lexiqueA.txt";
            OutputStream ips = new FileOutputStream(fichier);
            OutputStreamWriter ipsr = new OutputStreamWriter(ips, "ISO8859_1");
            PrintWriter br = new PrintWriter(ipsr);
            for (int i = 0; i < 26; i++) {
                tradNoeud(this.racine[i], i, br);
                br.println();
            }
            br.println();
            br.close();
        }catch(Exception e){

        }
    }

    void tradNoeud(Noeud n, int i, PrintWriter br){
        if(n!=null){
            br.print((char) (97 + i));
            if (n.peut_finir){
                br.print("!");
            }
            if(n.listeNoeud != null){
                for(int ii = 0; ii<26; ii++){
                    tradNoeud(n.listeNoeud[ii],ii,br);
                }}
            br.print(".");
        }
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

    Set<String> listeMots(String pattern){
        char[] mot=new char[pattern.length()];
        for (int i = 0; i<mot.length; i++){
            mot[i]=pattern.charAt(i);
        }
        return listeMots_rec(mot, "", 0, new HashSet<>());
    }

    private Set<String> listeMots_rec(char[] pattern, String mot, int profondeur, Set<String> liste){
        if (pattern[profondeur] == '.'){
            for (int i = 0; i< 26;i++){
                liste.addAll(racine[i].getMot(pattern, mot + (char) ('a' + i), profondeur + 1, liste));
            }
        }
        else{
            liste.addAll(racine[pattern[profondeur]-97].getMot(pattern,mot+pattern[profondeur], profondeur+1, liste));
        }
        return liste;
    }

    public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public static void main(String[] args) {
        String fichier = "./lexique.txt";
        long start;
        Arbre arbre;
        long end;

        start = System.currentTimeMillis();
        arbre = new Arbre(0);
        end = System.currentTimeMillis();
        System.err.println("Creation ArbreNEW time: " + ((end - start)) + " ms");

        ArrayList<String> text = new ArrayList<>();

        start = System.currentTimeMillis();
        arbre = new Arbre();
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String t[] = ligne.split("-");
                for (String s : t) {
                    text.add(removeAccent(s).toLowerCase());
                    arbre.addMot(s);
                }
            }
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }
        end = System.currentTimeMillis();
        System.err.println("Creation ArbreOLD time: " + ((end - start)) + " ms");

        try {
            String fichier2 = "./text.txt";
            OutputStream ips = new FileOutputStream(fichier2);
            OutputStreamWriter ipsr = new OutputStreamWriter(ips);
            PrintWriter br = new PrintWriter(ipsr);
            Random r = new Random(System.currentTimeMillis());
            for (int i = 0 ; i< 10; i++){
                for (int j = 0 ; j< 1000; j++){
                    br.print(text.get(r.nextInt(text.size()))+" ");
                }
                br.println();
            }
            br.flush();
            br.println();
            br.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }


        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            start = System.currentTimeMillis();
            while ((ligne = br.readLine()) != null) {
                String t[] = ligne.split("-");
                for (String s : t) {
                    s = removeAccent(s);
                    s = s.toLowerCase();
                    boolean a = arbre.chercheMot(s);
                    if (!a) System.out.println(s + " :" + a);
                }
            }
            end = System.currentTimeMillis();
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }
        System.err.println("check lexique time: " + ((end - start)) + " ms");





        boolean a;
        String mot = "....g...";
        Set<String> list;
        start = System.currentTimeMillis();
        list = arbre.listeMots(mot);
        end = System.currentTimeMillis();
        for (String s : list) {
            a = arbre.chercheMot(s);
            //if (a) System.out.println(s + " :" + a);
        }
        System.err.println("time recherche "+mot+": " + ((end - start)) + " ms");
        System.out.println("taille liste :" + list.size() + " taille mot :" + mot.length());

        //arbre.tradArbre();

    }



}
