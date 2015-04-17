import java.io.*;
import java.net.Socket;

public class Connexion {
    public Socket socket;
    public PrintWriter pw;
    public BufferedReader br;
    public boolean gestionnaire;

    public Connexion(String nom, int port, boolean gestionnaire) {
        try {
            socket = new Socket(nom, port);
            this.gestionnaire = gestionnaire;
            if (this.gestionnaire) {
                Client.afficher("Connection etablie à un gestionnaire tapez -liste pour obtenir sa liste de diffuseurs\n");
            }
        } catch (IOException e) {
            Client.afficher("Echec de la connexion : " + e.getMessage());
        }
    }

    //Recupere la liste des diffuseurs auprès du gestionnaire connecté
    public void getListe() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (socket != null) {
                    if (gestionnaire) {
                        try {
                            if (pw == null) {
                                pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                            }
                            if (br == null) {
                                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            }
                        } catch (IOException e) {
                            Client.afficher("Erreur : Impossible de communiquer : " + e.getMessage());
                            return;
                        }
                        //Envoi de list
                        pw.print("LIST\r\n");
                        pw.flush();
                        try {
                            //Reçu des infos
                            String[] lecture = br.readLine().split(" ");
                            //lecture du nombre de diffuseurs
                            if (lecture[0].equals("LINB")) {
                                long timer = System.currentTimeMillis();
                                int taille = Integer.parseInt(lecture[1]);
                                int lu = 0;
                                Client.afficher("Liste de diffuseurs disponibles : " + taille);
                                //Lecture des diffuseurs
                                while (lu < taille) {
                                    //Temps d'attente dépassé
                                    if (System.currentTimeMillis() - timer > 60000) {
                                        Client.afficher("Delai dépassé arret de l'attente de nouveau diffuseurs");
                                        break;
                                    }
                                    lecture = br.readLine().split(" ");
                                    if (lecture[0].equals("ITEM")) {
                                            Client.diffuseursConnus.add(new Diffuseur(lecture[1], lecture[2], Integer.parseInt(lecture[3]), lecture[4], Integer.parseInt(lecture[5])));
                                        lu++;
                                        Client.afficher("Nouveau diffuseur : " + lecture[1] + " (Ip : " + lecture[2] + ", Port : " + lecture[3] + ", Ip machine : " + lecture[4] + ", Port machine : " + lecture[5] + ") ");
                                    }
                                }
                                Client.afficher("");
                                dispose();
                            }
                        } catch (Exception e) {
                            Client.afficher("La lecture a échouée : " + e.getMessage());
                        }
                    } else {
                        Client.afficher("Vous n'êtes pas connecté a un gestionnaire\n");
                    }
                } else {
                    Client.afficher("Vous n'êtes connecté a rien\n");
                }
            }
        }
        ).start();
    }

    public void dispose() {
        try {
            if (br != null) {
                br.close();
            }
            if (pw != null) {
                pw.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            Client.afficher("Une erreur est survenue a la liberation d'espace : " + e.getMessage());
        }
    }
}
