package org.prog3.lab.week2.es2.v1.model;

/**
 * Implements a Parallelogram as a Polygon subclass.
 */
public class Parallelogram extends Polygon {
    protected double base;
    protected double height;

    /**
     * Builds a new parallelogram given its base and its height
     * @param base of the parallelogram
     * @param height of the parallelogram
     */
    public Parallelogram(double base, double height) { //costruttore
        super(4);
        this.base = base;
        this.height = height;
    }

    /**
     * Empty constructor. Base and height should be set using the setAttributes method.
     */
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