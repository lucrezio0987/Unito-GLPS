import Model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    /**
     * prima versione del main, triplica le operazioni per trattare i diversi tipi di dato
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Element<Integer>> interi = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            System.out.println("Inserisci un numero intero:");
            int n = 0;
            try{
                n = sc.nextInt();
            } catch (Exception e) {sc.next(); //toglie il carattere "a capo"
                System.out.println("Input errato, fisso il numero a 0.");}
            interi.add(new Element.classInt(n));
        }
        Calculator.print(interi);
        try {
            System.out.println("Max: " + Calculator.max(interi) +
                    "; Tot: " + Calculator.sum(interi));
        } catch (Exception e) {System.out.println("Errore: " + e.getMessage());}
        System.out.println("fatto");

        ArrayList<Element<String>> stringhe = new ArrayList<>();
        for (int i=0; i<3; i++) {
            System.out.println("Inserisci una stringa di caratteri:");
            stringhe.add(new Element.classString(sc.next()));
        }
        Calculator.print(stringhe);
        System.out.println("Max: " + Calculator.max(stringhe) +
                "; Tot: " + Calculator.sum(stringhe));
        System.out.println();
    }
}