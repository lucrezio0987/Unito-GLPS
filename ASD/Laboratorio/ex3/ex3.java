import java.util.Random;

public class ex3 {
    public static void main(String[] args) {
        Random random = new Random();
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Integer::compare);
        int i, tot = 10 ;

        for(i = 0; i< tot; ++i) {
          int r = random.nextInt(tot*2);
          priorityQueue.push(r);
        }

        System.out.println("Fine Inserimento\n");

        System.out.println("Coda: "  + priorityQueue.heap + " \n ");
        System.out.println("sort: "  + priorityQueue.sort + " \n ");
        
        
        System.out.println("contains 3: " + priorityQueue.contains(3));
        System.out.println("contains 17: " + priorityQueue.contains(17) + " \n ");
        
        System.out.println("remove 3: " + priorityQueue.remove(3));
        System.out.println("remove 17: " + priorityQueue.remove(17) + " \n ");

        System.out.println("Coda: "  + priorityQueue.heap + " \n ");
        System.out.println("sort: "  + priorityQueue.sort + " \n ");
        /*
        while(!priorityQueue.empty()){
          System.out.println("Top: " + priorityQueue.top() + " Coda: "  + priorityQueue);
          
          System.out.println("> Pop Eseguita...");
          priorityQueue.pop();
        }

        System.out.println("\nFine Rimozione:   elementi non minimi = " + flag);*/
    }
};