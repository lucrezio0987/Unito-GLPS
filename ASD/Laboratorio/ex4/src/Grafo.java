public class Grafo {
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
   * (*) quando il grafo è veramente sparso, assumendo che l'operazione venga 
   * effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1). 
   */

  public Grafo() {

  }

  public void addNode() {

  }
  
  public void addArch() {

  }

  public boolean isDirected() {
    return true;
  }

  public boolean containsNode(Node node) {
    return true;
  }

  public boolean containsArch(Arch arch) {
    return true;
  }

  public void removeNode(Node node) {

  }

  public void removeEdge(Arch arch) {

  }

  public int getNodesNumber() {
    return 0;
  }

  public int getArchNumber() {
    return 0;
  }

  public Set<Node> getNodes() {

  }

  public Set<Arch> getArch() {

  }

  public Set<Node> getNodesAdjacent(Node node) {

  }

  public String getArchLable(Arch arch) {

  }

  public String getGraphLable() {

  }

}
