package securiteL3;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        int read, N = 1024 * 1024;
        char[] buffer = new char[N];

        try {
            FileReader fr = new FileReader(args[1]);
            BufferedReader br = new BufferedReader(fr);
            while(true) {

                read = br.read(buffer, 0, N);
                text.append(buffer, 0, read);

                if(read < N) {
                    break;
                }

            }
            br.close();
            //string = new String(Files.readAllBytes(Paths.get(args[1])), StandardCharsets.UTF_8);


            /*FileInputStream f = new FileInputStream(args[1]);
            FileChannel ch = f.getChannel( );
            MappedByteBuffer mbb = ch.map( FileChannel.MapMode.READ_ONLY, 0L, ch.size( ) );
            while ( mbb.hasRemaining( ) )  {
                String charsetName = "UTF-8"; // choose the apropriate charset.
                CharBuffer cb =  Charset.forName(charsetName).decode(mbb);
                string = cb.toString();
            }*/

            /*InputStream ips = new FileInputStream(args[1]);
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.equals("\n")) {
                    text.append(ligne);
                    text.append("\n");
                } else {
                    text.append(ligne);
                    text.append("\n");
                }
            }
            br.close();*/
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
