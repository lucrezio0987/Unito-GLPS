package org.prog3.lab.week3.es2.model;

public class Rectangle extends Polygon {
    private double base;
    private double height;

    public Rectangle(double base, double height) { //costruttore
        super(4);
        this.base = base;
        this.height = height;
    }

    public Rectangle() {
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
            Rectangle a = (Rectangle) o;
            return this.base == a.base && this.height == a.height;
        }
        return false;
    }



    public double getPerimeter() {
        return 2*(base + height);
    }
}
