package model;
abstract class Polygon {
    int base;
    int altezza;
    int num_vertici;

    public int getNum_vertici(){
        return num_vertici;
    }

    public abstract double getArea();

    public abstract boolean equals(Object pol_2);

    @Override
    public String toString() {
        return "Polygon{" +
                "base=" + base +
                ", altezza=" + altezza +
                ", num_vertici=" + num_vertici +
                '}';
    }

    public String[] describeAttributs() {
        return new String[]{ "base" , "altezza"};
    }

}
