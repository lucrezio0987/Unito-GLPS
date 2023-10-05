import io.PrettyPrinter;
import model.Book;

import java.util.AbstractList;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ArrayList<Book> bookList = new ArrayList<Book>();
        Book b1 = new Book("titolo1", new ArrayList<String>());
        Book b2 = new Book("titolo2", new ArrayList<String>());
        bookList.add(b1);
        bookList.add(b2);
        PrettyPrinter.printBooks(bookList);
    }
}