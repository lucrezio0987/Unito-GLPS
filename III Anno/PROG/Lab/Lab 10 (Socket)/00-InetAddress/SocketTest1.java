/**
   @version 1.20 2004-08-03
   @author Cay Horstmann
*/

import java.io.*;
import java.net.*;
import java.util.*;

/**
   This program makes a socket connection to the atomic clock
   in Boulder, Colorado, and prints the time that the
   server sends.
*/

// modificato

public class SocketTest1 {
   	public static void main(String[] args) {
	   String host = "www.unito.it";
	   try {
		   InetAddress local = InetAddress.getLocalHost();
		   InetAddress addr = InetAddress.getByName(host);
		   System.out.println("Locale: indirizzo IP: "+ local);
		   System.out.println("Remoto: indirizzo IP: " + addr);
	   }
	   catch(UnknownHostException e)
	   		{System.out.println(host +"sconosciuto");}
	}
}
