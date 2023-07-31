public class PriorityQueue<T> implements AbstractQueue<T>{
    private Queue_obj<T>[] arr;
    private int count;
    private int capacity;
    private int rear;
    private int front;

    public Queue(int size) {
        arr = new Queue_obj[size];
        capacity = size;
        front = 0;
        count = 0;
        rear = -1;
    }
    public static void main (String[] args) {
        // creare una queue
        Queue<Integer> q = new Queue<>(5);
    }
}