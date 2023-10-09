interface Poligono {
	public double getArea();
}

class Triangolo implements Poligono {
	private double base;
	private double altezza;
	public Triangolo(double base,
		double altezza) {
		this.base = base;
		this.altezza = altezza;
	}
	public double getArea() {
		return base * altezza / 2;
	}
}

class Rettangolo implements Poligono {
	private double base;
	private double altezza;
	public Rettangolo(double base, double altezza) {
		this.base = base;
		this.altezza = altezza;
	}
	public double getArea() {
		return base * altezza;
	}
}

class Prisma {
	private Poligono base;
	private double altezza;
	public Prisma(Poligono base, double altezza) {
		this.base = base;
		this.altezza = altezza;
	}
	public double getVolume() {
		return base.getArea()*altezza;
	}
}


public class EreditarietaAdvanced {

   public static void main(String args[]) {
   	Prisma p1 = new Prisma(new Triangolo(2, 3), 4);
   	System.out.println("volume del prisma: " + p1.getVolume());
   	Prisma p2 = new Prisma(new Rettangolo(2, 3), 4);
   	System.out.println("volume del prisma: " + p2.getVolume());
   }
}
