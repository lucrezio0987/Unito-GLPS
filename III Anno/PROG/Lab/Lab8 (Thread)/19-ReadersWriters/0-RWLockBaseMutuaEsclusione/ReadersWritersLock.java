import java.util.concurrent.locks.*;

// Implementa i lettori-scrittori usando il
// Lock --> lettura e scrittura sono tutte mutuamente esclusive
// i lettori possono cominciare a leggere prima che uno scrittore scriva
// non ci sono controlli sul fatto che il database contenga o meno dei dati

class ReadersWritersLock {
	public static void main(String[] args) {
		int numReaders = 3;
      	int numWriters = 2;

      	Database db = new Database();

      	for (int i = 0; i < numReaders; i++)
         	new Reader(i, db);
        try {
			Thread.sleep(300);
		} catch (InterruptedException e) {};
      	for (int i = 0; i < numWriters; i++)
         	new Writer(numReaders + i, db);
   	}
}


class Database {
	private Object l1 = new Object();

   	public void read(int i) {
		synchronized(l1) {
      		System.out.println("Start reading - thread  " + i);
      		try {
				Thread.sleep((int)(Math.random() * 1000));
         	} catch(InterruptedException e) {}
		}
      	System.out.println("End reading - thread " + i);
   	}

   public void write(int i) {
		synchronized(l1) {
       		System.out.println("Start writing - thread " + i);
       		try {
		   		Thread.sleep((int)(Math.random() * 1000));
        	} catch(InterruptedException e) {}
		}
       	System.out.println("End writing - thread " + i);
   }

}


class Reader implements Runnable {
	private int id;
   	private Database db;

   	public Reader(int i, Database d) {
		id = i;
      	db = d;
      	new Thread(this).start();
   	}

   public void run() {
	   for(int i=0; i < 5; i++) {
		   try {
			   Thread.sleep((int)(Math.random() * 100));
           } catch(InterruptedException e) {}
         	db.read(id);
         	try {
				Thread.sleep((int)(Math.random() * 100));
            }  catch(InterruptedException e) {}
      }
   }
}



class Writer implements Runnable {
	private int id;
   	private Database db;

   	public Writer(int i, Database d) {
		id = i;
      	db = d;
      	new Thread(this).start();
   	}

   public void run() {
	   for(int i=0; i < 5; i++) {
		   try {
			   Thread.sleep((int)(Math.random() * 100));
            } catch(InterruptedException e) {}
           db.write(id);
           try {
			   Thread.sleep((int)(Math.random() * 100));
           }  catch(InterruptedException e) {}
      }
   }
}