public class MultipleInterfaceApp {

   public static void main(String args[]) {
	  A a = new C();//l'oggetto e' visto come un A
      B b = new C();//l'oggetto e' visto come un B
      a.metodoA(); // stampa A
      b.metodoB(); // stampa B
      //a.metodoB(); //errore: A non ha il metodB
      ((C)a).metodoB(); // facendo downcasting la compilazione ha successo
                        // stampa B

   }
}


interface A {
	 //public final static int v = 1;
	 void metodoA();
	 /* public static int raddoppia(int x) {
		 return 2*x;
	 }  */
}

interface B {
	 void metodoB();
}

class C implements A, B {
	public void metodoA() {
		System.out.println("A");
	}
	public void metodoB() {
		System.out.println("B");
	}
}

