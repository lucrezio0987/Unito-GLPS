import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Grafo<E extends Comparable<E>> implements GrafoInterface<E> {

  boolean diretto;
  HashMap<Node<E>, ArrayList<Arch<E>>> hashMap;
  Comparator<E> comparator;


  public Grafo(Comparator<E> comparator, boolean diretto) {
    this.hashMap = new HashMap<>();
    this.comparator = comparator;
    this.diretto = diretto;
  }

  public boolean empty()                                        { return hashMap.isEmpty(); }
  public boolean isDirected()                                   { return diretto; }
  public void addNode(Node<E> node)                             { hashMap.putIfAbsent(node, new ArrayList<Arch<E>>());}
  public boolean containsNode(Node<E> node)                     { return hashMap.containsKey(node); }
  public Set<Node<E>> getNodes()                                { return hashMap.keySet(); }
  public int getNodesNumber()                                   { return this.getNodes().size(); }
  private ArrayList<Arch<E>> getArchList(Node<E> node)          { return  hashMap.getOrDefault(node, new ArrayList<>());}
  private Collection<ArrayList<Arch<E>>> getCollectioArches()   { return hashMap.values();}

  public double getGraphWeight() {
    Double GraphWeight = 0.0;

    for (ArrayList<Arch<E>> archList : getCollectioArches())
      for (Arch<E> arch : archList) 
        GraphWeight += arch.getDistance();
    return GraphWeight;
  }

  public float getNodesLabel(Node<E> sorgente, Node<E> destinazione) {
    for (Arch<E> arch : getArchList(sorgente))
      if (arch.getDestinazione().equals(destinazione))
        return arch.getDistance();

    return -1.0f;
  }

  public Set<Node<E>> getNodesAdjacent(Node<E> node) {
    if (!hashMap.containsKey(node))
      throw new IllegalArgumentException("Il nodo specificato non esiste nel grafo.");

    Set<Node<E>> adjacentNodes = new HashSet<>();

    getArchList(node).forEach((arch) -> adjacentNodes.add(arch.getDestinazione()));

    if (!diretto)
      getNodes().forEach((currentNode) -> {
        if (!currentNode.equals(node) && !adjacentNodes.contains(currentNode))
          getArchList(currentNode).forEach((arch) -> {
            if (arch.getDestinazione().equals(node))
              adjacentNodes.add(currentNode);
          });
      });
    return adjacentNodes;
  }

  public void removeNode(Node<E> node) {
    if (hashMap.remove(node) != null)
        getArchList(node).forEach((arch) ->
          getArchList(arch.getDestinazione()).remove(arch.reveArch())
        );
  }

  public void addArch(Arch<E> arch) {
    if (!diretto) 
      hashMap.computeIfAbsent( arch.getDestinazione(), k -> new ArrayList<>() ).add( arch.reveArch() );
      hashMap.computeIfAbsent( arch.getSorgente(),     k -> new ArrayList<>() ).add( arch            );
  }

  public boolean containsArch(Arch<E> arch) {
    if(!diretto)
      if(getArchList(arch.getDestinazione()).contains(arch.reveArch()))
        return true; 
    return getArchList(arch.getSorgente()).contains(arch);
  }

  public void removeArch(Arch<E> arch) {
    if(getArchList(arch.getSorgente()).remove(arch) && !diretto)
      getArchList(arch.getDestinazione()).remove(arch.reveArch());
  }

  public Set<Arch<E>> getArches() {
    Set<Arch<E>> archSet = new HashSet<>();
    getCollectioArches().forEach((archList) -> archSet.addAll(archList));
    return archSet;
  }

  public int getArchNumber() {
    if (diretto) 
      return getArches().size();
    
    Set<Arch<E>> uniqueArches = new HashSet<>();
    getCollectioArches().forEach((archList) -> {
      archList.forEach((arch) -> {
        if (!uniqueArches.contains(arch) && !uniqueArches.contains(arch.reveArch()))
            uniqueArches.add(arch);
        });
      });
    
    return uniqueArches.size();
  }

  public void MinForestPrim() {
    if (diretto)
      throw new UnsupportedOperationException("L'algoritmo di Prim è applicabile solo a grafi non diretti.");
    
    HashSet<Node<E>>                      visitedNodes    = new HashSet<>();
    HashMap<Node<E>, ArrayList<Arch<E>>>  minimumForest   = new HashMap<>();
    getNodes().forEach((node) -> minimumForest.put(node, new ArrayList<>()));

    while (visitedNodes.size() != hashMap.size()) {
      Node<E>                             startNode       = getNodes().iterator().next();
      AbstractQueue<Arch<E>>              minHeap         = new PriorityQueue<>(new ArchComparator<>());

      for(Node<E> node : getNodes())
        if(!visitedNodes.contains(node)){
          startNode = node;
          break;
        }

      hashMap.get(startNode).forEach( (arch) -> minHeap.push(arch));
      visitedNodes.add(startNode);

      while (!minHeap.empty() && visitedNodes.size() < hashMap.size()) {
          Arch<E> minArch = minHeap.top();  minHeap.pop(); // Pull()
          Node<E> sourceNode = minArch.getSorgente();
          Node<E> destNode = minArch.getDestinazione();

          if (visitedNodes.contains(sourceNode) && visitedNodes.contains(destNode) )
              continue;

          minimumForest.get(sourceNode).add(minArch);

          visitedNodes.add(destNode);
          hashMap.get(destNode).forEach((adjacentArch) -> {
                                            if (!visitedNodes.contains(adjacentArch.getDestinazione()))
                                                minHeap.push(adjacentArch);
                                          });
      }
    }

    hashMap = minimumForest;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (Node<E> node : getNodes()) {
      result.append(node.toString()).append("\n");
      for (Arch<E> arch : hashMap.get(node)) 
        result.append(arch.toString()).append("\n");
    }

    return result.toString();
  }
}
