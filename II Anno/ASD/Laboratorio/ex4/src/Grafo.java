import java.util.AbstractQueue;
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

  public Grafo(Comparator<E> comparator, boolean diretto) { // ? COMPLETATO
    this.hashMap = new HashMap<>();
    this.comparator = comparator;
    this.diretto = diretto;

    // Creazione di un grafo vuoto – O(1)
  }

  public boolean isEmpty() { // ? COMPLETATO
    return hashMap.isEmpty();
  }

  public void addNode(Node<E> newNode) { // ? COMPLETATO
    // Aggiunta di un nodo – O(1)
    if (!hashMap.containsKey(newNode))
      hashMap.put(newNode, new ArrayList<>());
  }

  public void addArch(Arch<E> arch) { // ! DA RIVEDERE
    Node<E> sourceNode = new Node<>(arch.getSorgente());
    Node<E> destNode = new Node<>(arch.getDestinazione());

    ArrayList<Arch<E>> archList = hashMap.getOrDefault(sourceNode, new ArrayList<>());
    archList.add(arch);
    hashMap.put(sourceNode, archList);

    if (diretto) {
      ArrayList<Arch<E>> reverseArchList = hashMap.getOrDefault(destNode, new ArrayList<>());
      reverseArchList.add(new Arch<>(destNode.getVal(), sourceNode.getVal(), arch.getDistance()));
      hashMap.put(destNode, reverseArchList);
    }
  }

  public boolean isDirected() { // ? COMPLETATO
    // Verifica se il grafo è diretto – O(1)
    return diretto;
  }

  public boolean containsNode(Node<E> node) { // ? COMPLETATO
    // Verifica se il grafo contiene un dato nodo – O(1)
    return hashMap.containsKey(node);
  }

  public boolean containsArch(Arch<E> arch) { // ! DA RIVEDERE
    ArrayList<Arch<E>> archList = hashMap.get(new Node<>(arch.getSorgente()));

    if (archList == null) return false; // The source node does not have any arches

    for (Arch<E> existingArch : archList)
      if (comparator.compare(existingArch.getSorgente(), arch.getSorgente()) == 0 &&
          comparator.compare(existingArch.getDestinazione(), arch.getDestinazione()) == 0 &&
          existingArch.getDistance() == arch.getDistance())
        return true; // Found the corresponding arch

    return false; // The arch is not present
  }

  public void removeNode(Node<E> node) { // ! DA RIVEDERE
    // Cancellazione di un nodo – O(n)
    if (hashMap.containsKey(node)) {
      // Rimuovi gli archi in cui il nodo è coinvolto come sorgente
      ArrayList<Arch<E>> sourceArchList = hashMap.get(node);
      for (Arch<E> arch : sourceArchList) {
        Node<E> destNode = new Node<>(arch.getDestinazione());
        ArrayList<Arch<E>> destinationArchList = hashMap.get(destNode);
        if (destinationArchList != null) {
          // Rimuovi gli archi inversi corrispondenti
          destinationArchList.removeIf(a -> a.getSorgente().equals(node));
          hashMap.put(destNode, destinationArchList);
        }
      }

      // Rimuovi il nodo dalla mappa
      hashMap.remove(node);
    }
  }

  public void removeArch(Arch<E> arch) { // ! DA RIVEDERE
    // Cancellazione di un arco – O(1)
    // Quando il grafo è veramente sparso, assumendo che l'operazione venga
    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).
    Node<E> sourceNode = new Node<>(arch.getSorgente());
    if (hashMap.containsKey(sourceNode)) {
      ArrayList<Arch<E>> archList = hashMap.get(sourceNode);
      archList.remove(arch);
      hashMap.put(sourceNode, archList);

      if (diretto) {
        Node<E> destNode = new Node<>(arch.getDestinazione());
        ArrayList<Arch<E>> reverseArchList = hashMap.get(destNode);
        if (reverseArchList != null) {
          // Rimuovi gli archi inversi corrispondenti
          reverseArchList.removeIf(a -> a.getSorgente().equals(destNode) && a.getDestinazione().equals(sourceNode));
          hashMap.put(destNode, reverseArchList);
        }
      }
    }
  }

  public int getNodesNumber() { // ? COMPLETATO
    // Determinazione del numero di nodi – O(1)
    Set<Node<E>> nodi = hashMap.keySet();
    NodesNumber = nodi.size();
    return NodesNumber;
  }

  public int getArchNumber() { // ? COMPLETATO
    // Determinazione del numero di archi – O(n)
    ArchNumber = 0;

    for (ArrayList<Arch<E>> archList : hashMap.values())
      ArchNumber += archList.size();

    return ArchNumber;
  }

  public Set<Node<E>> getNodes() { // ? COMPLETATO
    // Recupero dei nodi del grafo – O(n)
    Set<Node<E>> nodi = hashMap.keySet();
    return nodi;
  }

  public Set<Arch<E>> getArch() { // ? COMPLETATO
    // Recupero degli archi del grafo – O(n)
    Set<Arch<E>> archSet = new HashSet<>();

    for (ArrayList<Arch<E>> archList : hashMap.values())
      archSet.addAll(archList);

    return archSet;
  }

  public Set<Node<E>> getNodesAdjacent(Node<E> node) { // ! DA RIVEDERE
    // Recupero nodi adiacenti di un dato nodo – O(1)
    // Quando il grafo è veramente sparso, assumendo che l'operazione venga
    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).

    if (!hashMap.containsKey(node))
      throw new IllegalArgumentException("Il nodo specificato non esiste nel grafo.");

    ArrayList<Arch<E>> archList = hashMap.get(node);
    HashSet<Node<E>> adjacentNodes = new HashSet<>();

    for (Arch<E> arch : archList) {
      Node<E> destNode = new Node<>(arch.getDestinazione());
      adjacentNodes.add(destNode);
    }

    return adjacentNodes;
  }

  public float getNodesLabel(Node<E> sorgente, Node<E> destinazione) { // ? COMPLETATO
    // Recupero etichetta associata a una coppia di nodi – O(1)
    // Quando il grafo è veramente sparso, assumendo che l'operazione venga
    // effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).

    if (!hashMap.containsKey(sorgente) || !hashMap.containsKey(destinazione))
      throw new IllegalArgumentException("Uno o entrambi i nodi specificati non esistono nel grafo.");

    ArrayList<Arch<E>> archList = hashMap.get(sorgente);

    for (Arch<E> arch : archList) {
      Node<E> destNode = new Node<>(arch.getDestinazione());
      if (destNode.equals(destinazione))
        return (float) arch.getDistance();
    }

    return (float) -1;
  }

  public double getGraphWeight() { // ! DA RIVEDERE
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

  public void MinForestPrim() { // TODO
    // IMPLEMENTAZIONE ALGORITMO di PRIM per calcolare la "minima foresta
    // ricoprente"
    
    if (diretto) throw new UnsupportedOperationException("L'algoritmo di Prim è applicabile solo a grafi non diretti.");

    // HashMap<Node<E>, ArrayList<Arch<E>>> hashMap;
    
    Set<Node<E>> visitedNodes = new HashSet<>();
    PriorityQueue<Arch<E>> minHeap = new PriorityQueue<>(new ArchComparator<>());
    Node<E> startNode = hashMap.keySet().iterator().next();
    HashMap<Node<E>, ArrayList<Arch<E>>> minimumForest = new HashMap<>();

    // Inizializza la foresta con un singolo nodo
    minimumForest.put(startNode, new ArrayList<>());
      
    for (Node<E> node : hashMap.keySet()) minimumForest.put(node, new ArrayList<>());

    // Aggiungi tutti gli archi uscenti dal nodo iniziale al minHeap
    minHeap.addAll(hashMap.get(startNode));

    while (!minHeap.empty() && visitedNodes.size() < hashMap.size()) {
      Arch<E> minArch = minHeap.top(); minHeap.pop();
      Node<E> sourceNode = new Node<>(minArch.getSorgente());
      Node<E> destNode = new Node<>(minArch.getDestinazione());

      // Verifica se l'arco collega due nodi già visitati, in tal caso scartalo
      if (visitedNodes.contains(sourceNode) && visitedNodes.contains(destNode)) 
       continue;

      // Aggiungi il nodo di destinazione ai nodi visitati
      visitedNodes.add(destNode);

      // Aggiungi l'arco minimo alla foresta ricoprente
      if (!minimumForest.containsKey(sourceNode)) 
        minimumForest.put(sourceNode, new ArrayList<>());
      minimumForest.get(sourceNode).add(minArch);

      // Aggiungi tutti gli archi uscenti dal nodo di destinazione non visitato al minHeap
      ArrayList<Arch<E>> adjacentArchs = hashMap.get(destNode);
      if (adjacentArchs != null)
        for (Arch<E> adjacentArch : adjacentArchs) 
            if (!visitedNodes.contains(minArch.getDestinazione())) 
                minHeap.push(adjacentArch);
    }

    hashMap = minimumForest;
  }


  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (Node<E> node : hashMap.keySet()) {
      result.append("Node: ").append(node.getVal()).append("\n");
      
      ArrayList<Arch<E>> archList = hashMap.get(node);
      
      // if (archList.isEmpty()) result.append("  (No Archi in uscita)\n"); else 
      for (Arch<E> arch : archList) 
        result.append("  Arch: ").append(arch.getSorgente()).append(" -> ")
              .append(arch.getDestinazione()).append(", Distance: ").append(arch.getDistance())
              .append("\n");
    }

    return result.toString();
  }
}
                      