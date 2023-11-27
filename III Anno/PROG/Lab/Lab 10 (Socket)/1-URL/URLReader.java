import java.net.*;
import java.io.*;

public class URLReader {

    public static void main(String[] args) {

					// apre socket verso l'indirizzo specificato (eccez se URL sbagliato)
		BufferedReader in = null;
		PrintWriter out = null;
		try {
   			URL yahoo = new URL("https://www.yahoo.com/");

   			in = new BufferedReader(
            	new InputStreamReader(yahoo.openStream()));

    		System.out.println("connesso");

   			out = new PrintWriter(new FileWriter("esempio.html"));

   			String inputLine;

   			while ((inputLine = in.readLine()) != null)
       			out.println(inputLine);
			}
  			catch (IOException e1) {System.out.println("Problema: " + e1.getMessage());}
			finally {
				if (in != null)
					try {
	   					in.close();
					}
					catch (IOException e2) {System.out.println(e2.getMessage());}
   				if (out!= null)
   						out.close();
  			}
		}
}