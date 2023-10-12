import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


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

public class Grafo<E extends Comparable<E>> {

  boolean diretto;
  HashMap<Node<E>, ArrayList<Arch<E>>> hashMap;
  Comparator<E> comparator;

  //* COSTRUTTORE e METODI BASE
  public Grafo(Comparator<E> comparator, boolean diretto) {
    this.hashMap = new HashMap<>();
    this.comparator = comparator;
    this.diretto = diretto;
  }

  public boolean empty() { return hashMap.isEmpty(); }
  public boolean isDirected() { return diretto; }

  // ! DA RIVEDERE
  public double getGraphWeight() {
    Double GraphWeight = 0.0;

    for (ArrayList<Arch<E>> archList : getCollectioArchs()) 
      for (Arch<E> arch : archList) 
        GraphWeight += arch.getDistance();

    return GraphWeight;
  }

  //* Node
  public void addNode(Node<E> node) { hashMap.putIfAbsent(node, new ArrayList<Arch<E>>());}
  public boolean containsNode(Node<E> node) { return hashMap.containsKey(node); }
  public Set<Node<E>> getNodes() { return hashMap.keySet(); }
  public int getNodesNumber() { return this.getNodes().size(); }

  public float getNodesLabel(Node<E> sorgente, Node<E> destinazione) {
//    if (!hashMap.containsKey(sorgente) || !hashMap.containsKey(destinazione))
//      throw new IllegalArgumentException("Uno o entrambi i nodi specificati non esistono nel grafo.");

    for (Arch<E> arch : getArchList(sorgente))
      if (arch.getDestinazione().equals(destinazione))
        return (float) arch.getDistance();
    
    return (float) -1;
  }

  // ! DA RIVEDERE
  public Set<Node<E>> getNodesAdjacent(Node<E> node) {

    HashSet<Node<E>> adjacentNodes = new HashSet<>();

    for (Arch<E> arch : getArchList(node)) {
      if (!diretto)
        adjacentNodes.add( arch.getSorgente()     );
        adjacentNodes.add( arch.getDestinazione() );
    }

    return adjacentNodes;
  }

  // ! DA RIVEDERE
  public void removeNode(Node<E> node) {
    if (hashMap.remove(node) != null)
      for (Arch<E> arch : getArchList(node))
        getArchList(arch.getDestinazione()).remove(arch.reveArch());
  }

  //* Arch

  public ArrayList<Arch<E>> getArchList(Node<E> node) { return  hashMap.getOrDefault(node, new ArrayList<>());}
  public Collection<ArrayList<Arch<E>>> getCollectioArchs() { return hashMap.values();}
  // ! DA RIVEDERE
  public void addArch(Arch<E> arch) {
    if (!diretto) 
      hashMap.computeIfAbsent( arch.getDestinazione(), k -> new ArrayList<>() ).add( arch.reveArch() );
      hashMap.computeIfAbsent( arch.getSorgente(),     k -> new ArrayList<>() ).add( arch            );
  }

  // ! DA RIVEDERE
  public boolean containsArch(Arch<E> arch) {
    if(!diretto)
      if(getArchList(arch.getDestinazione()).contains(arch.reveArch()))
        return true; 
    return getArchList(arch.getSorgente()).contains(arch);
  }

  // ! DA RIVEDERE
  public void removeArch(Arch<E> arch) {
    if(getArchList(arch.getSorgente()).remove(arch) && !diretto)
      getArchList(arch.getDestinazione()).remove(arch.reveArch());
  }

  public Set<Arch<E>> getArch() {
    Set<Arch<E>> archSet = new HashSet<>();
    for (ArrayList<Arch<E>> archList : getCollectioArchs())
      archSet.addAll(archList);
    return archSet;
  }

  public int getArchNumber() {
    int ArchNumber = 0;

    if (diretto) 
      for (ArrayList<Arch<E>> allArchList : getCollectioArchs())
        ArchNumber += allArchList.size();
    else {
      Set<Arch<E>> uniqueArches = new HashSet<>();
      for (ArrayList<Arch<E>> archList : getCollectioArchs())
        for (Arch<E> arch : archList)
          if (!uniqueArches.contains(arch) && !uniqueArches.contains(arch.reveArch()))
            uniqueArches.add(arch);
      ArchNumber = uniqueArches.size();
    }
    
    return ArchNumber;
  }


  //* Algoritmo di PRIM

  public void MinForestPrim() { // TODO: ALGORITMO DI PRIM
    if (diretto)
      throw new UnsupportedOperationException("L'algoritmo di Prim è applicabile solo a grafi non diretti.");
    
    PriorityQueue<Arch<E>> minHeap = new PriorityQueue<>(new ArchComparator<>());
    HashMap<Node<E>, ArrayList<Arch<E>>> minimumForest = new HashMap<>();
    HashSet<Node<E>> visitedNodes = new HashSet<>();

    Node<E> startNode = hashMap.keySet().iterator().next();
    visitedNodes.add(startNode);
    ArrayList<Arch<E>> startNodeArchs = hashMap.get(startNode);
    if (startNodeArchs != null)
        minHeap.addAll(startNodeArchs);

    for (Node<E> node : hashMap.keySet()) 
      minimumForest.put(node, new ArrayList<>());

    while (!minHeap.empty() && visitedNodes.size() < hashMap.size()) {
        Arch<E> minArch = minHeap.top();
        minHeap.pop();
        Node<E> sourceNode = minArch.getSorgente();
        Node<E> destNode = minArch.getDestinazione();

        if (visitedNodes.contains(sourceNode) && visitedNodes.contains(destNode))
            continue;

        if (!minimumForest.containsKey(sourceNode))
            minimumForest.put(sourceNode, new ArrayList<>());
        minimumForest.get(sourceNode).add(minArch);

        visitedNodes.add(destNode);
        ArrayList<Arch<E>> destNodeArchs = hashMap.get(destNode);
        if (destNodeArchs != null) {
            for (Arch<E> adjacentArch : destNodeArchs) {
                if (!visitedNodes.contains(adjacentArch.getDestinazione()))
                    minHeap.push(adjacentArch);
            }
        }
    }

    hashMap = minimumForest;
}

  //* OVERRIDE
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (Node<E> node : hashMap.keySet()) {
      result.append(node.toString()).append("\n");
      for (Arch<E> arch : hashMap.get(node)) 
        result.append(arch.toString()).append("\n");
    }

    return result.toString();
  }

}