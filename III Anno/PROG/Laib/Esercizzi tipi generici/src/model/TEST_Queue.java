package model;

public class TEST_Queue {
    public static void main(String[] args) {
        Queue<Integer> Test_Queue_Integer = new Queue<>();
        Queue<Double> Test_Queue_Double = new Queue<>();

        Test_Queue_Integer.enqueue(1);
        Test_Queue_Integer.enqueue(2);
        Test_Queue_Integer.enqueue(3);
        Test_Queue_Integer.enqueue(4);
        Test_Queue_Double.enqueue(1.0);
        Test_Queue_Double.enqueue(2.0);
        Test_Queue_Double.enqueue(3.0);
        Test_Queue_Double.enqueue(4.0);

        if (Test_Queue_Integer.dequeue() == 4)
            if (Test_Queue_Integer.dequeue() == 3)
                if (Test_Queue_Integer.dequeue() == 2)
                    if (Test_Queue_Integer.dequeue() == 1)
                        if (Test_Queue_Integer.array.isEmpty())
                            if (Test_Queue_Double.dequeue() == 4.0)
                                if (Test_Queue_Double.dequeue() == 3.0)
                                    if (Test_Queue_Double.dequeue() == 2.0)
                                        if (Test_Queue_Double.dequeue() == 1.0)
                                            if (Test_Queue_Double.array.isEmpty()) {
                                                System.err.println("FUNZIONA");
                                                return;
                                            }
        System.err.println("NON FUNZIONA");

    }


}
