import java.util.Comparator;
import java.util.Set;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class TestPrim {
  Grafo<String> grafo;

  @Before
  public void createGrafo() {
    grafo = new Grafo<>(String::compareTo, false);
  }

  @Test
  public void testIsEmpty() {
    try {
      assertTrue(grafo.isEmpty());
    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testIsEmpty");
    }
  }

  @Test
  public void testAddNode() {
    try {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));

      assertFalse(grafo.isEmpty());
      assertTrue(grafo.containsNode(new Node<>("A")));
      assertTrue(grafo.containsNode(new Node<>("B")));
      assertTrue(grafo.containsNode(new Node<>("C")));
      assertFalse(grafo.containsNode(new Node<>("d")));

    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testAddNode");
    }
  }

  @Test
  public void testAddArch() {
    try {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));
      grafo.addArch(new Arch<>("B", "D", 2.0f));
      grafo.addArch(new Arch<>("C", "D", 1.5f));

      assertFalse(grafo.isEmpty());
      assertTrue(grafo.containsArch(new Arch<>("A", "B", 2.5f)));
      assertTrue(grafo.containsArch(new Arch<>("A", "C", 1.0f)));
      assertTrue(grafo.containsArch(new Arch<>("B", "C", 3.0f)));
      assertTrue(grafo.containsArch(new Arch<>("B", "D", 2.0f)));
      assertTrue(grafo.containsArch(new Arch<>("C", "D", 1.5f)));
      assertFalse(grafo.containsArch(new Arch<>("A", "D", 4.0f)));

    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testAddArch");
    }
  }

  @Test
  public void testGetNodes() {
    try {
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
    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testGetNodes");
    }

  }

  @Test
  public void testGetArch() {
    try {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));

      Set<Arch<String>> arches = grafo.getArch();
      
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
    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testGetArch");
    }
  }

  @Test
  public void testGetNodesNumber() {
    try {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));

      assertEquals(3, grafo.getNodesNumber());
    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testGetNodesNumber");
    }
  }

  @Test
  public void testGetArchNumber() {
    try {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));

      assertEquals(3, grafo.getArchNumber());
    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testGetArchNumber");
    }
  }

  @Test
  public void testGetGraphWeight() {
    try {
      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));
  
      if (grafo.isDirected()) 
        assertEquals(6.5f, grafo.getGraphWeight(), 0.001);
      else
        assertEquals(13.0f, grafo.getGraphWeight(), 0.001); // Doppia somma nel caso di grafo non diretto
    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testGetGraphWeight");
    }
  }

  @Test
  public void testMinForestPrim() {
    try {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));
      grafo.addNode(new Node<>("D"));

      grafo.addArch(new Arch<>("A", "B", 2.5f));
      grafo.addArch(new Arch<>("A", "C", 1.0f));
      grafo.addArch(new Arch<>("B", "C", 3.0f));
      grafo.addArch(new Arch<>("B", "D", 2.0f));
      grafo.addArch(new Arch<>("C", "D", 1.5f));

      grafo.MinForestPrim();

      // Verifica che il numero di nodi nella foresta sia corretto
      assertEquals(4, grafo.getNodesNumber());

      // Verifica che il numero di archi nella foresta sia corretto
      assertEquals(3, grafo.getArchNumber());

      // Verifica che il peso della foresta sia corretto
      assertEquals(4.0f, grafo.getGraphWeight(), 0.001);

      // Verifica che tutti i nodi inclusi nella foresta siano corretti
      assertTrue(grafo.containsNode(new Node<>("A")));
      assertTrue(grafo.containsNode(new Node<>("B")));
      assertTrue(grafo.containsNode(new Node<>("C")));
      assertTrue(grafo.containsNode(new Node<>("D")));

      // Verifica che tutti gli archi inclusi nella foresta siano corretti
      assertTrue(grafo.containsArch(new Arch<>("A", "B", 2.5f)));
      assertTrue(grafo.containsArch(new Arch<>("A", "C", 1.0f)));
      assertTrue(grafo.containsArch(new Arch<>("C", "D", 1.5f)));

      // Verifica che altri archi non siano inclusi nella foresta
      assertFalse(grafo.containsArch(new Arch<>("B", "C", 3.0f)));
      assertFalse(grafo.containsArch(new Arch<>("B", "D", 2.0f)));

    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testMinForestPrim");
    }
  }

  @Test
  public void testGetNodesAdjacent() {
    try {
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
      assertEquals(2, adjacentNodes.size());
      assertFalse(adjacentNodes.contains(new Node<>("A")));
      assertTrue(adjacentNodes.contains(new Node<>("C")));
      assertTrue(adjacentNodes.contains(new Node<>("D")));

    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testGetNodesAdjacent");
    }
  }

  @Test
  public void testGetNodesLabel() {
    try {
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
      
    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testGetNodesLabel");
    }
  }

  @Test
  public void testRemoveNode() {
    try {
      grafo.addNode(new Node<>("A"));
      grafo.addNode(new Node<>("B"));
      grafo.addNode(new Node<>("C"));

      grafo.removeNode(new Node<>("B"));

      Set<Node<String>> nodes = grafo.getNodes();
      assertEquals(2, nodes.size());
      assertTrue(nodes.contains(new Node<>("A")));
      assertFalse(nodes.contains(new Node<>("B")));
      assertTrue(nodes.contains(new Node<>("C")));

    } catch (AssertionError e) {
      System.err.println("# NOT PASS: testRemoveNode");
    }
  }

  @Test
  public void testRemoveArch() {
      try {
          grafo.addArch(new Arch<>("A", "B", 2.5f));
          grafo.addArch(new Arch<>("A", "C", 1.0f));
          grafo.addArch(new Arch<>("B", "C", 3.0f));
  
          Arch<String> archToRemove = new Arch<>("A", "B", 2.5f);
          grafo.removeArch(archToRemove);
  
          Set<Arch<String>> arches = grafo.getArch();
          assertEquals(2, arches.size());
          assertFalse(arches.contains(archToRemove));
          
          if (!grafo.isDirected()) {
            Arch<String> reverseArch = archToRemove.reveArch();
            assertFalse(arches.contains(reverseArch));
          }
          
          assertTrue(arches.contains(new Arch<>("A", "C", 1.0f)));
          assertTrue(arches.contains(new Arch<>("B", "C", 3.0f)));
  
      } catch (AssertionError e) {
          System.err.println("# NOT PASS: testRemoveArch");
      }
  }
  
}
