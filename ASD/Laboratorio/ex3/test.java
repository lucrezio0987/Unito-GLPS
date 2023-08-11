package ex3;

import java.util.AbstractQueue;
import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class test {
	
	PriorityQueue<Integer> priorityQueue;

	@Before
	public void createPriorityQueue() {
		priorityQueue = new PriorityQueue<>(Integer::compare);
	}	

	@Test
  public void testIsEmptyZeroEl(){
    assertTrue(priorityQueue.empty());
  }

	@Test
  public void testIsEmptyOneEl() throws Exception{
    priorityQueue.push(1);
    assertFalse(priorityQueue.empty());
  }

	@Test
  public void testTopIfIsEmpty() throws Exception{
		assertEquals(priorityQueue.top(), null);
	}

	@Test
  public void testTop() throws Exception{
    priorityQueue.push(5);
		priorityQueue.push(2);
		priorityQueue.push(6);
		priorityQueue.push(4);

    assertEquals(priorityQueue.top(), 2);
  }

	@Test
  public void testPop() throws Exception{
    priorityQueue.push(5);
		priorityQueue.push(2);
		priorityQueue.push(6);
		priorityQueue.push(4);

		priorityQueue.pop();
		assertEquals(priorityQueue.top(), 4);
		priorityQueue.pop();
		assertEquals(priorityQueue.top(), 5);
		priorityQueue.pop();
		assertEquals(priorityQueue.top(), 6);
	}

}
