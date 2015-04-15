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
    boolean show = false;

    public Diffuseur(String id, String ip1, int port1, String ip2, int port2) {
        this.id = id;
        this.ip1 = ip1;
        this.port1 = port1;
        this.ip2 = ip2;
        this.port2 = port2;
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
        Diffuseur dc = cherche(false, nom);
        if (dc != null) {
            dc.show = false;
        }
    }

    //Reprend l'écoute d'un diffuseur
    public static void show(String nom) {
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
                Client.afficher(dc.id + " (Ip : " + dc.ip1 + ", Port : " + dc.port1 + ", Ip machine : " + dc.ip2 + ", Port machine : " + dc.port2 + ") ");
            }
        }
        Client.afficher("\n");
    }

    //Connection en udp a un diffuseur
    public static void connectUDP(String nom) {
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
                    dc.ecoute(ms);
                } catch (Exception e) {
                    Client.afficher("Erreur impossible de se connecter au diffuseur : " + e.getMessage());
                }
            }
        }
    }

    //Deconnection en udp a un diffuseur
    public static void deconnectUDP(String nom) {
        Diffuseur dc = cherche(false, nom);
        if (dc != null) {
            dc.ecoute.interrupt();
            Client.diffuseursConnecte.remove(dc);
        }
    }

    //Abonne a un diffuseur
    public void ecoute(MulticastSocket ms) {
        ecoute = new Thread(new RunEcoute(ms));
        show = true;
        ecoute.start();
    }

    public class RunEcoute implements Runnable {
        MulticastSocket ms;

        public RunEcoute(MulticastSocket ms) {
            this.ms = ms;
        }

        @Override
        public void run() {
            byte[] data = new byte[1024];
            DatagramPacket paquet = new DatagramPacket(data, data.length);
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ms.receive(paquet);
                    String[] paquetSplit = new String(paquet.getData(), 0, paquet.getLength()).split(" ", 4);
                    if (paquetSplit[0].equals("DIFF")) {
                        if (show) {
                            Client.afficher(paquetSplit[2] + " : " + paquetSplit[3]);
                        }
                    }
                } catch (Exception e) {
                    Client.afficher(e.getMessage());
                }
            }
        }
    }
}
