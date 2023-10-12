package org.prog3.lab.week2.es2.v2.model;

import java.util.ArrayList;

/**
 * Container class for storing a set of polygons
 */
public class Geometries {
    private ArrayList<Polygon> elements;

    /**
     * Builds a new, empty, geometry container.
     */
    public Geometries() {
        this.elements = new ArrayList<>();
    }

    /**
     * @return the size of the container
     */
    public int getNElements() {
        return elements.size();
    }

    /**
     * Adds a new polygon to the container. It does not insert the polygon if it is already present
     * in the container (as implied by Object.equals method)
     * @param p the polygon to be added
     * @return true if the polygon has been added, false otherwise.
     */
    public boolean add(Polygon p) {
        for (Polygon current:this.elements) {
            if (current.equals(p))
                return false;
        }
        this.elements.add(p);
        return true;
	}

    /**
     * Print a description of the objects in the collection.
     */
    public void printDescription() {
        System.out.println("Geometries collection:");
        for (Polygon p:this.elements) {
            System.out.println("Polygon class: " + p.getClass() + " Area: " + p.getArea());
        }
    }

}









