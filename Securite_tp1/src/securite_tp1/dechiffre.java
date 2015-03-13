import java.io.*;

public class Dechiffre {

    public static void checkargs(String[] args) {
        if (args.length != 3 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : Dechiffre [c/p/v] cle texte");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        checkargs(args);
        String text = "";
        try {
            InputStream ips = new FileInputStream(args[2]);
            InputStreamReader ipsr = new InputStreamReader(ips);
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
                dechiffreCesar(args[1], text);
                break;
            case "v":
                dechiffreVigenere(args[1], text);
                break;
            case "p":
                dechiffrePermutation(args[1], text);
                break;
        }

    }

    public static void dechiffreCesar(String cle, String text) {
        System.out.println("Dechiffre Cesar:");
        System.out.println("Cle: " + cle);
        System.out.println(new Cesar().dechiffreText(text, cle));
    }

    public static void dechiffrePermutation(String cle, String text) {
        System.out.println("Dechiffre Permutation:");
        System.out.println("Cle: " + cle);
        System.out.println(new Permutation().dechiffreText(text, cle));
    }

    public static void dechiffreVigenere(String cle, String text) {
        System.out.println("Dechiffre Vigenere:");
        System.out.println("Cle: " + cle);
        System.out.println((new Vigenere(cle)).dechiffreText(text, cle));

    }
}
