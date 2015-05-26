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
            if (message == null)return;
            else if(message.startsWith("MESS")){
                String ss[] = message.split(" ", 3);
                String mess = ss[2];
                System.out.println(mess);
                if(mess.startsWith("LAQU")){
                    String s = diff.next_question();
                    pw.print("ITEM " + s + "\r\n");
                    pw.flush();
                    pw.print("ENDM\r\n");
                    pw.flush();

                }
                else if(mess.startsWith("HINT")){
                    String tab[] = diff.get_hints();

                    String g = diff.getId() + " Un joueur a demandé des indices :";
                    diff.add_to_multi(g);
                    for (String s : tab) {
                        g = diff.getId() + " " + s;
                        diff.add_to_multi(g);
                        pw.print("ITEM " + s + "\r\n");
                        pw.flush();
                    }

                    pw.print("ENDM\r\n");
                    pw.flush();

                }

                else if(mess.startsWith("HINT")){
                    String tab[] = diff.get_hints();

                    String g = diff.getId() + " Un joueur a demandé des indices :";
                    diff.add_to_multi(g);
                    for (String s : tab) {
                        g = diff.getId() + " " + s;
                        diff.add_to_multi(g);
                        pw.print("ITEM " + s + "\r\n");
                        pw.flush();
                    }

                    pw.print("ENDM\r\n");
                    pw.flush();

                }

                else if(mess.startsWith("HELP")){
                    pw.print("ITEM LAQU : affiche la dernière question posée\r\n");
                    pw.flush();
                    pw.print("ITEM HINT : affiche pour le client et sur la multi diffusion des indice sur la question courante\r\n");
                    pw.flush();
                    pw.print("ITEM CLAS : affiche le classement de la partie courante\r\n");
                    pw.flush();
                    pw.print("ITEM HELP : affiche ce message\r\n");
                    pw.flush();

                    pw.print("ENDM\r\n");
                    pw.flush();

                }
                else {
                    String [] tab = message.split(" ", 3);
                    if (tab.length != 3) return;
                    diff.recup_reponse(tab[1] + " " + tab[2]);
                    pw.print("ACKM\r\n");
                    pw.flush();
                }

            }
            // TODO :
            else if(message.startsWith("LAST")){
                String [] tab = message.split(" ");
                if (tab.length != 2) return;
                int nb_message = 0;
                try {
                    nb_message = Integer.parseInt(tab[1]);
                }
                catch (Exception e){
                    pw.print("ENDM\r\n");
                    pw.flush();
                    br.close();
                    pw.close();
                    socket.close();
                    return;
                }

                nb_message = Math.min(nb_message, 9999);
                int size = diff.getHistorique().size()-1;
                for (int i = size; i> size-nb_message; i--){
                    Message m = diff.getHistorique().get(i);
                    pw.print("OLDM " + m.getNum() + " "+ m.getId() + " " + m.getMessage() + "\r\n");
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
