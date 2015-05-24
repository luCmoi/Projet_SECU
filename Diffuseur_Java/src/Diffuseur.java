import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Diffuseur{


    private String id_diff;
    private int port_user_message;
    private String ip_multibroadcast;
    private int port_multi_diffusion;
    private ArrayList<Message> liste_message;
    private short num_message;
    private short curent_message;

    public Diffuseur(String id, int port_user_message, String ip_multibroadcast, int port_multi_diffusion){
        this.id_diff = id;
        this.port_user_message = port_user_message;
        this.ip_multibroadcast = ip_multibroadcast;
        this.port_multi_diffusion = port_multi_diffusion;
        this.liste_message = new ArrayList<Message>();
        this.num_message = 0;
        this.curent_message = 0;


    }

    public Diffuseur(String id, int port_user_message, String ip_multibroadcast){
        this.id_diff = id;
        this.liste_message = new ArrayList<Message>();
        this.num_message = 0;
        this.curent_message = 0;
    }




    // *************
    // * FONCTIONS *
    // *************



    public boolean initatise_messages(String path){
        int read, N = 140;
        char[] buffer = new char[N];

        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            read = N+1;
            while (read >= N) {
                read = br.read(buffer, 0, N);
                this.liste_message.add(new Message(String.valueOf(buffer),this.id_diff));
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;


        }
        return true;
    }

    public void ajoute_message(Message mess){
        //System.out.println("\n\n\n\ncurr: "+this.curent_message);
        this.liste_message.add(this.curent_message, mess);
    }

    public void ajoute_message_to_current_place(Message mess, int place){
        this.liste_message.add(place, mess);
    }

    /**
     * Donne les n derniers messages envoyer par le diffuseur
     * @param n
     * @return
     */
    public ArrayList<Message> get_last_n_message(int n){
        ArrayList<Message> liste = new ArrayList<Message>();
        int i = curent_message-1;
        while (n != 0){
            if(i < 0 ) i = liste_message.size()-1;
            if(!liste_message.get(i).isAlready_send()) break;
            liste.add(liste_message.get(i));
            n--;
            i--;
        }
        return liste;
    }

     // *************
     // * GET & SET *
     // *************

    public String getId() {
        return id_diff;
    }

    public void setId(String id) {
        this.id_diff = id;
    }

    public int getPort_user_message() {
        return port_user_message;
    }

    public void setPort_user_message(int port_user_message) {
        this.port_user_message = port_user_message;
    }

    public String getIp_multibroadcast() {
        return ip_multibroadcast;
    }

    public void setIp_multibroadcast(String ip_multibroadcast) {
        this.ip_multibroadcast = ip_multibroadcast;
    }

    public int getPort_multi_diffusion() {
        return port_multi_diffusion;
    }

    public void setPort_multi_diffusion(int port_multi_diffusion) {
        this.port_multi_diffusion = port_multi_diffusion;
    }

    public ArrayList<Message> getListe_message() {
        return liste_message;
    }

    public Message get_message(int i){
        return liste_message.get(i);
    }

    public void setListe_message(ArrayList<Message> liste_message) {
        this.liste_message = liste_message;
    }

    public short getNum_message() {

        return num_message;
    }

    public void setNum_message() {
        this.num_message++;
        if (this.num_message > 9999) this.num_message = 0;
    }

    private Message getNext_message_M() {
        return liste_message.get(this.curent_message);
    }

    public String getNext_message(){
        this.getNext_message_M().setAlready_send(true);
        return this.getNext_message_M().getId() + " " + this.getNext_message_M().getMessage();
    }

    public void setCurent_message() {
        this.curent_message++;
        if (this.curent_message >= liste_message.size()) this.curent_message = 0;
    }
}
