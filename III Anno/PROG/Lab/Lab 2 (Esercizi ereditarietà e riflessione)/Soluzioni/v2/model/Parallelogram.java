package org.prog3.lab.week2.es2.v2.model;

/**
 * Implements a Parallelogram as a Polygon subclass.
 */
public class Parallelogram extends Polygon {
    private double base;
    private double height;

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

    /**
     * @return the base of the parallelogram
     */
    public double getBase() {
        return base;
    }

    /**
     * Setter for the base of the parallelogram
     * @param base of the parallelogram
     */
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * @return the height of the parallelogram
     */
    public double getHeight() {
        return height;
    }

    /**
     * Setter for the height of the parallelogram
     * @param height of the parallelogram
     */
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getArea() {
        return base * height;
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