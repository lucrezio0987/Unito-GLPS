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
    assertTrue(grafo.isEmpty());
  }

  @Test
  public void testAddNode() {
    grafo.addNode(new Node<>("A"));
    grafo.addNode(new Node<>("B"));
    grafo.addNode(new Node<>("C"));

    assertFalse(grafo.isEmpty());
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

    assertFalse(grafo.isEmpty());
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
  public void testGetArch() {
    grafo.addArch(new Arch<>("A", "B", 2.5f));
    grafo.addArch(new Arch<>("A", "C", 1.0f));
    grafo.addArch(new Arch<>("B", "C", 3.0f));

    Set<Arch<String>> arches = grafo.getArch();
    assertEquals(3, arches.size());

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
    
    assertEquals(6.5f, grafo.getGraphWeight(), 0.001);
  }
}
