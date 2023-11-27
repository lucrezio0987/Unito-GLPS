
import java.io.*;
import java.net.*;
import java.util.*;


public class EchoClient {
   public static void main(String[] args) {
      try {
         String nomeHost = InetAddress.getLocalHost().getHostName();
         System.out.println(nomeHost);

					// vari modi di aprire il socket verso il server
         Socket s = new Socket(nomeHost, 8189);
         //Socket s = new Socket("127.0.0.1", 8189);
         //Socket s = new Socket("localhost", 8189);

         System.out.println("Ho aperto il socket verso il server");

         try {
            InputStream inStream = s.getInputStream();
            Scanner in = new Scanner(inStream);
            OutputStream outStream = s.getOutputStream();
            PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);
            Scanner stin = new Scanner(System.in);

			System.out.println("Sto per ricevere dati dal socket server!");

            String line = in.nextLine(); // attenzione: se il server non scrive nulla questo resta in attesa...
            System.out.println(line);

            boolean done = false;
            while (!done) /* && in.hasNextLine()) */ {

            	String lineout = stin.nextLine();
            	out.println(lineout);

            	line = in.nextLine();
            	System.out.println(line);
            	if (lineout.equals("BYE"))
            		done = true;
            }
         }
         finally {
            s.close();
         }
      }
      catch (IOException e) {e.printStackTrace();}
   }
}
