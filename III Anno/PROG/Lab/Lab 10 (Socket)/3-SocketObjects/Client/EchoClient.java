
import java.io.*;
import java.net.*;
import java.util.*;


public class EchoClient {
   public static void main(String[] args) {
      try {
         String nomeHost = InetAddress.getLocalHost().getHostName();
         System.out.println(nomeHost);
         Socket s = new Socket(nomeHost, 8189);
         System.out.println("Ho aperto il socket verso il server.\n");

         try {

            InputStream inStream = s.getInputStream();
            Scanner in = new Scanner(inStream);

            ObjectOutputStream outStream = new ObjectOutputStream(s.getOutputStream());

			System.out.println("Sto per ricevere dati dal socket server!");

            String line = in.nextLine();
            System.out.println(line);

            boolean done = false;
            outStream.writeObject(new Date());

            line = in.nextLine();
            System.out.println(line);
         }
         finally {
            s.close();
         }
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }
}
