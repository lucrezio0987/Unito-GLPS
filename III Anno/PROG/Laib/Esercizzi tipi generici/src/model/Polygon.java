package model;

/**
 * Base abstract class for every Polygon.
 */
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
     * @return the area of a Polygon. This is an abstract method, each subclass should implement
     * their own definition of area.
     */
    public abstract double getArea();

    /**
     * @return an array containing the names of the fields in this polygon. For instance:
     * a Rectangle would return ['base', 'height']
     */
    public abstract String[] describeAttributes();

    /**
     * Sets the attributes of this polygon.
     * @param attributes values to be set in the order given by the describeAttributes method. For instance,
     *                   assuming that describeAttributes returned ['base', 'height'], a Rectangle would expect
     *                   the values for the 'base' and 'height' in this order.
     */
    public abstract void setAttributes(double[] attributes);

    /**
     * @return a textual description of this Polygon.
     */
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
