import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Diffuseur{


    private String id_diff;
    private int port_user_message;
    private String ip_multibroadcast;
    private int port_multi_diffusion;
    private LinkedList<Message> historique;
    private LinkedList<Question> questions;
    public LinkedList<String> msg_to_diff;
    private int num_question = 0;
    int num_mess = 0;


    public Diffuseur(String id, int port_user_message, String ip_multibroadcast, int port_multi_diffusion, String file){
        this.id_diff = id;
        while (this.id_diff.length() < 8){
            this.id_diff += "#";
        }
        this.port_user_message = port_user_message;
        this.ip_multibroadcast = ip_multibroadcast;
        this.port_multi_diffusion = port_multi_diffusion;
        this.historique = new LinkedList<>();
        this.questions = new LinkedList<>();
        msg_to_diff = new LinkedList<>();
        init_Question(file);
        add_to_multi(next_question());

    }








    // *************
    // * FONCTIONS *
    // *************


    public synchronized void init_Question(String file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();

            while (line != null) {

                this.add_to_question(line);
                line = br.readLine();
            }
            br.close();

        }
        catch (Exception e){

        }
    }

    public void add_to_question(String line){
        String[] tab = line.split(";");
        Question q = new Question("QUIZZ", tab[0], tab[1], tab[2]);
        questions.add(q);
    }

    public void add_to_histo(String id, String mess, int num){
        Message m = new Message(id, mess);
        historique.add(m);
        m.setNum(num_mess);
    }


    public String next_question(){
        System.out.println("la question est :" + questions.get(num_question).toString());
        System.out.println("les réponses sont :");
        for (String s : questions.get(num_question).getReponses()){
            System.out.println(s);
        }

        return questions.get(num_question).toString();
    }

    public synchronized void add_to_multi(String s){
        String ss[] = s.split(" ", 2);
        add_to_histo(ss[0], ss[1], num_mess);
        inc_num_mess();
        msg_to_diff.add(num_mess + " " + s);
    }
    public synchronized void recup_reponse(String s){
        String tab[] = s.split(" ", 2);
        Question q = questions.get(num_question);
        add_to_multi(s);
        if(q.win(tab[1])){
            num_inc();
            add_to_multi(this.id_diff + " " + tab[0] +" a répondu " + tab[1]);
            add_to_multi(this.id_diff + " " + tab[0] + " WIN !!");
            add_to_multi(next_question());

        }
        else{
            add_to_multi(this.id_diff + " " + tab[0] +" a répondu " + tab[1]);
            add_to_multi(this.id_diff + " " + tab[0] + " mauvaise réponse !");
        }
    }


    public synchronized String[] get_hints(){
        return questions.get(num_question).getHints();
    }

    public synchronized String pop(){

        if (!msg_to_diff.isEmpty()){
            return  msg_to_diff.pop();
        }

        else return null;
    }

    private void inc_num_mess(){
        this.num_mess++;
        if (this.num_mess > 9999) this.num_question = 0;
    }

    private void num_inc(){
        this.num_question++;
        if (this.num_question >= questions.size()) this.num_question = 0;
    }


     // *************
     // * GET & SET *
     // *************


    public LinkedList<Message> getHistorique() {
        return historique;
    }

    public void setHistorique(LinkedList<Message> historique) {
        this.historique = historique;
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
