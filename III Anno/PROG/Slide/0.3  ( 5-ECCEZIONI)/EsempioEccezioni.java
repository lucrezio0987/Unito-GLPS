import java.util.*;

class Ecc1 extends RuntimeException {} // eccezione non controllata
class Ecc2 extends RuntimeException {} // eccezione non controllata
class Ecc3 extends Exception {}		   // eccezione controllata

public class EsempioEccezioni {
    public static void p(int i)  throws Ecc1, Ecc2, Ecc3 {
        System.out.println("istr0");
        try {
            if (i == 0) throw new Ecc1();
            i--;
            System.out.println("istr1");
            if (i == 0) throw new Ecc2();
            i--;
            System.out.println("istr2");
            if (i == 0) throw new Ecc3();
            i--;
            System.out.println("istr3");
        } catch(Ecc2 e) {
            System.out.println("istr4");
        }
            System.out.println("istr5");
   }

public static void main(String[] args) throws Ecc3 {
		Scanner sc = new Scanner(System.in);
		System.out.println("Immetti un valore intero [0..3]: ");
        int i = sc.nextInt();
        System.out.println("istr6");
        try {
            p(i);
        } catch(Ecc1 e) {
            System.out.println("istr7");
        } catch(Ecc2 e) {
            System.out.println("istr8");
        }
        System.out.println("istr9");
    }
}