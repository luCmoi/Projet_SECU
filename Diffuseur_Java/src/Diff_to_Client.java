import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Diff_to_Client implements Runnable{

    private Diffuseur diff;
    Socket socket;

    public Diff_to_Client(Diffuseur d, Socket s){
        this.diff = d;
        this.socket = s;
    }


    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String message = br.readLine();
            System.out.println(message);
            if (message == null){
                // TODO : erreur message reçu du client null
            }
            else if(message.startsWith("MESS")){
                // TODO : ajout du message client dans la liste

                String [] tab = message.split(message);
                if (tab.length != 3){
                    // TODO : ajout du message taille != 3
                }
                diff.ajoute_message(new Message(tab[2], tab[1]));
                pw.print("ACKM\r\n");
                pw.flush();

            }
            else if(message.startsWith("LAST")){
                // TODO : envoi des n derniers messages diffusé
            }


            br.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Communication failed. Quitting...");
            System.out.println("Connection closed. Try again later.");
            System.exit(0);
        }

    }
}
