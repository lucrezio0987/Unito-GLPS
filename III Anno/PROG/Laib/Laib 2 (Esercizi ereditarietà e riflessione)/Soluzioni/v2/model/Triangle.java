package org.prog3.lab.week2.es2.v2.model;

public class Triangle extends Polygon {
    private double base;
    private double height;

    /**
     * Builds a new triangle
     * @param base of the triangle
     * @param height of the triangle
     */
    public Triangle(double base, double height) { //costruttore
        super(3);
        this.base = base;
        this.height = height;
    }
    /**
     * Empty constructor. The values of the attributes should be set using
     * setter and getter methods.
     */
    public Triangle() {
        super(3);
    }


    /**
     * @return the base of the triangle
     */
    public double getBase() {
        return base;
    }

    /**
     * Sets the base of the triangle to the given value
     * @param base new value for the base of the triangle
     */
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * @return the height of the triangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the triangle
     * @param height new value for the height of the triangle
     */
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getArea() {
        return base * height / 2;
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