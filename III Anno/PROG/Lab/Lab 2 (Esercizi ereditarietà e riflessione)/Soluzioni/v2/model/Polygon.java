package org.prog3.lab.week2.es2.v2.model;

public abstract class Polygon {

    private final int numVertices;

    /**
     * Create a new Polygon
     * @param numVertices Number of vertices of the polygon
     */
    public Polygon(int numVertices) {
        this.numVertices = numVertices;
    }

    /**
     * @return Return the number of vertices of a Polygon (read-only property)
     */
    public int getNumVertices() {
        return numVertices;
    }

    /**
     * @return the area of a Polygon. This is an abstract method, each subclass will implement
     * their own definition of area.
     */
    public abstract double getArea();

    @Override
    public String toString() {
        return " - " + numVertices + " - ";
    }

    /**
     * Definition of equality between two polygons. Two Polygons are equal if they have the same number of vertices
     * @param o the object to compare to
     * @return true if two polygons are equals, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o != null && this.getClass() == o.getClass()) {
            Polygon a = (Polygon) o;
            return this.numVertices == a.numVertices;
        }
        return false;
    }

}
