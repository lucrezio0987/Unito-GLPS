package org.prog3.lab.week3.es2.main;

import org.prog3.lab.week3.es2.model.*;

public class GeometriesApp {

    public static void main(String[] args) {
        Geometries<Rectangle> g = new Geometries<>();
        g.add(new Rectangle(5, 5));
        g.add(new Rectangle(3, 3));
        g.printAreas();
        //g.add(new Triangle(2, 3)); // produce l'errore 'incompatible types: Triangle cannot be converted to Rectangle'
        System.out.println("Numero di poligoni: " + g.getElementsNum());
        System.out.println();
        Geometries<Triangle> t = new Geometries<>();
        t.add(new Triangle(4, 5));
        t.add(new Triangle(5, 3));
        t.add(new Triangle(2, 2));
        t.add(new Triangle(4, 5)); // non viene aggiunto perche' gia' presente nella lista
        t.printAreas();
        System.out.println("Numero di poligoni: " + t.getElementsNum());
        //Geometries<String> gWrong = new Geometries(); // errore: il parametro di tipo deve estendere Polygon
        //g.add("ciao"); // produce l'errore 'incompatible types: String cannot be converted to Rectangle'
    }
}
