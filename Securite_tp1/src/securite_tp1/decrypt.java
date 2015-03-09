public class decrypt {

    public static void checkargs(String[] args) {
        if (args.length != 2 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : decrypt [c/p/v] texte");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        checkargs(args);

        switch (args[0]) {
            case "c":
                Cesar c = new Cesar();
                String chif = c.chiffre("bonjour les petits pinsons rotent de la choucroutte", "12");
                System.out.println(chif);
                System.out.println(c.dechiffre(chif, "12"));
                System.out.println(c.decrypt(chif));
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
        System.out.println("Text: " + text);
    }
}
