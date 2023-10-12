package org.prog3.lab.week2.es2.v2.model;

/**
 * Implements a Rectangle.
 *
 * Note: this class cannot inherit from Parallelogram since the method used to query the attributes
 * in the main program does not search in the parent classes. This is clearly a sub-optimal design,
 * we do not tackle the problem for simplicity.
 */
public class Rectangle extends Polygon {
    private double base;
    private double height;

    /**
     * Builds a new rectangle
     * @param base of the new rectangle
     * @param height of the new rectangle
     */
    public Rectangle(double base, double height) { //costruttore
        super(4);
        this.base = base;
        this.height = height;
    }

    /**
     * Empty constructor. The values of the attributes should be set using
     * setter and getter methods.
     */
    public Rectangle() {
        super(4);
    }
    
    /**
     * @return the base of the rectangle
     */
    public double getBase() {
        return base;
    }

    /**
     * Setter for the base of the rectangle
     * @param base of the parallelogram
     */
    public void setBase(double base) {
        this.base = base;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {
        return height;
    }

    /**
     * Setter for the height of the rectangle
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
            Rectangle a = (Rectangle) o;
            return this.base == a.base && this.height == a.height;
        }
        return false;
    }


    /**
     * @return the perimeter of the rectangle.
     */
    public double getPerimeter() {
        return 2*(base + height);
    }
}
