import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Decrypt {

    public static void checkargs(String[] args) {
        if (args.length != 2 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : Decrypt [c/p/v] texte");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        checkargs(args);
        String text = "";
        try {
            InputStream ips = new FileInputStream("./text.txt");
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                text += ligne + "\n";
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
                decryptVigenere(args[1]);
                break;
            case "p":
                decryptPermutation(args[1]);
                break;
        }

    }

    public static void decryptCesar(String text) {
        System.out.println("Decrypt Cesar:");
        System.out.println("Text: " + text);
    }

    public static void decryptPermutation(String text) {
        System.out.println("Decrypt Permutation:");
        System.out.println("Text: " + text);
    }

    public static void decryptVigenere(String text) {
        System.out.println("Decrypt Vigenere:");
        //System.out.println("Text: " + text);
        Arbre arbre = new Arbre();


        ArrayList<String> list = new ArrayList<>();
        try {
            InputStream ips = new FileInputStream(text);
            InputStreamReader ipsr = new InputStreamReader(ips, "ISO8859_1");
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.equals("") || ligne.equals("\n")) continue;
                //System.out.println("AZERTYUIO:"+ligne+":AZERTYUIO");
                String t[] = ligne.split(" ");
                for (String s : t) {
                    list.add(s);
                }
            }
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }
        long start = System.currentTimeMillis();
        for (String s : list) {
            System.out.println(s);
            boolean a = arbre.chercheMot(s);
            if (!a) System.out.println(s + " :" + a);
        }
        long end = System.currentTimeMillis();
        System.err.println("Dechiffre time: " + ((end - start)) + " ms");
    }
}
