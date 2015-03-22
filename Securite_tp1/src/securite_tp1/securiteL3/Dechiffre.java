package securiteL3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dechiffre {

    public static void checkargs(String[] args) {
        if (args.length != 3 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : securiteL3.Dechiffre [c/p/v] cle texte");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        checkargs(args);
        StringBuilder text = new StringBuilder();
        String string;
        try {
            InputStream ips = new FileInputStream(args[2]);
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
                dechiffreCesar(args[1], string);
                break;
            case "v":
                dechiffreVigenere(args[1], string);
                break;
            case "p":
                dechiffrePermutation(args[1], string);
                break;
        }

    }

    public static void dechiffreCesar(String cle, String text) {
        long start = System.currentTimeMillis();
        System.out.println(new Cesar().dechiffreText(text, cle));
        long end = System.currentTimeMillis();
        //System.err.println((end - start));
    }

    public static void dechiffrePermutation(String cle, String text) {
        System.out.println(new Permutation().dechiffreText(text, cle));
    }

    public static void dechiffreVigenere(String cle, String text) {
        System.out.println((new Vigenere(cle)).dechiffreText(text, cle));

    }
}
