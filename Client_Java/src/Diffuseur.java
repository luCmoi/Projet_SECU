import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Diffuseur {
    String id;
    String ip1;
    int port1;
    String ip2;
    int port2;
    Thread ecoute;
    PrintWriter pw;
    boolean show = false;

    public Diffuseur(String id, String ip1, int port1, String ip2, int port2) {
        this.id = id;
        this.ip1 = ip1;
        this.port1 = port1;
        this.ip2 = ip2;
        this.port2 = port2;
    }

    public String afficheId() {
        int i;
        for (i = id.length() - 1; i > 0; i--) {
            if (id.charAt(i) != '#') {
                break;
            }
        }
        return id.substring(0, i + 1);
    }

    public static String traduitId(String nom){
        if (nom.length()<8) {
            for (int i = nom.length(); i < 8; i++) {
                nom = nom + '#';
            }
            return nom;
        }else {
            return  nom;
        }
    }

    public static Diffuseur cherche(boolean connu, String nom) {
        ArrayList<Diffuseur> liste;
        if (connu) {
            liste = Client.diffuseursConnus;
        } else {
            liste = Client.diffuseursConnecte;
        }
        for (Diffuseur dc : liste) {
            //Cherche si le diffuseur est dans la liste
            if (dc.id.equals(nom)) {
                return dc;
            }
        }
        Client.afficher("Ce diffuseur n'est pas trouvable : " + nom + "\n");
        return null;
    }

    //Pause l'écoute d'un diffuseur
    public static void hide(String nom) {
        nom = traduitId(nom);
        Diffuseur dc = cherche(false, nom);
        if (dc != null) {
            dc.show = false;
        }
    }

    //Reprend l'écoute d'un diffuseur
    public static void show(String nom) {
        nom = traduitId(nom);
        Diffuseur dc = cherche(false, nom);
        if (dc != null) {
            dc.show = true;
        }
    }

    //Affiche la liste des diffuseurs
    public static void liste(boolean connu) {
        ArrayList<Diffuseur> liste;
        if (connu) {
            liste = Client.diffuseursConnus;
            Client.afficher("Liste des diffuseurs connus : \n");
        } else {
            liste = Client.diffuseursConnecte;
            Client.afficher("Liste des diffuseurs connéctés : \n");
        }
        if (liste.isEmpty()) {
            Client.afficher("Liste vide");
        } else {
            for (Diffuseur dc : liste) {
                Client.afficher(dc.afficheId() + " (Ip : " + dc.ip1 + ", Port : " + dc.port1 + ", Ip machine : " + dc.ip2 + ", Port machine : " + dc.port2 + ") ");
            }
        }
        Client.afficher("\n");
    }

    //Connection en udp a un diffuseur
    public static void connectUDP(String nom) {
        nom = traduitId(nom);
        Diffuseur dc = cherche(true, nom);
        if (dc != null) {
            if (Client.diffuseursConnecte.contains(dc)) {
                Client.afficher("Vous écoutez déja ce diffuseur");
            } else {
                try {
                    //Démarre l'écoute
                    MulticastSocket ms = new MulticastSocket(dc.port1);
                    ms.joinGroup(InetAddress.getByName(dc.ip1));
                    Client.diffuseursConnecte.add(dc);
                    dc.ecoute(ms, dc);
                } catch (Exception e) {
                    Client.afficher("Erreur impossible de se connecter au diffuseur : " + e.getMessage());
                }
            }
        }
    }

    //Deconnection en udp a un diffuseur
    public static void deconnectUDP(String nom) {
        nom = traduitId(nom);
        Diffuseur dc = cherche(false, nom);
        if (dc != null) {
            dc.ecoute.interrupt();
            Client.diffuseursConnecte.remove(dc);
        }
        Client.afficher("Vous n'etes pas connecté a ce diffuseur.");
    }

    public static void changeSortie(String nom, String sortie) {
        nom = traduitId(nom);
        Diffuseur dc = cherche(false, nom);
        try {
            dc.pw = new PrintWriter(new BufferedWriter(new FileWriter(sortie)));
        } catch (IOException e) {
            Client.afficher("Impossible de rediriger la sortie : " + e.getMessage());
        }
    }

    //Abonne a un diffuseur
    public void ecoute(MulticastSocket ms, Diffuseur dc) {
        ecoute = new Thread(new RunEcoute(ms, dc));
        show = true;
        ecoute.start();
    }

    public void recupNom() {
        String line;
        String file_path = "/tmp/lectureNomTerm";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file_path));
            line = br.readLine();
            br.close();
        } catch (Exception e) {
            Client.afficher("Echec a la lecture du nom de terminal " + e.getMessage());
            return;
        }
        new File(file_path).delete();
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(line)));
        } catch (IOException e) {
            Client.afficher("Impossible de rediriger la sortie : " + e.getMessage());
        }
    }

    public void newTerm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process p;
                    String file_path = "/tmp/lectureNomTerm";
                    String cmd[] = new String[]{"gnome-terminal", "-e", "bash -c \"tty 1> " + file_path + "; exec bash\""};
                    p = Runtime.getRuntime().exec(cmd);
                    p.waitFor();
                    Thread.sleep(500);
                    Diffuseur.this.recupNom();
                } catch (Exception e) {
                    try {
                        Process p;
                        String file_path = "/tmp/lectureNomTerm";
                        String cmd[] = new String[]{"xterm", "-e", "bash -c \"tty 1> " + file_path + "; exec bash\""};
                        p = Runtime.getRuntime().exec(cmd);
                        p.waitFor();
                        Thread.sleep(500);
                        Diffuseur.this.recupNom();
                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                }

            }
        }).start();
    }

    public static void post(String nom, String message) {
        Diffuseur dc = cherche(true, nom);
        if (dc != null) {
            new Connexion(dc.ip2, dc.port2).post(message);
        }
    }

    public static void ancien(String nom, String nombre) {
        Diffuseur dc = cherche(true, nom);
        if (dc != null) {
            new Connexion(dc.ip2, dc.port2).affiche(nombre);
        }
    }


    public class RunEcoute implements Runnable {
        MulticastSocket ms;
        Diffuseur container;

        public RunEcoute(MulticastSocket ms, Diffuseur container) {
            this.ms = ms;
            this.container = container;
        }

        @Override
        public void run() {
            container.newTerm();
            byte[] data = new byte[1024];
            DatagramPacket paquet = new DatagramPacket(data, data.length);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ms.receive(paquet);
                    String[] paquetSplit = new String(paquet.getData(), 0, paquet.getLength()).split(" ", 4);
                    if (paquetSplit[0].equals("DIFF")) {
                        if (container.pw == null) {
                            Client.afficher(paquetSplit[2] + " : " + paquetSplit[3]);
                        } else {
                            container.pw.println(paquetSplit[2] + " : " + paquetSplit[3]);
                            container.pw.flush();
                        }
                    }
                } catch (Exception e) {
                    Client.afficher(e.getMessage());
                }
            }
        }
    }

}
