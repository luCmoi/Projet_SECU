import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.Socket;

public class Diff_to_Gestionnaire implements Runnable{

    Diffuseur diff;
    private int port_gestionnaire;

    public Diff_to_Gestionnaire(Diffuseur diff, int port_gestionnaire){
        this.diff = diff;
        this.port_gestionnaire = port_gestionnaire;
    }
    @Override
    public void run() {

        try {
            String servAddr = Inet4Address.getLocalHost().getHostAddress();
            Socket sock = new Socket(servAddr, port_gestionnaire);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
                String reg = "REGI " + diff.getId() + " " + diff.getIp_multibroadcast() + " " + diff.getPort_multi_diffusion() + " " + servAddr + " " + diff.getPort_user_message() + "\r\n";
                System.out.println(reg);
                pw.println(reg);
                pw.flush();

                String msg = br.readLine();
                if (!msg.equals("REOK")) {
                    System.out.println("Erreur : connexion au gestionnaire");
                    br.close();
                    pw.close();
                    sock.close();
                    System.exit(0);
                }

                while (true) {
                    msg = br.readLine();
                    System.out.println("message recu du gestionnaire :" + msg);
                    if (msg.equals("RUOK")) {
                        pw.println("IMOK\r\n");
                        pw.flush();
                    }
                    else {
                        System.out.println("Erreur : message != RUOK " + msg);
                        pw.close();
                        br.close();
                        sock.close();
                        System.exit(0);
                    }
                }

            } catch (Exception e) {
                System.out.println("Communication failed. Quitting...");
                sock.close();
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Connection failed. Try again later.");
        }

    }
}
