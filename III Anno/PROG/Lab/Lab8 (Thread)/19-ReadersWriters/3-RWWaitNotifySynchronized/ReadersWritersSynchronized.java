class ReadersWritersSynchronized {
	public static void main(String[] args) {
		int numReaders = 3;
      	int numWriters = 2;

      	ReadWriteCoord rw = new ReadWriteCoord();

      	for (int i = 0; i < numReaders; i++)
         	new Reader(i, rw);

        /* se posticipo la creaz dei writer i reader leggono lo stesso...
		try {
				Thread.sleep((int)(Math.random() * 4000));
            }  catch(InterruptedException e) {}
		*/
      	for (int i = 0; i < numWriters; i++)
         	new Writer(numReaders + i, rw);
   	}
}

/* NB: READER E WRITER SI COORDINANO PRENDENDO L'ELEMENTO DI SINCRONIZZAZIONE
   ReadWriteCoord. PERO' ANCHE QUI POTREBBE DARSI CHE UN LETTORE LEGGA PRIMA
   CHE UNO SCRITTORE SCRIVA PERCHE' LA WAIT E' SOLO SUBORDINATA AL NON AVERE WRITER ATTIVI.
   */

class Reader implements Runnable {
	private int id;
   	private ReadWriteCoord rw;

   	public Reader(int i, ReadWriteCoord c) {
		id = i;
      	rw = c;
      	new Thread(this).start();
   	}

   	public void run() {
		for(int i=0; i < 5; i++) {
			try {
				Thread.sleep((int)(Math.random() * 1000));
            }  catch(InterruptedException e) {}
         	rw.startRead(id);
         	// lettura
         	try {
				Thread.sleep((int)(Math.random() * 1000));
            }  catch(InterruptedException e) {}
         	rw.endRead(id);
      	}
   }
}

class Writer implements Runnable {
	private int id;
   private ReadWriteCoord rw;

   public Writer(int i, ReadWriteCoord c) {
	  id = i;
      rw = c;
      new Thread(this).start();
   }

   public void run() {
	   for(int i=0; i < 5; i++) {
		   try {
			   Thread.sleep((int)(Math.random() * 1000));
            }  catch(InterruptedException e) {}
         	rw.startWrite(id);
         	// scrittura
         	try {
				Thread.sleep((int)(Math.random() * 1000));
            }  catch(InterruptedException e) {}
         	rw.endWrite(id);
       }
   }
}

class ReadWriteCoord {
	private int numReaders = 0;
   	private int numWriters = 0;

// quando un lettore inizia a leggere, se c'è almeno uno scrittore attivo, si sospende
   	public synchronized void startRead(int i) {
		while(numWriters > 0)
         	try{
				wait();
			}
         	catch(InterruptedException e){}
      	numReaders++;
      	System.out.println("Start reading " + i + "; ci sono " + numReaders + " lettori attivi");
  	}

// quando un lettore finisce di leggere, se non ci sono altri lettori attivi, notifica i thread in stato di wait
   public synchronized void endRead(int i) {
	   numReaders--;
      	System.out.println("End reading " + i + "; ci sono " + numReaders + " lettori attivi");
      	if(numReaders == 0)
      		notifyAll();
   }

// quando uno scrittore inizia a scrivere, se c'è almeno uno scrittore o un lettore
// attivo, si sospende
   public synchronized void startWrite(int i) {
	   while(numReaders > 0 || numWriters > 0)
            try{
				wait();
			} catch(InterruptedException e){}
       numWriters++;
       System.out.println("Start writing " + i + "; ci sono " + numWriters + " scrittori attivi");
   }

// quando uno scrittore finisce di scrivere notifica i thread sospesi
   public synchronized void endWrite(int i) {
	   numWriters--;
       System.out.println("End writing " + i + "; ci sono " + numWriters + " scrittori attivi");
       notifyAll();
   }

}