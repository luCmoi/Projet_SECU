package securiteL3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;

public class Decrypt {

    public static void checkargs(String[] args) {
        if (args.length < 2 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : securiteL3.Decrypt [c/p/v] texte");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        long end;
        checkargs(args);
        StringBuilder text = new StringBuilder();
        String string;
        try {
            InputStream ips = new FileInputStream(args[1]);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.equals("\n")) {
                    text.append(ligne);
                } else {
                    text.append(ligne);
                    text.append("\n");
                }
            }
            br.close();
        } catch (Exception r) {
            System.out.println(r.toString());
        }
        string = text.toString();
        switch (args[0]) {
            case "c":
                decryptCesar(args, string);
                end = System.currentTimeMillis();
                System.err.println("Temps de Cesar:" + (end - start) + "ms");
                break;
            case "v":
                decryptVigenere(string, args);
                end = System.currentTimeMillis();
                System.err.println("Temps de Vigenere:" + (end - start) + "ms");
                break;
            case "p":
                decryptPermutation(args[1]);
                end = System.currentTimeMillis();
                System.err.println("Temps de Permutation:" + (end - start) + "ms");
                break;
        }

    }

    public static void decryptCesar(String[] args, String text) {
        if (args.length == 3) System.out.println(new Cesar().decrypt(text, args[2]));
        else System.out.println(new Cesar().decrypt(text, args[2], args[3]));


    }

    public static void decryptPermutation(String text) {
        System.out.println("securiteL3.Decrypt securiteL3.Permutation:");
        System.out.println("Text: " + text);
    }

    public static void decryptVigenere(String text, String[] args) {
        if (args.length == 3) System.out.println(new Vigenere("").decrypt(text, args[2]));
        else System.out.println(new Vigenere("").decrypt(text));
    }
}
