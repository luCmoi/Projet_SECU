package securiteL3;

import java.io.*;

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
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];

        try {
            FileReader fr = new FileReader(args[2]);
            BufferedReader br = new BufferedReader(fr);
            while (true) {

                read = br.read(buffer, 0, N);
                text.append(buffer, 0, read);

                if (read < N) {
                    break;
                }

            }
            br.close();
        } catch (Exception r) {
            r.printStackTrace();
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
        System.out.println(new Cesar().dechiffre(text, cle));
    }

    public static void dechiffrePermutation(String cle, String text) {
        System.out.println(new Permutation().dechiffreText(text, cle));
    }

    public static void dechiffreVigenere(String cle, String text) {
        System.out.print((new Vigenere(cle)).dechiffre(text, cle));

    }
}
