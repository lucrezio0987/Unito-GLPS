public class EredMetodi {
	public static void main(String[] args) {
		B b = new B();
		b.stampa(); // stampa "B"
		A a = new A();
		a.stampa();
	}
}

class A {
	void stampaTipo() {System.out.println("A");}
	void stampa() {stampaTipo();}
}

class B extends A {
	void stampaTipo() {
		System.out.println("B");
	}
}


