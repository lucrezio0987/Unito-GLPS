public class Triangle extends Polygon {
    public double getArea() {
        return ((super.base * super.altezza) / 2.0);
    }

    public boolean equals(Object pol_2) {
        if(this.getClass() == pol_2.getClass())
            return this.base == ((Triangle) pol_2).base && this.altezza == ((Triangle) pol_2).altezza;
        return false;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Triangle{}";
    }
}
