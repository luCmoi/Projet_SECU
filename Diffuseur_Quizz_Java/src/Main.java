import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    void parse_args(String[] str){

    }

    public static void main(String [] args){

        String nom_diff = "QUIZZ";
        String multi_adresse = "";
        int port_mult = 0;
        int port_tcp = 0;
        int portgestionnaire = 0;
        String file = "";
        try{
            multi_adresse = args[0];
            port_mult = Integer.parseInt(args[1]);
            port_tcp = Integer.parseInt(args[2]);
            portgestionnaire = Integer.parseInt(args[3]);
            file = args[4];
        }
        catch(Exception e){
            System.out.println("erreur arguments : passage en saisie manuel");
            // TODO : saisie manuel
            System.exit(0);
        }
        Diffuseur d = new Diffuseur(nom_diff, port_tcp, multi_adresse, port_mult, file);
        Diff_to_Gestionnaire dtg = new Diff_to_Gestionnaire(d, portgestionnaire);
        Diff_to_multi dtm = new Diff_to_multi(d);

        Thread t = new Thread(dtm);
        t.start();
        t = new Thread(dtg);
        t.start();
        try {

            ServerSocket srvSock = new ServerSocket(d.getPort_user_message());

            while (true) {
                try {
                    Socket sock = srvSock.accept();
                    System.out.println("New connection stablished with " +
                            sock.getInetAddress().getHostName() +
                            " (port " + sock.getPort() +
                            "), trying to communicate...");
                    Diff_to_Client dtc = new Diff_to_Client(d, sock);
                    t = new Thread(dtc);
                    t.start();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Problem with client.\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to create server socket:");
            e.printStackTrace();
        }
    }
}
