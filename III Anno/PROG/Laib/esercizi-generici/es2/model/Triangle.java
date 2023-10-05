package org.prog3.lab.week3.es2.model;

public class Triangle extends Polygon {
    private double base;
    private double height;

    public Triangle(double base, double height) { //costruttore
        super(3);
        this.base = base;
        this.height = height;
    }

    public Triangle() {
        super(3);
    }

    @Override
    public double getArea() {
        return base * height / 2;
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
            Triangle a = (Triangle) o;
            return this.base == a.base && this.height == a.height;
        }
        return false;
    }
}