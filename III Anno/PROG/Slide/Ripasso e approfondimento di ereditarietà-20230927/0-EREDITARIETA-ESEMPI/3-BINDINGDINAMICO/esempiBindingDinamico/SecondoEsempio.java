public class SecondoEsempio {
    public static void main(String args[]) {
        P p = new P();
		P q = new Q();
        p.m(q); 				// Stampa P
        q.m(p);					// Stampa Q
        System.out.println("");

        P pq = new Q();
        Q qq = new Q();
        pq.m(qq); 		// stampa Q (binding dinamico sul metodo ereditato)
        qq.m(qq);		// stampa altro Q
    }
}


class P {
    public void m(P a) {
        System.out.println("P");
    }
    public void m(Q a) {
	        System.out.println("XX");
    }
}

class Q extends P {
    public void m(P a) {
        System.out.println("Q");
    }
    /*public void m(Q b) {
        System.out.println("altro Q");
    }*/
}

