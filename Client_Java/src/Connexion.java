import java.io.*;
import java.net.Socket;

public class Connexion {
    public Socket socket;
    public PrintWriter pw;
    public BufferedReader br;
    public boolean gestionnaire;

    public Connexion(String nom, int port, boolean gestionnaire) {
        try {
            socket = new Socket(nom, port);
            this.gestionnaire = gestionnaire;
            if (this.gestionnaire){
                Client.afficher("Connection etablie à un gestionnaire tapez -liste pour obtenir sa liste de diffuseurs\n");
            }
        } catch (IOException e) {
            Client.afficher("Echec de la connexion\n");
        }
    }

    //Recupere la liste des diffuseurs auprès du gestionnaire connecté
    public void getListe() {
        if (this.socket == null) {
            if (this.gestionnaire) {
                try {
                    if (pw == null) {
                        pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    }
                    if (br == null) {
                        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pw.print("LIST\r\n");
                //La lecture du retour seffectuera ici... Pas bien compris le format des messages

            }else {
                Client.afficher("Vous n'êtes pas connecter a un gestionnaire\n");
            }
        }else {
            Client.afficher("Vous n'êtes connecté a rien\n");
        }
    }
}
