import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    void parse_args(String[] str){

    }

    public static void main(String [] args){
        Diffuseur d = new Diffuseur("Allo", 1234, "225.77.13.99", 9999);
        Diff_to_Gestionnaire dtg = new Diff_to_Gestionnaire(d, 7896);
        Diff_to_multi dtm = new Diff_to_multi(d);
        d.initatise_messages("/media/data/git/Projet_Tweetoradio/text_diff_coree.txt");
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
