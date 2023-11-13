import java.util.*;

class EsempioT extends Thread {
	int counter = 0;
   	public void run() {
		try {
             sleep(400);
        } catch (InterruptedException e)
        		{System.out.println(e.getMessage());}
      	incr();
      	stampaTuttiStack();
      	return;
   	}

   public void incr() {
   		counter++;
      	return;
   }


   public static void stampaTuttiStack() {
	   Map<Thread,StackTraceElement[]> stack = Thread.getAllStackTraces();
      	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	Set<Thread> keys = stack.keySet();
      	for (Thread key : keys) {
			System.out.println("\n" + key.getName() + ": ");
         	stampaStack(stack.get(key));
     	}
      	System.out.println("FINE STACK");
      	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
      	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%% \n");
   }

   private static void stampaStack(StackTraceElement[] frames) {
      for (StackTraceElement f : frames)
         System.out.println(f);
   }

   public static void main(String[] args) {
	   	EsempioT t1 = new EsempioT();
      	EsempioT t2 = new EsempioT();
      	t1.start();
      	t2.start();
      	try {
      		Thread.sleep(200);
		} catch (InterruptedException e)
				{System.out.println("Thread interrotto: " + e.getMessage());}
      	stampaTuttiStack();
   }

}