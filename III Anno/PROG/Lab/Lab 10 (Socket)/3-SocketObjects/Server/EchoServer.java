import java.io.*;
import java.net.*;
import java.util.*;

/**
   This program implements a simple server that listens to port 8189 and echoes back all
   client input.
*/
public class EchoServer {
   public static void main(String[] args ) {
	  System.out.println("Finestra del socket server");
      try {
         // establish server socket
         ServerSocket s = new ServerSocket(8189);

         // wait for client connection (1 sola connessione)
         Socket incoming = s.accept( );
         try {
            ObjectInputStream inStream = new ObjectInputStream(incoming.getInputStream());
            OutputStream outStream = incoming.getOutputStream();

            PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);

            out.println( "Hello! Waiting for data." );

            // echo client input
				try {
               		Date date = ((Date)inStream.readObject());
               		System.out.println("Echo: " + date.toString());
               		out.println("Echo: " + date.toString());
				} catch (ClassNotFoundException e) {System.out.println(e.getMessage());}
         }
         finally {
            incoming.close();
         }
      }
      catch (IOException e) {
         e.printStackTrace();
      }
   }
}


