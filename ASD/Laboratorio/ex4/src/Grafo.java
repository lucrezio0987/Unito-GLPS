import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Grafo<E extends Comparable<E>> {

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
   * n può indicare il numero di nodi o il numero di archi, a seconda del
   * contesto):
   * 
   * - Creazione di un grafo vuoto – O(1)
   * - Aggiunta di un nodo – O(1)
   * - Aggiunta di un arco – O(1)
   * - Verifica se il grafo è diretto – O(1)
   * - Verifica se il grafo contiene un dato nodo – O(1)
   * - Verifica se il grafo contiene un dato arco – O(1) (*)
   * - Cancellazione di un nodo – O(n)
   * - Cancellazione di un arco – O(1) (*)
   * - Determinazione del numero di nodi – O(1)
   * - Determinazione del numero di archi – O(n)
   * - Recupero dei nodi del grafo – O(n)
   * - Recupero degli archi del grafo – O(n)
   * - Recupero nodi adiacenti di un dato nodo – O(1) (*)
   * - Recupero etichetta associata a una coppia di nodi – O(1) (*)
   * - Determinazione del peso del grafo (se il grafo non è pesato, il metodo può
   * terminare con un errore)– O(n)
   * 
   * (*) Quando il grafo è veramente sparso, assumendo che l'operazione venga
   * effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).
   */

  public Grafo(Comparator<E> comparator, boolean diretto) {
    this.hashMap = new HashMap<>();
    this.comparator = comparator;
    this.diretto = diretto;

    // Creazione di un grafo vuoto – O(1)
  }

  public boolean isEmpty() {
    return hashMap.isEmpty();
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

    if (diretto) {
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
    ArchNumber = 0;

    for (ArrayList<Arch<E>> archList : hashMap.values())
      ArchNumber += archList.size();

    return ArchNumber;
  }

  public Set<Node<E>> getNodes() {
    // Recupero dei nodi del grafo – O(n)
    Set<Node<E>> nodi = hashMap.keySet();
    return nodi;
  }

  public Set<Arch<E>> getArch() {
    // Recupero degli archi del grafo – O(n)
    Set<Arch<E>> archSet = new HashSet<>();

    for (ArrayList<Arch<E>> archList : hashMap.values())
      archSet.addAll(archList);

    return archSet;
  }

  public Set<Node<E>> getNodesAdjacent(Node<E> node) {
    // Recupero nodi adiacenti di un dato nodo – O(1)
    // Quando il grafo è veramente sparso, assumendo che l'operazione venga
    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).

    if (!hashMap.containsKey(node)) {
        throw new IllegalArgumentException("Il nodo specificato non esiste nel grafo.");
    }

    ArrayList<Arch<E>> archList = hashMap.get(node);
    HashSet<Node<E>> adjacentNodes = new HashSet<>();

    for (Arch<E> arch : archList) {
        Node<E> destinationNode = new Node<>(arch.getDestinazione());
        adjacentNodes.add(destinationNode);
    }

    return adjacentNodes;
}

public float getNodesLabel(Node<E> node1, Node<E> node2) {
    // Recupero etichetta associata a una coppia di nodi – O(1)
    // Quando il grafo è veramente sparso, assumendo che l'operazione venga
    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).

    if (!hashMap.containsKey(node1) || !hashMap.containsKey(node2)) {
        throw new IllegalArgumentException("Uno o entrambi i nodi specificati non esistono nel grafo.");
    }

    ArrayList<Arch<E>> archList = hashMap.get(node1);

    for (Arch<E> arch : archList) {
        Node<E> destinationNode = new Node<>(arch.getDestinazione());
        if (destinationNode.equals(node2)) {
            return arch.getDistance();
        }
    }

    return -1;
}

  public double getGraphWeight() {
    // Determinazione del peso del grafo (se il grafo non è pesato, il metodo può
    // terminare con un errore)– O(n)
    GraphWeight = 0.0;

    if (hashMap.isEmpty())
      return GraphWeight;

    for (ArrayList<Arch<E>> archList : hashMap.values())
      for (Arch<E> arch : archList)
        GraphWeight += arch.getDistance();

    return GraphWeight;
  }

  public void MinForestPrim() {
    // IMPLEMENTAZIONE ALGORITMO di PRIM per calcolare la "minima foresta
    // ricoprente"

    // Inizializzazione
    HashSet<Node<E>> visitedNodes = new HashSet<>();
    HashSet<Node<E>> nonVisitedNodes = new HashSet<>(hashMap.keySet());

    // Scegli un nodo iniziale (qui prendiamo il primo nodo)
    Node<E> initialNode = nonVisitedNodes.iterator().next();
    visitedNodes.add(initialNode);
    nonVisitedNodes.remove(initialNode);

    // Continua finché ci sono nodi da visitare
    while (!nonVisitedNodes.isEmpty()) {
      Arch<E> minArch = null;
      Node<E> selectedNode = null;

      // Trova l'arco più corto che collega un nodo visitato a uno non visitato
      for (Node<E> visitedNode : visitedNodes) {
        ArrayList<Arch<E>> archList = hashMap.get(visitedNode);
        for (Arch<E> arch : archList) {
          Node<E> destinationNode = new Node<>(arch.getDestinazione());
          if (nonVisitedNodes.contains(destinationNode) &&
              (minArch == null || arch.getDistance() < minArch.getDistance())) {
            minArch = arch;
            selectedNode = destinationNode;
          }
        }
      }

      if (minArch != null && selectedNode != null) {
        // Aggiungi l'arco e il nodo alla foresta ricoprente
        visitedNodes.add(selectedNode);
        nonVisitedNodes.remove(selectedNode);
        // Puoi fare qualcosa con l'arco minimo "minArch" qui se necessario
      } else {
        // Non ci sono archi validi per collegare i nodi visitati ai nodi non visitati
        break;
      }
    }

    // La foresta ricoprente è costruita, puoi fare qualcosa con i nodi e gli archi
    // inclusi
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
