package org.prog3.lab.week2.es2.v2.main;

import org.prog3.lab.week2.es2.v2.model.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
     * @param geometries a Geometries container to be filled with the user inputs
     */
    private static void interactiveMenu(Scanner input, Geometries geometries) {
        int choice = -1;

        while( choice != 0 ) {
            showOptions();

            choice = input.nextInt();
            switch (choice) {
                case 1 -> {
                    Polygon p = new Rectangle();
                    addPolygon(input, geometries, p);
                }
                case 2 -> {
                    Polygon p = new Triangle();
                    addPolygon(input, geometries, p);
                }
                case 3 -> {
                    Polygon p = new Square();
                    addPolygon(input, geometries, p);
                }
                case 4 -> {
                    Polygon p = new Parallelogram();
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
            }
        }
    }

    /**
     * Outputs to the screen the list of possible choices that the user can take.
     */
    private static void showOptions() {
        System.out.println("Menu\n");
        System.out.print("1.) Add a Rectangle.\n");
        System.out.print("2.) Add a Triangle.\n");
        System.out.print("3.) Add a Square.\n");
        System.out.print("4.) Add a Parallelogram.\n");
        System.out.print("5.) List geometries.\n");
        System.out.print("6.) Number of geometries.\n");
        System.out.println("0.) Exit");
        System.out.print("\nEnter Your Menu Choice: ");
    }

    /**
     * Change the case of the first letter of the string to upcase
     * @param value is the string to be changed
     * @return a string containing exactly the same characters as those in `value` with the
     *  exception that the first letter is upcase.
     */
    private static String upperCaseFirst(String value) {
        char[] array = value.toCharArray();
        array[0] = Character.toUpperCase(array[0]);
        return new String(array);
    }

    /**
     * Asks to the user the relevant values to fill out the properties of the given polygon and
     * updates the polygon with the given data.
     * @param input the input scanner to be used for the input of the values
     * @param geometries the container where the polygon has to be inserted
     * @param p the polygon to be updated. It will be a concrete subclass of Polygon.
     */
    private static void addPolygon(Scanner input, Geometries geometries, Polygon p) {
        Field[] attributes = p.getClass().getDeclaredFields();

        System.out.println(attributes.length);

        for( Field field: attributes ) {
            System.out.println("Enter "+field.getName()+" (double)");
            double param = input.nextDouble();

            try {
                invokeSetter(p, field, param);
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        geometries.add(p);
        System.out.println("Geometry "+p.getClass().getName()+" added.\n");
    }

    /**
     * Sets field 'field' of object p with value 'param' by invoking the corresponding setter method through
     * the java reflection API.
     * @param p the object to be modified
     * @param field the field to be set
     * @param param the value for the field
     * @throws NoSuchMethodException see java.reflect.Method.invoke
     * @throws IllegalAccessException see java.reflect.Method.invoke
     * @throws InvocationTargetException see java.reflect.Method.invoke
     */
    private static void invokeSetter(Polygon p, Field field, double param) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        var setter = p.getClass().getMethod("set"+upperCaseFirst(field.getName()), double.class);
        setter.invoke(p, param);
    }
}
