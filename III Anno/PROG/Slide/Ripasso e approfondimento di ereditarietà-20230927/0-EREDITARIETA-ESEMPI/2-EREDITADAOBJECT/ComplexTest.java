
public class ComplexTest {

    private double re, im;

    public ComplexTest(double re, double im) {
        this.re = re;
        this.im = im;
    }

	public String toString() {
		return this.re + " " + this.im;
	}

    public static void main(String args[]) {
        ComplexTest c = new ComplexTest(1.5, 2.4);
        System.out.println(c);
}

}

/*
class Prova extends ComplexTest {
  private int t;

public Prova(double re, double im) {
        super(re, im);
        t = 10*getRe();
    }
}
*/