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
         ServerSocket s = new ServerSocket(8189);
         Socket incoming = s.accept( );
         try {
            ObjectInputStream inStream = new ObjectInputStream(incoming.getInputStream());
            OutputStream outStream = incoming.getOutputStream();

            PrintWriter out = new PrintWriter(outStream, true);

            out.println( "Hello! Waiting for data." );

            // echo client input
            Vector<Date> v = null;
			try {
				v = ((Vector<Date>)inStream.readObject());
			} catch (ClassNotFoundException e) {System.out.println(e.getMessage());}

			if (v!=null)
            	for (int i=0; i<v.size(); i++) {
               		Date date = v.get(i);
               		System.out.println("Echo: " + date);
               		out.println("Echo: " + date);
            	}
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


