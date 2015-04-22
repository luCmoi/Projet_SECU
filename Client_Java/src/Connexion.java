import java.io.*;
import java.net.Socket;

public class Connexion {
    public Socket socket;
    public PrintWriter pw;
    public BufferedReader br;

    public Connexion(String nom, int port) {
        try {
            socket = new Socket(nom, port);
        } catch (IOException e) {
            Client.afficher("Echec de la connexion : " + e.getMessage());
            dispose();
        }
    }

    //Recupere la liste des diffuseurs auprès du gestionnaire connecté
    public void getListe() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (socket != null) {
                    try {
                        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                        dispose();
                    }
                } else {
                    Client.afficher("Vous n'êtes connecté a rien\n");
                    dispose();
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


    public String rempli(String mess, int taille) {
        char[] message = new char[taille];
        for (int i = 0; i < message.length; i++) {
            if (i < mess.length()) {
                message[i] = mess.charAt(i);
            } else {
                message[i] = '#';
            }
        }
        return new String(message);
    }

    public String rempliDevan(String mess, int taille) {
        char[] message = new char[taille];
        int i;
        for (i = mess.length(); i >= 0; i--) {
            message[message.length - (mess.length() - i)] = mess.charAt(mess.length() - i);
        }
        for (i = 0; i < taille; i++) {
            if (message[i]=='\0') {
                message[i] = '0';
            } else {
                break;
            }
        }
        return new String(message);
    }

    public void post(final String message) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (socket != null) {
                    try {
                        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    } catch (IOException e) {
                        Client.afficher("Erreur : Impossible de communiquer : " + e.getMessage());
                        return;
                    }
                    if (message.length() <= 140) {
                        //Envoi du message
                        pw.print("MESS " + rempli(Client.nom, 8) + " " + rempli(message, 140) + "\r\n");
                        pw.flush();
                        try {
                            //Reçu de la réponse
                            String lecture = br.readLine();
                            if (lecture.equals("ACKM")) {
                                Client.afficher("Le message a bien été envoyé");
                            }
                            Client.afficher("");
                            dispose();
                        } catch (Exception e) {
                            Client.afficher("La lecture a échouée : " + e.getMessage());
                            dispose();
                        }
                    }
                } else {
                    Client.afficher("Vous n'êtes connecté a rien\n");
                    dispose();
                }
            }
        }
        ).start();
    }

    public void affiche(final String nombre) {
        try {
            int nb = Integer.parseInt(nombre);
            if (nb <= 0 || nb >= 999) {
                Client.afficher("Entrez un nombre entre 0 et 999\n");
                dispose();
            }
        } catch (Exception e) {
            Client.afficher("Veuillez passer un nombre en argument \n");
            dispose();
        }
        new Thread(new Runnable() {

            @Override
            public void run() {
                if (socket != null) {
                    try {
                        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    } catch (IOException e) {
                        Client.afficher("Erreur : Impossible de communiquer : " + e.getMessage());
                        return;
                    }
                    //Envoi de la demande last
                    pw.print("LAST " + rempliDevan(nombre, 3) + "\r\n");
                    pw.flush();
                    try {
                        //Reçu des messages
                        String [] lecture;
                        while(!br.readLine().equals("ENDM")){
                            lecture = br.readLine().split(" ");
                            Client.afficher(lecture[2]+" : "+lecture[3]);
                        }
                        Client.afficher("");
                        dispose();
                    } catch (Exception e) {
                        Client.afficher("La lecture a échouée : " + e.getMessage());
                        dispose();
                    }
                } else {
                    Client.afficher("Vous n'êtes connecté à rien\n");
                    dispose();
                }
            }
        }
        ).start();
    }
}

