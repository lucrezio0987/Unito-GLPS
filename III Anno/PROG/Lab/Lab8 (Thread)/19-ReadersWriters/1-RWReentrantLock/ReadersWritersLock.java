import java.util.concurrent.locks.*;

// Implementa i lettori-scrittori usando il
// ReadWriteLock

class ReadersWritersLock {
	public static void main(String[] args) {
		int numReaders = 3;
      	int numWriters = 2;

      	Database db = new Database();

      	for (int i = 0; i < numReaders; i++)
         	new Reader(i, db);
       	 try {
 			Thread.sleep(300);
          } catch(InterruptedException e) {}
      	for (int i = 0; i < numWriters; i++)
         	new Writer(numReaders + i, db);
   	}
}

/* NB: SE SI USANO I READER/WRITER LOCKS POSSONO LEGGERE
   IN PARALLELO PIU' READERS, SE UN WRITER SCRIVE E' IN MUTUA
   ESCLUSIONE TOTALE, MA UN LETTORE PUO' INIZIARE A LEGGERE
   PRIMA CHE ALCUNO SCRITTORE SCRIVA. DIRE CHE I LETTORI ACCEDONO
   IN MUTUA ESCLUSIONE RISPETTO AGLI SCRITTORI, E CHE OGNI SCRITTORE
   OPERA IN MUTUA ESCLUSIONE TOTALE NON BASTA...
*/



class Database {
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
   	private Lock rl = rwl.readLock();
   	private Lock wl = rwl.writeLock();

   	public void read(int i) {
		rl.lock();
      	System.out.println("Start reading - thread  " + i);
      	try {
			Thread.sleep((int)(Math.random() * 1000));
         } catch(InterruptedException e) {}
      	System.out.println("End reading - thread " + i);
      	rl.unlock();
   	}

   public void write(int i) {
	   wl.lock();
       System.out.println("Start writing - thread " + i);
       try {
		   Thread.sleep((int)(Math.random() * 1000));
        } catch(InterruptedException e) {}
       System.out.println("End writing - thread " + i);
       wl.unlock();
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