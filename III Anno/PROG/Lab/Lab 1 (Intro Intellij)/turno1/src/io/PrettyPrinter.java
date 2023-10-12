package io;

import model.Book;
import java.util.ArrayList;

public class PrettyPrinter {
    public static void printBooks(ArrayList<Book> bookList) {
        for (Book book : bookList) {
            System.out.println("------");
            System.out.println(book);
            System.out.println("------");
        }
    }
}
