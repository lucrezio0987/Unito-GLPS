package v1WaitNotify;

public class ConditionManager {
    private boolean[] conditions;
    public ConditionManager(int n) {
        conditions = new boolean[n];
            // set all conditions to false (nothing has happened yet)
        for (int i=0; i<conditions.length; i++) {
            conditions[i] = false;
        }
    }

    public synchronized void checkPreconditions(int[] indexes) {
        while (!check(indexes)) {
            try {
                wait();
            } catch (InterruptedException e)
            {System.out.println("Problem!!! " + e.getMessage());}
        }
    }
    /**
     * controlla un insieme di precondizioni (in AND).
     *
     * @param    indexes  lista degli indici delle condizioni da verificare
     * restituisce true se tutte le condizioni da verificare sono true,
     * false altrimenti
     */
    private boolean check(int[] indexes) {
        boolean result = true;
        for (int i=0; result && i<indexes.length; i++) {
            int k = indexes[i];
            if (k >= 0 && k < conditions.length)
                result = result && conditions[k];
        }
        return result;
    }
    public synchronized void setCondition(int k, boolean b) {
        if (k>=0 && k<conditions.length)
            conditions[k] = b;
        notifyAll();
    }
}
