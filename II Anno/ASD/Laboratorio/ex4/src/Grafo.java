import java.util.AbstractQueue;
import java.util.ArrayList;
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
  int ArchNumber;
  int NodesNumber;
  Double GraphWeight;
  HashMap<Node<E>, ArrayList<Arch<E>>> hashMap;
  Comparator<E> comparator;

  //* COSTRUTTORE e METODI BASE
  // ? COMPLETATO
  public Grafo(Comparator<E> comparator, boolean diretto) {
    this.hashMap = new HashMap<>();
    this.comparator = comparator;
    this.diretto = diretto;

    this.ArchNumber = 0;
    this.NodesNumber = 0;
    this.GraphWeight = 0.0;
  }

  // ? COMPLETATO
  public boolean isEmpty() { return hashMap.isEmpty(); }
  
  // ? COMPLETATO
  public boolean isDirected() { return diretto; }
  

  //* Node
  public void addNode(Node<E> node) { hashMap.putIfAbsent(node, new ArrayList<>()); }
  public boolean containsNode(Node<E> node) { return hashMap.containsKey(node); }
  public Set<Node<E>> getNodes() { return hashMap.keySet(); }
  public int getNodesNumber() { return this.getNodes().size(); }

  public float getNodesLabel(Node<E> sorgente, Node<E> destinazione) {
    if (!hashMap.containsKey(sorgente) || !hashMap.containsKey(destinazione))
      throw new IllegalArgumentException("Uno o entrambi i nodi specificati non esistono nel grafo.");

    for (Arch<E> arch : getArchList(sorgente))
      if (arch.getDestinazione().equals(destinazione))
        return (float) arch.getDistance();
    
    return (float) -1;
  }

  // ! DA RIVEDERE
  public Set<Node<E>> getNodesAdjacent(Node<E> node) {
    if (!hashMap.containsKey(node))
      throw new IllegalArgumentException("Il nodo specificato non esiste nel grafo.");

    HashSet<Node<E>> adjacentNodes = new HashSet<>();

    for (Arch<E> arch : getArchList(node)) {
      if (!diretto)
        adjacentNodes.add( arch.getSorgente()     );
        adjacentNodes.add( arch.getDestinazione() );
    }

    return adjacentNodes;
  }

  //* Arch

  public ArrayList<Arch<E>> getArchList(Node<E> node) { return  hashMap.get(node);}
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


  //* Algoritmo di PRIM

}