package securiteL3;

import java.io.*;

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
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];

        try {
            FileReader fr = new FileReader(args[1]);
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
            System.out.println(r.toString());
        }

        switch (args[0]) {
            case "c":
                decryptCesar(args, text.toString());
                end = System.currentTimeMillis();
                System.err.println("Temps de Cesar:" + (end - start) + "ms");
                break;
            case "v":
                decryptVigenere(text.toString(), args);
                end = System.currentTimeMillis();
                System.err.println("Temps de Vigenere:" + (end - start) + "ms");
                break;
            case "p":
                decryptPermutation(text.toString());
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
        System.out.println(new Permutation().decrypt(text));
    }

    public static void decryptVigenere(String text, String[] args) {
        if (args.length == 3) System.out.println(new Vigenere("").decrypt(text, args[2]));
        else System.out.println(new Vigenere("").decrypt(text));
    }
}
