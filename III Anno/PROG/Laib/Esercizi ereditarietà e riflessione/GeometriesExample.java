package org.prog3.lab.week2.es2.v1.main;

import org.prog3.lab.week2.es2.v1.model.*;

import java.util.Scanner;

/**
 * Main program class. The program presents to the user an interactive menu allowing
 * him/her to input some polygons and to query the list of inserted polygons.
 */
public class GeometriesExample {
    /**
     * @param args Not used.
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Geometries geometries = new Geometries();


        interactiveMenu(input, geometries);
    }

    /**
     * Presents to the user an interactive menu
     * @param input An input scanner pre initialized by the caller
     * @param geometries a Geometries' container to be filled with the user inputs
     */
    private static void interactiveMenu(Scanner input, Geometries geometries) {
        boolean mainLoop = true;
        int choice = -1;


        while(choice!=0) {

            System.out.println("Menu\n");
            System.out.print("1.) Add a Rectangle.\n");
            System.out.print("2.) Add a Triangle.\n");
            System.out.print("3.) Add a Square.\n");
            System.out.print("4.) Add a Parallelogram.\n");
            System.out.print("5.) List geometries.\n");
            System.out.print("6.) Number of geometries.\n");
            System.out.print("0.) Exit");
            System.out.print("\nEnter Your Menu Choice: ");

            choice = input.nextInt();
            Polygon p;

            switch (choice) {
                case 1 -> {
                    p = new Rectangle();
                    addPolygon(input, geometries, p);
                }
                case 2 -> {
                    p = new Triangle();
                    addPolygon(input, geometries, p);
                }
                case 3 -> {
                    p = new Square();
                    addPolygon(input, geometries, p);
                }
                case 4 -> {
                    p = new Parallelogram();
                    addPolygon(input, geometries, p);
                }
                case 5 -> {
                    geometries.printDescription();
                    System.out.println();
                }
                case 6 -> {
                    System.out.println("Number of geometries in the collection: " + geometries.getNElements());
                    System.out.println();
                }
                default -> {
                }
            }
        }
    }

    /**
     * Asks to the user the relevant values to fill out the properties of the given polygon and
     * updates the polygon with the given data.
     * @param input the input scanner to be used for the input of the values
     * @param geometries the container where the polygon has to be inserted
     * @param p the polygon to be updated. It will be a concrete subclass of Polygon.
     */
    private static void addPolygon(Scanner input, Geometries geometries, Polygon p) {
        String[] attributesNames = p.describeAttributes();
        double[] attributesValues = new double[attributesNames.length];
        int i = 0;
        for (String attr: attributesNames) {
            System.out.println("Enter "+attr+" (double):");
            double current = input.nextDouble();
            attributesValues[i] = current;
            i++;
        }
        p.setAttributes(attributesValues);
        geometries.add(p);
        System.out.println("Geometry "+p.getClass().getName()+" added.\n");
    }


}
