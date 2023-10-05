package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class Queue<T> {
    LinkedList<T> array;

    public Queue() {
        this.array = new LinkedList<>();
    }

    public void enqueue(T el) {
        array.addLast(el);
    }

    public T dequeue() {
        return array.removeLast();
    }
    public boolean empty() {
        return array.isEmpty();
    }


}
