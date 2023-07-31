package orderedarray;

import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * It specifies a test suite for the Ordered Array library
 * To improve readability, Java methods' naming conventions are not fully
 * respected...
 * @author magro
 */
public class OrderedArrayTests {
  
  class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i1.compareTo(i2);
    }
  }
  
  
  private Integer i1,i2,i3;
  private OrderedArray<Integer> orderedArray;
  
  @Before
  public void createOrderedArray() throws OrderedArrayException{
    i1 = -12;
    i2 = 0;
    i3 = 4;
    orderedArray = new OrderedArray<>(new IntegerComparator());
  }
  
  @Test
  public void testIsEmptyZeroEl(){
    assertTrue(orderedArray.isEmpty());
  }
  
  @Test
  public void testIsEmptyOneEl() throws Exception{
    orderedArray.add(i1);
    assertFalse(orderedArray.isEmpty());
  }
  
  
  @Test
  public void testSizeZeroEl() throws Exception{
    assertEquals(0,orderedArray.size());
  }
  
  @Test
  public void testSizeOneEl() throws Exception{
    orderedArray.add(i1);
    assertEquals(1,orderedArray.size());
  }
  
  @Test
  public void testSizeTwoEl() throws Exception{
    orderedArray.add(i1);
    orderedArray.add(i2);
    assertEquals(2,orderedArray.size());
  }
  
  @Test
  //It directly accesses the OrderedArray instance variable orderedArray.array
  public void testAddOneEl() throws Exception{
    orderedArray.add(i1);
    assertEquals(i1, orderedArray.array.get(0));
  }
  
  @Test
  //It directly accesses the OrderedArray instance variable orderedArray.array
  public void testAddThreeElAddedInOrder() throws Exception{
    Integer[] expectedArray = {i1,i2,i3};
    orderedArray.add(i1);
    orderedArray.add(i2);
    orderedArray.add(i3);
    assertArrayEquals(expectedArray, orderedArray.array.toArray());
  }

  @Test
  //It directly accesses the OrderedArray instance variable orderedArray.array
  public void testAddThreeElAddedInReverseOrder() throws Exception{
    Integer[] expectedArray = {i1,i2,i3};
    orderedArray.add(i3);
    orderedArray.add(i2);
    orderedArray.add(i1);
    assertArrayEquals(expectedArray, orderedArray.array.toArray());
  }

  @Test
  //It directly accesses the OrderedArray instance variable orderedArray.array
  public void testAddThreeElAddedInNoOrder() throws Exception{
    Integer[] expectedArray = {i1,i2,i3};
    orderedArray.add(i2);
    orderedArray.add(i3);
    orderedArray.add(i1);
    assertArrayEquals(expectedArray, orderedArray.array.toArray());
  }

  @Test
  //It directly accesses the OrderedArray instance variable orderedArray.array
  public void testAddThreeElTwoEqual() throws Exception{
    Integer[] expectedArray = {i2,i2,i3};
    orderedArray.add(i2);
    orderedArray.add(i3);
    orderedArray.add(i2);
    assertArrayEquals(expectedArray, orderedArray.array.toArray());
  }

  @Test
  //It directly accesses the OrderedArray instance variable orderedArray.array
  public void testAddThreeElAllEqual() throws Exception{
    Integer[] expectedArray = {i2,i2,i2};
    orderedArray.add(i2);
    orderedArray.add(i2);
    orderedArray.add(i2);
    assertArrayEquals(expectedArray, orderedArray.array.toArray());
  }

  @Test
  public void testAddGetOneEl() throws Exception{
    orderedArray.add(i1);
    assertEquals(i1,orderedArray.get(0));
  }
  
  @Test
  public void testAddGetThreeElFirst() throws Exception{
    orderedArray.add(i2);
    orderedArray.add(i1);
    orderedArray.add(i3);
    assertEquals(i1,orderedArray.get(0));
  }

  @Test
  public void testAddGetThreeElSecond() throws Exception{
    orderedArray.add(i2);
    orderedArray.add(i1);
    orderedArray.add(i3);
    assertEquals(i2,orderedArray.get(1));
  }

  @Test
  public void testAddGetThreeElThird() throws Exception{
    orderedArray.add(i2);
    orderedArray.add(i1);
    orderedArray.add(i3);
    assertEquals(i3,orderedArray.get(2));
  }
 
}

