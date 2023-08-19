import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class Grafo<E extends Comparable<E>>  {

  boolean diretto;
  int ArchNumber;
  int NodesNumber;
  Double GraphWeight;
  HashMap<Node<E>, ArrayList<Arch<E>>> hashMap;
  Comparator<E> comparator;

  /*
   * L'implementazione deve essere generica sia per quanto riguarda il tipo dei 
   * nodi, sia per quanto riguarda le etichette degli archi. 
   * 
   * La struttura dati implementata dovrà offrire (almeno) le seguenti operazioni
   * (accanto a ogni operazione è specificata la complessità richiesta; 
   * n può indicare il numero di nodi o il numero di archi, a seconda del contesto): 
   * 
   *  - Creazione di un grafo vuoto – O(1)
   *  - Aggiunta di un nodo – O(1)
   *  - Aggiunta di un arco – O(1)
   *  - Verifica se il grafo è diretto – O(1)
   *  - Verifica se il grafo contiene un dato nodo – O(1)
   *  - Verifica se il grafo contiene un dato arco – O(1) (*)
   *  - Cancellazione di un nodo – O(n)
   *  - Cancellazione di un arco – O(1) (*)
   *  - Determinazione del numero di nodi – O(1)
   *  - Determinazione del numero di archi – O(n)
   *  - Recupero dei nodi del grafo – O(n)
   *  - Recupero degli archi del grafo – O(n)
   *  - Recupero nodi adiacenti di un dato nodo – O(1) (*)
   *  - Recupero etichetta associata a una coppia di nodi – O(1) (*)
   *  - Determinazione del peso del grafo (se il grafo non è pesato, il metodo può terminare con un errore)– O(n) 
   * 
   * (*) Quando il grafo è veramente sparso, assumendo che l'operazione venga 
   * effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1). 
   */


  public Grafo(Comparator<E> comparator, boolean diretto) {
    this.hashMap = new HashMap<>();
    this.comparator = comparator;
    this.diretto = diretto;

    //Creazione di un grafo vuoto – O(1)
  }

  public void addNode(Node<E> newNode) {
    // Aggiunta di un nodo – O(1)
    if (!hashMap.containsKey(newNode)) 
      hashMap.put(newNode, new ArrayList<>());
  }
 
  public void addArch(Arch<E> arch) {
    Node<E> sourceNode = new Node<>(arch.getSorgente());
    Node<E> destinationNode = new Node<>(arch.getDestinazione());

    ArrayList<Arch<E>> archList = hashMap.getOrDefault(sourceNode, new ArrayList<>());
    archList.add(arch);
    hashMap.put(sourceNode, archList);

    if (!diretto) {
      ArrayList<Arch<E>> reverseArchList = hashMap.getOrDefault(destinationNode, new ArrayList<>());
      reverseArchList.add(new Arch<>(destinationNode.getVal(), sourceNode.getVal(), arch.getDistance()));
      hashMap.put(destinationNode, reverseArchList);
    }
  }


  public boolean isDirected() {
    // Verifica se il grafo è diretto – O(1)
    return diretto;
  }

  public boolean containsNode(Node<E> node) {
    // Verifica se il grafo contiene un dato nodo – O(1)
    return hashMap.containsKey(node);
  }

  public boolean containsArch(Arch<E> arch) {
    ArrayList<Arch<E>> archList = hashMap.get(new Node<>(arch.getSorgente()));
    if (archList == null) {
        return false; // The source node does not have any arches
    }

    for (Arch<E> existingArch : archList) {
        if (comparator.compare(existingArch.getSorgente(), arch.getSorgente()) == 0 &&
            comparator.compare(existingArch.getDestinazione(), arch.getDestinazione()) == 0 &&
            existingArch.getDistance() == arch.getDistance()) {
            return true; // Found the corresponding arch
        }
    }

    return false; // The arch is not present
  }

  public void removeNode(Node node) {
    // Cancellazione di un nodo – O(n)
  }

  public void removeEdge(Arch arch) {
    // Cancellazione di un arco – O(1)
    // Quando il grafo è veramente sparso, assumendo che l'operazione venga 
    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).
  }

  public int getNodesNumber() {
    // Determinazione del numero di nodi – O(1)
    Set<Node<E>> nodi = hashMap.keySet();
    NodesNumber = nodi.size();
    return NodesNumber;
  }

  public int getArchNumber() {
    // Determinazione del numero di archi – O(n)
    return ArchNumber;
  }

//  public Set<Node> getNodes() {
//    // Recupero dei nodi del grafo – O(n)
//
//  }
//
//  public Set<Arch> getArch() {
//    // Recupero degli archi del grafo – O(n)
//  }
//
//  public Set<Node> getNodesAdjacent(Node node) {
//    // Recupero nodi adiacenti di un dato nodo – O(1)
//    // Quando il grafo è veramente sparso, assumendo che l'operazione venga 
//    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).
//  }
//
//  public String getNodesLable(Node node1, Node node2) {
//    // Recupero etichetta associata a una coppia di nodi – O(1)
//    // Quando il grafo è veramente sparso, assumendo che l'operazione venga 
//    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).
//  }
//
  public int getGraphWeight() {
    // Determinazione del peso del grafo (se il grafo non è pesato, il metodo può terminare con un errore)– O(n)
    return -1;
  }

  public void MinForestPrim(){
    // IMPLEMENTAZIONE ALGORITMO di PRIM per calcolare la "minima foresta ricoprente"
  }


  @Override
  public String toString() {
      StringBuilder result = new StringBuilder();

      // Iterate over nodes and their corresponding arches
      for (Node<E> node : hashMap.keySet()) {
          result.append("Node: ").append(node.getVal()).append("\n");

          ArrayList<Arch<E>> archList = hashMap.get(node);
          for (Arch<E> arch : archList) {
              result.append("  Arch: ").append(arch.getSorgente()).append(" -> ")
                    .append(arch.getDestinazione()).append(", Distance: ").append(arch.getDistance())
                    .append("\n");
          }
      }

      return result.toString();
  }
}
