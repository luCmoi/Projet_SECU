import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class Diff_to_multi  implements Runnable{
    Diffuseur diff;

    public Diff_to_multi(Diffuseur d){
        this.diff = d;
    }



    @Override
    public void run() {
        try{
            DatagramSocket dso=new DatagramSocket();
            byte[]data;
            while (true){
                String s = diff.pop();
                if(s != null){
                    s = "DIFF " + s + "\r\n";
                    System.out.println(s);
                    data = s.getBytes();
                    InetSocketAddress ia = new InetSocketAddress(diff.getIp_multibroadcast(),diff.getPort_multi_diffusion());
                    DatagramPacket paquet = new DatagramPacket(data, data.length, ia);
                    dso.send(paquet);
                }
            }
        } catch(Exception e){

            e.printStackTrace();
            //System.exit(0);
        }
    }
}
