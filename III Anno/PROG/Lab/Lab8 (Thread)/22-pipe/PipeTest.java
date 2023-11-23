import java.io.*;

class Sender extends Thread {
   private Writer out;

   public Sender (Writer w)
   	{out = w;}

   public void run() {
      for (char c = 'A'; c <= 'F'; c++)
          try {
            System.out.println("scrivi " + c);
            out.write(c);
            sleep(1000);
         } catch (IOException e) {}
           catch(InterruptedException e) {System.out.println(e.getMessage());}
   }
}

class Receiver extends Thread {
   private Reader in;

   public Receiver (Reader r)
   		{in = r;}

   public void run() {
      try {
         while (true) {
            sleep(1000);
            System.out.println("leggi " + (char)in.read());
         }
      } catch(IOException e) {System.out.println("fine input");
      						  //System.out.println(e.getMessage());
      						  }
        catch(InterruptedException e) {System.out.println(e.getMessage());}
   }
}

class PipeTest {
   public static void main(String[] args) {
      PipedWriter out = new PipedWriter();
      PipedReader in = null;
      try {
         in = new PipedReader(out);
      } catch(IOException e) {System.out.println(e.getMessage());}
      new Sender(out).start();
      new Receiver(in).start();
   }
}