package securiteL3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;

public class Decrypt {

    public static void checkargs(String[] args) {
        if (args.length < 3 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : securiteL3.Decrypt [c/p/v] texte");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        checkargs(args);
        String text = "";
        try {
            InputStream ips = new FileInputStream(args[1]);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.equals("\n")) {
                    text += ligne;
                } else {
                    text += ligne + "\n";
                }
            }
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }
        switch (args[0]) {
            case "c":
                decryptCesar(args[1]);
                break;
            case "v":
                decryptVigenere(text,args);
                break;
            case "p":
                decryptPermutation(args[1]);
                break;
        }

    }

    public static void decryptCesar(String text) {
        System.out.println("securiteL3.Decrypt securiteL3.Cesar:");
        System.out.println("Text: " + text);
    }

    public static void decryptPermutation(String text) {
        System.out.println("securiteL3.Decrypt securiteL3.Permutation:");
        System.out.println("Text: " + text);
    }

    public static void decryptVigenere(String text, String[] args) {
        long start = System.currentTimeMillis();
        if(args.length == 3) System.out.println(new Vigenere("").decrypt(text, args[2]));
        else System.out.println(new Vigenere("").decrypt(text));
        long end = System.currentTimeMillis();
        System.err.println("Temps de Vigenere:"+(end-start)+"ms");






        /*ArrayList<String> list = new ArrayList<>();
        System.err.println("\n\nCréation Arbre");
        start = System.currentTimeMillis();
        Arbre arbre = new Arbre();
        end = System.currentTimeMillis();
        System.err.println("Création Arbre: " + ((end - start)) + " ms");
        String fichier = "/media/data/git/Projet_SECU/Securite_tp1/src/securite_tp1/lexique.txt";
        try {
            InputStream ips = new FileInputStream(fichier);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String t[] = ligne.split("-");
                for (String s : t) {
                    s=removeAccent(s);
                    s = s.toLowerCase();
                    list.add(s);

                }
            }
            br.close();
        } catch (Exception r) {
            System.err.println(r.toString());
        }

        System.err.println("\n\nRecherch de "+ list.size()+" mots");
        start = System.currentTimeMillis();
        for (String s : list) {
            boolean a = arbre.chercheMot(s);
            if (!a) System.err.println(s + " :" + a);
        }
        end = System.currentTimeMillis();
        System.err.println("Temps recherche: " + ((end - start)) + " ms");
        */



    }

    /*public static String removeAccent(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }*/
}
