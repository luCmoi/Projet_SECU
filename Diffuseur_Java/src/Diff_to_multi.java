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
                Thread.sleep(1000);
                String s = "DIFF " + diff.getNum_message() + " " + diff.getNext_message() + "\r\n";
                //System.out.println(s);
                data = s.getBytes();
                diff.setCurent_message();
                diff.setNum_message();
                InetSocketAddress ia = new InetSocketAddress(diff.getIp_multibroadcast(),diff.getPort_multi_diffusion());
                DatagramPacket paquet = new DatagramPacket(data, data.length, ia);
                dso.send(paquet);

            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
