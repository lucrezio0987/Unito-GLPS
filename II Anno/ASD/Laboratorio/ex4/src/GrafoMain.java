import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GrafoMain {

  public static void main(String[] args) {
    Grafo<String> grafo = new Grafo<>(String::compareTo, false);
  
    // Aggiungi alcuni nodi al grafo

    grafo.addNode(new Node<>("A"));
    grafo.addNode(new Node<>("B"));
    grafo.addNode(new Node<>("C"));
    grafo.addNode(new Node<>("D"));

  
    // Aggiungi alcuni archi al grafo
    grafo.addArch(new Arch<>("A", "B", 2.5f));
    grafo.addArch(new Arch<>("A", "C", 1.0f));
    grafo.addArch(new Arch<>("B", "C", 3.0f));
    grafo.addArch(new Arch<>("B", "D", 2.0f));
    grafo.addArch(new Arch<>("C", "D", 1.5f));

    System.out.println("\n");

    // Verifica se il grafo contiene un nodo specifico
    System.out.println("Il grafo contiene il nodo A: " + grafo.containsNode(new Node<>("A"))+ "/true");
    System.out.println("Il grafo contiene il nodo Z: " + grafo.containsNode(new Node<>("Z"))+ "/false");  

    // Verifica se il grafo contiene un arco specifico
    System.out.println("Il grafo contiene l'arco A -> B: " + grafo.containsArch(new Arch<>("A", "B", 2.5f)) + "/true");
    System.out.println("Il grafo contiene l'arco A -> D: " + grafo.containsArch(new Arch<>("A", "D", 4.0f)) + "/false");
    
    System.out.println("\n");

    // Stampa Gli ari per ogni Nodo
    System.out.println(grafo.toString());
    // Cose
    System.out.println("Numero Archi :  " + grafo.getArchNumber());
    System.out.println("Numero Nodi  :  " + grafo.getNodesNumber());
    System.out.println("Peso Grafo   :  " + grafo.getGraphWeight());

    System.out.println("\n");

    // Stampa Grafo Minimizzato
    System.out.println("GRAFO MINIMIZZATO\n");
    grafo.MinForestPrim();
    System.out.println("");
    System.out.println(grafo.toString());

    System.out.println("    EXPECTED                          ");
    System.out.println("    |  Node: A                        ");
    System.out.println("    |    Arch: A -> B, Distance: 2.5  ");
    System.out.println("    |    Arch: A -> C, Distance: 1.0  ");
    System.out.println("    |  Node: B                        ");
    System.out.println("    |  Node: C                        ");
    System.out.println("    |    Arch: C -> D, Distance: 1.5  ");
    System.out.println("    |  Node: D                        \n");

    // Cose
    System.out.println("Numero Archi :  " + grafo.getArchNumber()  + "    / 3   expected" );
    System.out.println("Numero Nodi  :  " + grafo.getNodesNumber() + "    / 4   expected" );
    System.out.println("Peso Grafo   :  " + grafo.getGraphWeight() + "  / 5.0 expected" );

    System.out.println("\n");

  }
}