package model;


/**
 * Implements a Square as a Polygon subclass.
 */
public class Square extends Polygon {
    private double side;

    /**
     * Builds a new square
     * @param side the length of the side of the square to be built
     */
    public Square(double side) {
        super(4);
        this.side = side;
    }

    /**
     * Empty constructor. The side should be set using the setAttributes method.
     */
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