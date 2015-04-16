import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public Diffuseur(String id, String ip1, int port1, String ip2, int port2) {
        this.id = id;
        this.ip1 = ip1;
        this.port1 = port1;
        this.ip2 = ip2;
        this.port2 = port2;
    }

    public static void connect(ArrayList<Diffuseur> liste, String nom) {
        for (Diffuseur dc : liste) {
            if (dc.id.equals(nom)) {
                if (Client.diffuseursConnecte.contains(dc)) {
                    Client.afficher("Vous écoutez déja ce diffuseur");
                    return;
                }
                try {
                    MulticastSocket ms = new MulticastSocket(dc.port1);
                    ms.joinGroup(InetAddress.getByName(dc.ip1));
                    Client.diffuseursConnecte.add(dc);
                    ecoute(dc, ms);
                    break;
                } catch (Exception e) {
                    Client.afficher("Erreur impossible de se connecter au diffuseur");
                }
            }
        }
    }

    public static void deconnect(ArrayList<Diffuseur> liste, String nom) {
        for (Diffuseur dc : liste) {
            if (dc.id.equals(nom)) {
                dc.ecoute.interrupt();
                return;
            }
        }
        Client.afficher("Vous n'etes pas connecté a ce diffuseur.");
    }

    public static void ecoute(Diffuseur dc, MulticastSocket ms) {
        dc.ecoute = new Thread(new RunEcoute(ms,dc));
        dc.ecoute.start();
    }

    public static void changeSortie (Diffuseur dc, String sortie){

        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(sortie)));
        } catch (IOException e) {
            Client.afficher("Impossible de rediriger la sortie : " + e.getMessage());
        }
    }

    public static class RunEcoute implements Runnable {
        MulticastSocket ms;
        Diffuseur container;

        public RunEcoute(MulticastSocket ms, Diffuseur container) {
            this.ms = ms;
            this.container = container;
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
                        if (container.pw == null) {
                            Client.afficher(paquetSplit[2] + " : " + paquetSplit[3]);
                        } else {
                            container.pw.println(paquetSplit[2] + " : " + paquetSplit[3]);
                        }
                    }
                } catch (Exception e) {
                    Client.afficher(e.toString());
                }
            }
        }
    }

}
