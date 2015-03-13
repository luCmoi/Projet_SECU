import java.io.*;

public class Chiffre {

    public static void checkargs(String[] args) {
        if (args.length != 3 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : Chiffre [c/p/v] cle texte");
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
                chiffreCesar(args[1], text);
                break;
            case "v":
                chiffreVigenere(args[1], text);
                break;
            case "p":
                chiffrePermutation(args[1], text);
                break;
        }
    }

    public static void chiffreCesar(String cle, String text) {
        System.out.println("Chiffre Cesar:");
        System.out.println("Cle: " + cle);
        System.out.println(new Cesar().chiffreText(text, cle));
    }


    public static void chiffrePermutation(String cle, String text) {
        System.out.println("Chiffre Permutation:");
        System.out.println("Cle: " + cle);
        System.out.println(new Permutation().chiffreText(text, cle));
    }

    public static void chiffreVigenere(String cle, String text) {
        System.out.println("Chiffre Vigenere:");
        System.out.println("Cle: " + cle);
        System.out.println((new Vigenere(cle)).chiffreText(text, cle));
    }
}
