public class dechiffre {

    public static void checkargs(String[] args) {
        if (args.length != 3 || !(args[0].equals("c") || args[0].equals("p") || args[0].equals("v"))) {
            System.out.println("usage : dechiffre [c/p/v] cle texte");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        checkargs(args);

        switch (args[0]) {
            case "c":
                dechiffreCesar(args[1], args[2]);
                break;
            case "v":
                dechiffreVigenere(args[1], args[2]);
                break;
            case "p":
                dechiffrePermutation(args[1], args[2]);
                break;
        }

    }

    public static void dechiffreCesar(String cle, String text) {
        System.out.println("Dechiffre Cesar:");
        System.out.println("Cle: " + cle);
        System.out.println("Text: " + text);
    }

    public static void dechiffrePermutation(String cle, String text) {
        System.out.println("Dechiffre Permutation:");
        System.out.println("Cle: " + cle);
        System.out.println("Text: " + text);
    }

    public static void dechiffreVigenere(String cle, String text) {
        System.out.println("Dechiffre Vigenere:");
        System.out.println("Cle: " + cle);
        System.out.println("Text: " + text);
        String res;
        Vigenere v = new Vigenere(cle);
        res = v.dechiffreText(text);
        System.out.println(res);
    }
}
