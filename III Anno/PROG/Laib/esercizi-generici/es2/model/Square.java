package org.prog3.lab.week3.es2.model;

public class Square extends Polygon {

    private double side;

    public Square(double side) {
        super(4);
        this.side = side;
    }

    public Square() {
        super(4);
    }

    @Override
    public double getArea() {
        return this.side*this.side;
    }

    @Override
    public String[] describeAttributes() {
        return new String[]{"side"};
    }

    @Override
    public void setAttributes(double[] attributes) {
        this.side = attributes[0];
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            Square a = (Square) o;
            return this.side == a.side;
        }
        return false;
    }
}