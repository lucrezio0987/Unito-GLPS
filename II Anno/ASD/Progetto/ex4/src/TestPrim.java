import java.util.Comparator;
import java.util.Set;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

public class TestPrim {
  Grafo<String> grafo;

  @Before
  public void createGrafo() {
    grafo = new Grafo<>(String::compareTo, false);
  }

  @Test
  public void testEmpty() {
      assertTrue(grafo.empty());
  }

  @Test
  public void testAddNode() {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));

      assertFalse(grafo.empty());
      assertTrue(grafo.containsNode(new Node<>("A")));
      assertTrue(grafo.containsNode(new Node<>("B")));
      assertTrue(grafo.containsNode(new Node<>("C")));
      assertFalse(grafo.containsNode(new Node<>("d")));
  }

  @Test
  public void testAddArch() {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));
      grafo.addArch(new Arch<>("B", "D", 2.0f));
      grafo.addArch(new Arch<>("C", "D", 1.5f));

      assertFalse(grafo.empty());
      assertTrue(grafo.containsArch(new Arch<>("A", "B", 2.5f)));
      assertTrue(grafo.containsArch(new Arch<>("A", "C", 1.0f)));
      assertTrue(grafo.containsArch(new Arch<>("B", "C", 3.0f)));
      assertTrue(grafo.containsArch(new Arch<>("B", "D", 2.0f)));
      assertTrue(grafo.containsArch(new Arch<>("C", "D", 1.5f)));
      assertFalse(grafo.containsArch(new Arch<>("A", "D", 4.0f)));
  }

  @Test
  public void testGetNodes() {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));
      grafo.addNode(new Node<>("D"));

      Set<Node<String>> nodes = grafo.getNodes();
      assertEquals(4, nodes.size());

      assertTrue(nodes.contains(new Node<>("A")));
      assertTrue(nodes.contains(new Node<>("B")));
      assertTrue(nodes.contains(new Node<>("C")));
      assertTrue(nodes.contains(new Node<>("D")));
  }

  @Test
  public void testGetArches() {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));

      Set<Arch<String>> arches = grafo.getArches();
      
      if (grafo.isDirected()) 
        assertEquals(3, arches.size());
      else {
        assertEquals(6, arches.size());
        assertTrue(arches.contains(new Arch<>("B", "A", 2.5f))); // Archi inversi
        assertTrue(arches.contains(new Arch<>("C", "A", 1.0f)));
        assertTrue(arches.contains(new Arch<>("C", "B", 3.0f)));
      }

      assertTrue(arches.contains(new Arch<>("A", "B", 2.5f)));
      assertTrue(arches.contains(new Arch<>("A", "C", 1.0f)));
      assertTrue(arches.contains(new Arch<>("B", "C", 3.0f)));
      assertFalse(arches.contains(new Arch<>("C", "D", 2.0f)));
  }

  @Test
  public void testGetNodesNumber() {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));

      assertEquals(3, grafo.getNodesNumber());
  }

  @Test
  public void testGetArchNumber() {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));

      assertEquals(3, grafo.getArchNumber());
  }

  @Test
  public void testGetGraphWeight() {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));
  
      if (grafo.isDirected()) 
        assertEquals(6.5f, grafo.getGraphWeight(), 0.001);
      else
        assertEquals(13.0f, grafo.getGraphWeight(), 0.001); // Doppia somma nel caso di grafo non diretto
  }


  @Test
  public void testMinForestPrim() {
      grafo.addNode(new Node<>("a"));
      grafo.addNode(new Node<>("b"));
      grafo.addNode(new Node<>("c"));
      grafo.addNode(new Node<>("d"));
      grafo.addNode(new Node<>("e"));
      grafo.addNode(new Node<>("f"));
      grafo.addNode(new Node<>("g"));
      grafo.addNode(new Node<>("h"));

      grafo.addArch(new Arch<>("a","b",9.0f));
      grafo.addArch(new Arch<>("a","c",3.0f));
      grafo.addArch(new Arch<>("a","d",2.0f));
      grafo.addArch(new Arch<>("a","e",5.0f));
      grafo.addArch(new Arch<>("b","c",9.0f));
      grafo.addArch(new Arch<>("b","f",5.0f));
      grafo.addArch(new Arch<>("c","e",4.0f));
      grafo.addArch(new Arch<>("d","f",9.0f));
      grafo.addArch(new Arch<>("d","h",2.0f));
      grafo.addArch(new Arch<>("e","h",2.0f));
      grafo.addArch(new Arch<>("f","h",9.0f));
      grafo.addArch(new Arch<>("f","g",9.0f));

      grafo.MinForestPrim();
      grafo.toString();

      // Verifica che il numero di nodi nella foresta sia corretto
      assertEquals(8, grafo.getNodesNumber());

      // Verifica che il numero di archi nella foresta sia corretto
      assertEquals(7, grafo.getArchNumber());

      // Verifica che il peso della foresta sia corretto
      assertEquals(32.0f, grafo.getGraphWeight(), 0.001);


      // Verifica che tutti i nodi inclusi nella foresta siano corretti
      assertTrue(grafo.containsNode(new Node<>("a")));
      assertTrue(grafo.containsNode(new Node<>("b")));
      assertTrue(grafo.containsNode(new Node<>("c")));
      assertTrue(grafo.containsNode(new Node<>("d")));
      assertTrue(grafo.containsNode(new Node<>("e")));
      assertTrue(grafo.containsNode(new Node<>("f")));

      // Verifica che tutti gli archi inclusi nella foresta siano corretti
      assertTrue(grafo.containsArch(new Arch<>("a", "d", 2.0f)));
      assertTrue(grafo.containsArch(new Arch<>("a", "c", 3.0f)));
      assertTrue(grafo.containsArch(new Arch<>("a", "b", 9.0f)));
      assertTrue(grafo.containsArch(new Arch<>("b", "f", 5.0f)));
      assertTrue(grafo.containsArch(new Arch<>("d", "h", 2.0f)));
      assertTrue(grafo.containsArch(new Arch<>("f", "g", 9.0f)));
      assertTrue(grafo.containsArch(new Arch<>("h", "e", 2.0f)));

      // Verifica che altri archi non siano inclusi nella foresta
      assertFalse(grafo.containsArch(new Arch<>("b", "c", 9.0f)));
      assertFalse(grafo.containsArch(new Arch<>("c", "e", 4.0f)));
      assertFalse(grafo.containsArch(new Arch<>("d", "f", 9.0f)));
      assertFalse(grafo.containsArch(new Arch<>("f", "h", 9.0f)));
  }

  @Test
  public void testGetNodesAdjacent() {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));
      grafo.addNode(new Node<>("D"));

      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));
      grafo.addArch(new Arch<>("B", "D", 2.0f));
      grafo.addArch(new Arch<>("C", "D", 1.5f));

      Set<Node<String>> adjacentNodes = grafo.getNodesAdjacent(new Node<>("A"));
      assertEquals(2, adjacentNodes.size());
      assertTrue(adjacentNodes.contains(new Node<>("B")));
      assertTrue(adjacentNodes.contains(new Node<>("C")));

      adjacentNodes = grafo.getNodesAdjacent(new Node<>("B"));
      assertEquals(3, adjacentNodes.size());
      assertTrue(adjacentNodes.contains(new Node<>("A")));
      assertTrue(adjacentNodes.contains(new Node<>("C")));
      assertTrue(adjacentNodes.contains(new Node<>("D")));
  }

  @Test
  public void testGetNodesLabel() {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));

      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));

      float labelAB = grafo.getNodesLabel(new Node<>("A"), new Node<>("B"));
      assertEquals(2.5f, labelAB, 0.001f);
  
      float labelAC = grafo.getNodesLabel(new Node<>("A"), new Node<>("C"));
      assertEquals(1.0f, labelAC, 0.001f);
  
      float labelBC = grafo.getNodesLabel(new Node<>("B"), new Node<>("C"));
      assertEquals(3.0f, labelBC, 0.001f);
  }

  @Test
  public void testRemoveNode() {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));

      grafo.removeNode(new Node<>("B"));

      Set<Node<String>> nodes = grafo.getNodes();
      assertEquals(2, nodes.size());
      assertTrue(nodes.contains(new Node<>("A")));
      assertFalse(nodes.contains(new Node<>("B")));
      assertTrue(nodes.contains(new Node<>("C")));

  }

  @Test
  public void testRemoveArch() {
        grafo.addArch(new Arch<>("A", "B", 2.5f));
        grafo.addArch(new Arch<>("A", "C", 1.0f));
        grafo.addArch(new Arch<>("B", "C", 3.0f));
  
        Arch<String> archToRemove = new Arch<>("A", "B", 2.5f);
        grafo.removeArch(archToRemove);
  
        Set<Arch<String>> arches = grafo.getArches();
        
        if (!grafo.isDirected()) {
          assertEquals(4, arches.size());
          assertFalse(arches.contains(archToRemove));
          
          Arch<String> reverseArch = archToRemove.reveArch();
          assertFalse(arches.contains(reverseArch));
        } else {
          assertEquals(2, arches.size());
          assertFalse(arches.contains(archToRemove));
        }
        
        assertTrue(arches.contains(new Arch<>("A", "C", 1.0f)));
        assertTrue(arches.contains(new Arch<>("B", "C", 3.0f)));
  }
}
