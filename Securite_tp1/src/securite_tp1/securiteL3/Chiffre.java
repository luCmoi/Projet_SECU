package securiteL3;

import java.io.*;

public class Chiffre {

    public static void checkargs(String[] args) {
        if (args.length != 3 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : securiteL3.Chiffre [c/p/v] cle texte");
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
                chiffreCesar(args[1], string);
                break;
            case "v":
                chiffreVigenere(args[1], string);
                break;
            case "p":
                chiffrePermutation(args[1], string);
                break;
        }
    }

    public static void chiffreCesar(String cle, String text) {
        System.out.print(new Cesar().chiffre(text, cle));
    }


    public static void chiffrePermutation(String cle, String text) {
        System.out.println(new Permutation().chiffreText(text, cle));
    }

    public static void chiffreVigenere(String cle, String text) {
        System.out.print((new Vigenere(cle)).chiffre(text, cle));
    }
}
