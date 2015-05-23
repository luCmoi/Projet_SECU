import java.io.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Process proc;
        String file_path;
        String cmd[];
        Runtime rt;
        String line;
        BufferedReader br;
        File f;
        boolean b;
        PrintWriter pw;

        file_path = "/home/renaud/Documents/test_Term/src/t";
        cmd = new String[]{"gnome-terminal", "-e", "bash -c \"tty 1> " + file_path + "; exec bash\""};

        rt = Runtime&.getRuntime();
        proc = rt.exec(cmd);
        proc.waitFor();
        Thread.sleep(500);
        br = new BufferedReader(new FileReader(file_path));
        try {
            line  = br.readLine();
        } finally {
            br.close();
        }
        f = new File(file_path);
        b  = f.delete();
        System.out.println("file delete :" + b);
        System.out.println("tty :"+line+".");
        pw = new PrintWriter(new BufferedWriter(new FileWriter(line)));
        while(true){
            //pw.println("hello "+line);
            //pw.flush();

        }
    }
}
