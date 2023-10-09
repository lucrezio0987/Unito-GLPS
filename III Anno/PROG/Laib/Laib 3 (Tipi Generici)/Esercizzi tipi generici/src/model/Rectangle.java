package model;


/**
 * Implements a Rectangle. A rectangle is just a parallelogram with all right angles.
 * In addition to the methods in Parallelogram, Rectangles also implement the getPerimeter method.
 */
public class Rectangle extends Parallelogram {
    public Rectangle(double base, double height) {
        super(base, height);
    }
    public Rectangle() {
        super();
    }

    /**
     * @return the perimeter of the rectangle
     */
    public double getPerimeter() {
        return 2*(base + height);
    }
}
