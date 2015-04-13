import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    private static String nom = "Default";
    public static ArrayDeque<String> sortieStandard = new ArrayDeque<String>();
    public static Connexion connexion;
    public static ArrayList<Diffuseur> diffuseursConnus = new ArrayList<Diffuseur>();
    public static ArrayList<Diffuseur> diffuseursConnecte = new ArrayList<Diffuseur>();


    //Runnable qui s'occupe d'écouter en continu le terminal
    static class RunnableRecep implements Runnable {

        Scanner sc = new Scanner(System.in);

        @Override
        public void run() {
            String lecture = new String();
            String[] lectureSplit;
            int taille = 0;
            while (true) {
                lecture = sc.nextLine();
                //Lecture d'un message different qu'un message affiché
                if (!(lecture.equals(sortieStandard.poll()))) {
                    lectureSplit = lecture.split(" ");
                    taille = lectureSplit.length;
                    //Commande
                    if (lectureSplit[0].length()>0 && '-' == lectureSplit[0].charAt(0)) {
                        //Liste des commandes
                        if ("-help".equals(lectureSplit[0])) {
                            afficher("Liste des commandes : \n");
                            afficher("-help : Liste des co-mmandes");
                            afficher("-name nouveauNom : Change le nom d'utilisateur");
                            afficher("-connectG adresse port : Se connecter a un gestionnaire et ajouter ses diffuseurs");
                            afficher("-abonne nom : Se connecter a un diffuseur connu\n");
                        }//Changer de nom
                        else if ("-name".equals(lectureSplit[0])) {
                            if (taille > 1) {
                                nom = lectureSplit[1];
                            } else {
                                nom = "Default";
                            }
                            afficher("Votre nouveau nom est " + nom + "\n");
                        } //Se connecter (Pour l'instant uniquement gestionnaire)
                        else if ("-connectG".equals(lectureSplit[0])) {
                            if (taille <= 3) {
                                connexion = new Connexion(lectureSplit[1], Integer.parseInt(lectureSplit[2]), true);
                            } else {
                                afficher("Mauvaise utilisation de -connectG tapez -help pour recevoir la liste des commandes\n");
                            }
                        }//Sur un gestionnaire recuperer la liste
                        else if ("-liste".equals(lectureSplit[0]) && connexion != null) {
                            connexion.getListe();
                        } else if ("-abonne".equals(lectureSplit[0])) {
                            Diffuseur.connect(diffuseursConnus, lectureSplit[1]);
                        } else {
                            afficher("Commande non reconnue tapez -help pour recevoir la liste des commandes\n");
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        afficher("Bonjour, votre nom est default, taper -help pour recevoir la liste des commandes.");
        new Thread(new RunnableRecep()).start();
    }

    //Affiche un message et l'ajoute aux messages envoyés
    public static void afficher(String message) {
        sortieStandard.add(message);
        System.out.println(message);
    }
}
