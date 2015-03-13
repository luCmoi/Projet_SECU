import java.io.*;
import java.util.Random;

public class chiffre {

    public static void checkargs(String[] args) {
        if (args.length != 3 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : chiffre [c/p/v] cle texte");
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
                //String s = "";
                if(ligne.equals("\n")){
                    text += ligne;
                }
                else {
                    text += ligne+"\n";
                }
                //ligne.equals("\n"))?"":"\n";

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
        //System.out.println("Text: " + text);
        try {
            String fichier2 = "./sortie.txt";
            OutputStream ips = new FileOutputStream(fichier2);
            OutputStreamWriter ipsr = new OutputStreamWriter(ips);
            PrintWriter br = new PrintWriter(ipsr);
            br.print(new Cesar().chiffre(text, cle));
            br.flush();
            br.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
        //System.out.println(new Cesar().chiffre(text, cle));
    }


    public static void chiffrePermutation(String cle, String text) {
        System.out.println("Chiffre Permutation:");
        System.out.println("Cle: " + cle);
        System.out.println("Text: " + text);
    }

    public static void chiffreVigenere(String cle, String text) {
        System.out.println("Chiffre Vigenere:");
        System.out.println("Cle: " + cle);
        //System.out.println("Text: " + text);
        String res;
        Vigenere v = new Vigenere(cle);
        long start = System.currentTimeMillis();
        res = v.chiffreText(text);
        long end = System.currentTimeMillis();
        System.err.println("Chiffre time: " + ((end - start)) + " ms");

        try {
            String fichier2 = "./chiffreV.txt";
            OutputStream ips = new FileOutputStream(fichier2);
            OutputStreamWriter ipsr = new OutputStreamWriter(ips);
            PrintWriter br = new PrintWriter(ipsr);
            br.print(res);
            br.flush();
            br.close();
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
}
