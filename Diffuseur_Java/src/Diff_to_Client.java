import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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

            if (message == null){
                // TODO : erreur message reçu du client null
            }
            else if(message.startsWith("MESS")){
                System.out.println(message);
                // TODO : ajout du message client dans la liste

                String [] tab = message.split(" ", 3);
                if (tab.length != 3){
                    // TODO : ajout du message taille != 3
                }
                System.out.println(tab.length);
                diff.ajoute_message(new Message(tab[2], tab[1]));
                pw.print("ACKM\r\n");
                pw.flush();

            }
            else if(message.startsWith("LAST")){
                // TODO : envoi des n derniers messages diffusé
                String [] tab = message.split(" ");
                if (tab.length != 2){
                    // TODO : Last n messsages != 2
                }
                int nb_message = Integer.parseInt(tab[1]); // TODO : check error type
                ArrayList<Message> liste = diff.get_last_n_message(nb_message);
                for (Message  m : liste){
                    pw.print("OLDM 1 " + m.getId() + " " + m.getMessage() + "\r\n");
                    
                    pw.flush();
                }
                pw.print("ENDM\r\n");
                pw.flush();

            }


            br.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Communication failed. Quitting...");
            System.out.println("Connection closed. Try again later.");
            System.exit(0);
        }

    }
}
