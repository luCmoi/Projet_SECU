
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class Diffuseur{


    private String id_diff;
    private int port_user_message;
    private String ip_multibroadcast;
    private int port_multi_diffusion;
    private LinkedList<Message> historique;
    private LinkedList<Message> msg_to_duffuse;
    int num_mess = 0;
    int curr = 0;


    public Diffuseur(String id, int port_user_message, String ip_multibroadcast, int port_multi_diffusion, String file){
        this.id_diff = id;
        while (this.id_diff.length() < 8){
            this.id_diff += "#";
        }
        this.port_user_message = port_user_message;
        this.ip_multibroadcast = ip_multibroadcast;
        normalize_ip();
        this.port_multi_diffusion = port_multi_diffusion;
        this.historique = new LinkedList<>();
        this.msg_to_duffuse = new LinkedList<>();
        init_Question(file);

    }

    public void normalize_ip(){
        String ss[] = ip_multibroadcast.split("\\.");
        ip_multibroadcast = "";
        for (int i = 0; i<ss.length; i++){
            while (ss[i].length()<3){
                ss[i] = "0"+ss[i];
            }
            ip_multibroadcast += ss[i]+".";
        }
        ip_multibroadcast = ip_multibroadcast.substring(0, ip_multibroadcast.length()-1);
    }






    // *************
    // * FONCTIONS *
    // *************


    public synchronized void init_Question(String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {

                this.add_to_multi(this.getId() + " " + line);
                line = br.readLine();
            }
            br.close();

        }
        catch (Exception e){

        }
    }

    public void add_to_histo(){
        historique.add(msg_to_duffuse.get(curr));
    }

    public String get_next_message(){

        String s =  num_mess + " " + msg_to_duffuse.get(curr).toString();
        inc_num_mess();
        inc_cur_mess();
        add_to_histo();
        return s;
    }

    public synchronized void add_to_multi(String s){
        String ss[] = s.split(" ", 2);
        inc_num_mess();
        Message m = new Message(ss[0], ss[1]);
        msg_to_duffuse.add(m);

    }

    public synchronized void add_to_multi(String s, int n){
        String ss[] = s.split(" ", 2);
        inc_num_mess();
        Message m = new Message(ss[0], ss[1]);
        msg_to_duffuse.add(n, m);

    }
    public synchronized void recup_reponse(String s){
        add_to_multi(s, curr);

    }

    private void inc_cur_mess(){
        this.curr++;
        if (this.curr >= msg_to_duffuse.size()) this.curr = 0;
    }

    private void inc_num_mess(){
        this.num_mess++;
        if (this.num_mess > 9999) this.num_mess = 0;
    }




    // *************
    // * GET & SET *
    // *************


    public LinkedList<Message> getHistorique() {
        return historique;
    }


    public String getId() {
        return id_diff;
    }

    public int getPort_user_message() {
        return port_user_message;
    }

    public String getIp_multibroadcast() {
        return ip_multibroadcast;
    }


    public int getPort_multi_diffusion() {
        return port_multi_diffusion;
    }




}
