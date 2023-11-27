/**
   @author Cay Horstmann
   @version 1.20 2004-08-03
   modificata...
*/

import java.io.*;
import java.net.*;
import java.util.*;

/**
   This program implements a multithreaded server that listens to port 8189 and echoes back
   all client input.
*/
public class ThreadedEchoServer {
   public static void main(String[] args ) {
	  System.out.println("Finestra del server: ");
      try {
         int i = 1;
         ServerSocket s = new ServerSocket(8189);

         while (true) {
            Socket incoming = s.accept(); // si mette in attesa di richiesta di connessione e la apre
            System.out.println("Spawning " + i);
            Runnable r = new ThreadedEchoHandler(incoming, i);
            new Thread(r).start();
            i++;
         }
      }
      catch (IOException e) {e.printStackTrace();}
   }
}

/**
   This class handles the client input for one server socket connection.
*/
class ThreadedEchoHandler implements Runnable {

   private Socket incoming;
   private int counter;

   /**
      Constructs a handler.
      @param i the incoming socket
      @param c the counter for the handlers (used in prompts)
   */
   public ThreadedEchoHandler(Socket in, int c) {
      incoming = in;
      counter = c;
   }

   public void run() {
      try {
         try {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();

            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);

            out.println( "Hello! Enter BYE to exit." );

            // echo client input
            boolean done = false;
            while (!done && in.hasNextLine()) {
               String line = in.nextLine();
               out.println("Echo: " + line);
               System.out.println("ECHO: "+ line);
               if (line.trim().equals("BYE"))
                  done = true;
            }
         }
         finally {
            incoming.close();
         }
      }
      catch (IOException e) {e.printStackTrace();}
   }

}

