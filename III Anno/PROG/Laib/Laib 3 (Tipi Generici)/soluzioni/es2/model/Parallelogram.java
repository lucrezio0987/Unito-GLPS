package org.prog3.lab.week3.es2.model;

public class Parallelogram extends Polygon {
    private double base;
    private double height;

    public Parallelogram(double base, double height) { //costruttore
        super(4);
        this.base = base;
        this.height = height;
    }

    public Parallelogram() {
        super(4);
    }

    @Override
    public double getArea() {
        return base * height;
    }

    @Override
    public String[] describeAttributes() {
        return new String[]{"base","height"};
    }

    @Override
    public void setAttributes(double[] attributes) {
        this.base = attributes[0];
        this.height = attributes[1];
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            Parallelogram a = (Parallelogram) o;
            return this.base == a.base && this.height == a.height;
        }
        return false;
    }
}