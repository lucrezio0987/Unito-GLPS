package org.prog3.lab.week2.es2.v2.model;

/**
 * Implements a square polygon
 */
public class Square extends Polygon {
    private double side;

    /**
     * Builds a new square
     * @param side of the new square
     */
    public Square(double side) {
        super(4);
        this.side = side;
    }

    /**
     * Empty constructor. The values of the attributes should be set using
     * setter and getter methods.
     */
    public Square() {
        super(4);
    }


    /**
     * @return the side length of the square
     */
    public double getSide() {
        return side;
    }

    /**
     * Setter for the side of the square
     * @param side value to be set.
     */
    public void setSide(double side) {
        this.side = side;
    }


    @Override
    public double getArea() {
        return this.side*this.side;
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