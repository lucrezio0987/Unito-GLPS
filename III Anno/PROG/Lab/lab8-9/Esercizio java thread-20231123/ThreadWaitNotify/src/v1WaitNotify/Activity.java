package v1WaitNotify;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Una activity è una unità di esecuzione a sé stante. In questo semplice esempio tutte le attività
 * fanno la stessa cosa (per maggiori informazioni, vedere documentazione del methodo `run`)
  */

public class Activity extends Thread {
    private int[] waitFor;
    private int activityNumber;
    private int secondsToWait;
    private ConditionManager manager;
    private Semaphore sem;

    /**
     * Costruttore della classe.
     *
     * @param    activityNumber  numero assegnato alla attività
     * @param    secondsToWait   numero di secondi che dovrà attendere il thread prima di terminare
     * @param    preconditionsIndexes   lista degli indici delle condizioni da cui l'attività dipende
     * @param    manager gestore delle condizioni
     */

    public Activity(int activityNumber, int secondsToWait, int[] preconditionsIndexes, ConditionManager manager) {
        int[] clonedIndexes = new int[preconditionsIndexes.length];
        for (int i=0; i< clonedIndexes.length; i++) {
            clonedIndexes[i] = preconditionsIndexes[i];
        }
        this.waitFor = clonedIndexes;
        this.activityNumber = activityNumber;
        this.secondsToWait = secondsToWait;
        this.manager = manager;
    }

     /**
      * Esegue le azioni previste da questa attività. In questa semplice implementazione:
      *  - stampa a video un messaggio per segnalare che si sta mettendo in attesa della terminazione dei thread
      *     da cui dipende l'attività corrente
      *  - attende che i thread da cui dipende l'attività corrente terminino
      *  - segnala che l'esecuzione inizia
      *  - si mette in attesa per il numero di secondi specificato al momento della costruzione dell'oggetto
      *  - segnala il termine dell'attività e risveglia gli altri thread
      */

    @Override
    public void run() {
        if (sem!=null) {
            try {
                sem.acquire();
                work();
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
            sem.release();
        }
        else work();
    }

    private void work() {
        manager.checkPreconditions(waitFor);
        System.out.println("Activity " + activityNumber + " running:");
        try {
            Thread.sleep(secondsToWait * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Activity " + activityNumber + " ended.");
        manager.setCondition(activityNumber, true);
    }

    public void setSemaphore(Semaphore s) {
        sem = s;
    }
}
