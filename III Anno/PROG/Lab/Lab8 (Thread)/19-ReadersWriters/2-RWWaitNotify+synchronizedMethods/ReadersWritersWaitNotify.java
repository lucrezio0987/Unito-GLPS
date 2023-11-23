public class ReadersWritersWaitNotify {

	public static void main(String[] args) {
		int numReaders = 3;
      	int numWriters = 2;

      	Database db = new Database();

      	for (int i = 0; i < numReaders; i++)
         	new Reader(i, db);
      	for (int i = 0; i < numWriters; i++)
         	new Writer(numReaders + i, db);
   }
}


/*
   Spezziamo read in due parti per permettere a più thread di lavorare in parallelo.
   La write no perché deve essere eseguita in mutua esclusione.
   */

class Database {
	private int numReaders = 0;

	public synchronized void startRead(int i) {
		numReaders++;
		System.out.println("Start reading " + i + "; ci sono " + numReaders + " lettori attivi");
	}

// quando l'ultimo lettore attivo finisce di leggere notifica i thread in stato di wait (scrittori).
	public synchronized void endRead(int i) {
		numReaders--;
		System.out.println("End reading " + i + "; ci sono " + numReaders + " lettori attivi");
		if(numReaders == 0)
				/* NB: fondamentale fare notifyAll e non notify per prevenire deadlock! */
			notifyAll();
	}


// quando uno scrittore vede che ci sono lettori attivi si mette in stato di wait.
// quando ha finito di scrivere notifica i thread in stato di wait (scrittori).
// L'operazione di scrittura è gestita in sezione critica
	public synchronized void write(int i) {
		while(numReaders > 0)
			try{
				wait();
			} catch(InterruptedException e){}
		System.out.println("Start writing " + i);
		try {
			Thread.sleep((int)(Math.random() * 1000));
			 } catch(InterruptedException e) {}
		System.out.println("End writing " + i);
				/* NB: fondamentale fare notifyAll e non notify per prevenire deadlock! */
		notifyAll();
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
		for(int i=0; i<5; i++) {
			try {
				Thread.sleep((int)(Math.random() * 100));
			} catch(InterruptedException e) {}
			db.startRead(id);
			try
				{Thread.sleep((int)(Math.random() * 100));
			 	}	catch(InterruptedException e) {}
			db.endRead(id);
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
		for(int i=0; i<5; i++) {
		try {
			Thread.sleep((int)(Math.random() * 100));
			 } catch(InterruptedException e) {}
		db.write(id);
		}
	}
}