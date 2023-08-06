public class ex3 {
    public static void main(String[] args) {
        // Esempio di utilizzo
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Integer::compare);
        priorityQueue.push(5);
        priorityQueue.push(2);
        priorityQueue.push(1);
        priorityQueue.push(3);
        priorityQueue.push(10);
        priorityQueue.push(1);
        priorityQueue.push(3);

        while(!priorityQueue.empty()){
          System.out.println("Top: " + priorityQueue.top() + "   Coda: "  + priorityQueue);
          System.out.println("Pop Eseguita...");
          priorityQueue.pop();
        }
    }
};