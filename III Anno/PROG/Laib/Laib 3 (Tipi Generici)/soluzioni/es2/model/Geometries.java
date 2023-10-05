package org.prog3.lab.week3.es2.model;

import java.util.*;

public class Geometries<T extends Polygon> {
    private List<T> elements;

    public Geometries() {
        this.elements = new ArrayList<T>();
    }

    /**
     * Ritorna il numero di elementi inseriti nella lista di poligoni.
     */
    public int getElementsNum() {
        return elements.size();
    }

    /**
     * Aggiunge un nuovo elemento di tipo T alla lista, solo se tale
     * elemento non e' gia' presente al suo interno.
     *
     * @param el    elemento che si vuole aggiungere alla lista
     * @return      true se l'elemento e' stato aggiunto, false altrimenti.
     */
    public boolean add(T el) {
        if (elements.contains(el)) {
			return false;
		}
		else {
			elements.add(el);
			return true;
		}
	}

	/**
     * Stampa il valore dell'area di ciascun elemento della lista,
     * sfruttando le implementazioni concrete del metodo getArea(),
     * ereditato dalle sottoclassi di Polygon.
     */
    public void printAreas() {
        for (T el : elements) {
            System.out.println("Area: " + el.getArea());
        }
    }
}

