import java.util.Comparator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GrafoMain {

  public static void main(String[] args) {
    Grafo<String> grafo = new Grafo<>(String::compareTo, false);
  
    // Aggiungi alcuni nodi al grafo
    //grafo.addNode(new Node<>("A"));
    //grafo.addNode(new Node<>("B"));
    //grafo.addNode(new Node<>("C"));
    //grafo.addNode(new Node<>("D"));
    //grafo.addNode(new Node<>("E"));
    //grafo.addNode(new Node<>("F"));
  //
    //// Aggiungi alcuni archi al grafo
    //grafo.addArch(new Arch<>("A", "B", 2.5f));
    //grafo.addArch(new Arch<>("A", "C", 1.0f));
    //grafo.addArch(new Arch<>("B", "C", 3.0f));
    //grafo.addArch(new Arch<>("B", "D", 2.0f));
    //grafo.addArch(new Arch<>("C", "D", 1.5f));


          //BufferedReader inputFile;
          String[] lnBufferSplitted;
          String lnBuffer, sorgente, destinazione;
          float distance;
          Arch arch;
          String FileName;
          int Limit = 0;
          
          if(!args[0].isEmpty()) FileName = args[0]; else 
          FileName = "../italian_dist_graph.csv";
          
          try ( BufferedReader inputFile = new BufferedReader(new FileReader(args[0]))) {
    
            while ((lnBuffer = inputFile.readLine()) != null) {
              //if(++Limit > 1000) break; // <--- LIMITATORE
              lnBufferSplitted = lnBuffer.split(",");
              sorgente = lnBufferSplitted[0];
              destinazione = lnBufferSplitted[1];
              distance = Float.parseFloat(lnBufferSplitted[2]);
    
              arch = new Arch(sorgente, destinazione, distance);
              grafo.addArch(arch);
            }
            inputFile.close();
          } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
          }
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
    System.out.println("Peso Grafo   :  " + String.format("%.3f", grafo.getGraphWeight()));

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
    System.out.println("Numero Nodi  :  " + grafo.getNodesNumber() + "    / 6   expected" );
    System.out.println("Peso Grafo   :  " + String.format("%.3f", grafo.getGraphWeight()) + "  / 5.0 expected" );

    System.out.println("\n");
  }
}


/*

Posso fornirti un esempio di come dovrebbe venire il risultato dell'applicazione dell'algoritmo di Prim su un grafo. Supponiamo di avere il seguente grafo:

Grafo iniziale:
```
Node: A
  Arch: A -> B, Distance: 2.5
  Arch: A -> C, Distance: 1.0
Node: B
  Arch: B -> A, Distance: 2.5
  Arch: B -> C, Distance: 3.0
  Arch: B -> D, Distance: 2.0
Node: C
  Arch: C -> A, Distance: 1.0
  Arch: C -> B, Distance: 3.0
  Arch: C -> D, Distance: 1.5
Node: D
  Arch: D -> B, Distance: 2.0
  Arch: D -> C, Distance: 1.5
Node: E
Node: F
```

Prima dell'applicazione dell'algoritmo di Prim, il numero di archi, il numero di nodi e il peso totale del grafo possono essere calcolati come segue:

- Numero_Archi: 9 (ci sono 9 archi nel grafo iniziale)
- Numero_di_Nodi(Erticigrafo): 6 (ci sono 6 nodi nel grafo iniziale)
- Peso_Grafo: 12.0 (la somma delle distanze degli archi nel grafo iniziale)

Dopo l'applicazione dell'algoritmo di Prim per trovare la minima foresta ricorrente, il grafo risultante potrebbe essere il seguente:

Grafo risultante dopo l'applicazione di Prim:
```
Node: A
  Arch: A -> C, Distance: 1.0
Node: B
  Arch: B -> D, Distance: 2.0
Node: C
Node: D
  Arch: D -> C, Distance: 1.5
Node: E
Node: F
```

Dopo l'applicazione dell'algoritmo di Prim, il numero di archi, il numero di nodi e il peso totale del grafo risultante possono essere calcolati come segue:

- Numero_Archi: 3 (ci sono 3 archi nel grafo risultante)
- Numero_di_Nodi(Erticigrafo): 4 (ci sono 4 nodi nel grafo risultante)
- Peso_Grafo: 3.5 (la somma delle distanze degli archi nel grafo risultante)

Quindi, queste sono le informazioni richieste prima e dopo l'applicazione dell'algoritmo di Prim sul grafo.
 
 */