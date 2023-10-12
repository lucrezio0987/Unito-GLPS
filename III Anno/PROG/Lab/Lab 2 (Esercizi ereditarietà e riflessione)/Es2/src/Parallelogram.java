public class Parallelogram extends Polygon {
    public double getArea() {
        return super.base * super.altezza;
    }

    public boolean equals(Object pol_2) {
        if(this.getClass() == pol_2.getClass())
            return this.base == ((Parallelogram) pol_2).base && this.altezza == ((Parallelogram) pol_2).altezza;
        return false;
    }

}
