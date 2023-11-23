import java.io.*;
import java.util.*;

class Sender extends Thread {
   private OutputStream out;

   public Sender (OutputStream w)
   	{out = w;}

   public void run() {
	  ObjectOutputStream oout = null;
	  try {
		  oout = new ObjectOutputStream(out);}
	  catch (IOException e) {System.out.println(e.getMessage());}
      for (int i=0; i<5; i++) {
      //while(true) {
          try {
			Date d = new Date();
            System.out.println("scrivi data: " + d);
            oout.writeObject(d);
            sleep(500);
          } catch (IOException e) {System.out.println(e.getMessage());}
            catch(InterruptedException e) {System.out.println(e.getMessage());}
	  }
   }
}

class Receiver extends Thread {
   private final InputStream in;

   public Receiver (InputStream r)
   		{in = r;}

   public void run() {
	   System.out.println("parte il receiver");
	   ObjectInputStream pin = null;
	   try { pin = new ObjectInputStream(in);
	   }
	   catch (IOException e){System.out.println(e.getMessage());}
       try {
         //while (true) {
		   for (int i=0; i<5; i++) {
            sleep(1000);
            System.out.println("leggi la data: " + (Date)pin.readObject());
         }
      } catch(IOException e) {//System.out.println("fine input");
      						  System.out.println(e.getMessage());
      						  }
        catch(ClassNotFoundException e) {System.out.println(e.getMessage());}
        catch(InterruptedException e) {System.out.println(e.getMessage());}
   }
}

public class PipeTestOggetti {
    public static void main(String argv[]) throws Exception {
      PipedOutputStream pout = new PipedOutputStream();
      PipedInputStream pin = new PipedInputStream(pout);
      Sender s = new Sender(pout);
      Receiver r = new Receiver(pin);
      s.start();
      r.start();
    }
}