/* Se l'array passato come parametro ai thread deve essere modificato
in mutua esclusione bisogna proteggere la risorsa con un lock, se no,
come si vede in questo esempio, le modifiche potrebbero essere fatte
in modo inconsistente.
*/


class MyThread extends Thread {
	private int delay;
	private int[] elenco;
	private int val;

    public MyThread(String str, int d, int[] elenco, int val) {
        super(str);
		delay = d;
		this.elenco = elenco;
		this.val = val;
    }

    public void run() {
        for (int i = 0; i < elenco.length; i++) {
            try {
               sleep(delay);
               elenco[i] = val;
            } catch (InterruptedException e) {return;}
        }
        System.out.println("DONE! " + getName());
    }
}

public class Interferenza0 {

    public static void main (String[] args) {
		int[] numeri = {0,0,0,0,0,0,0};
        Thread t1 = new MyThread("UNO", 300, numeri, 20);
        t1.start();
        Thread t2 = new MyThread("DUE", 300, numeri, 30);
		t2.start();
        try {
        	t1.join();
		} catch (InterruptedException e)
			{System.out.println(e.getMessage());}
        try {
        	t2.join();
		} catch (InterruptedException e)
			{System.out.println(e.getMessage());}
        for (int i=0; i<numeri.length; i++) {
			System.out.println(numeri[i]);
		}
    }
}