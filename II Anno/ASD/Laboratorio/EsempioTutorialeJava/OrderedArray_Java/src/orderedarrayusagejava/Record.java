package orderedarrayusagejava;

import java.util.Objects;
import orderedarray.OrderedArrayException;

/**
 *
 * @author magro
 */
public class Record {
  private String stringField = null;
  private int integerField;
  
  
  public Record(String stringField,int integerField) throws OrderedArrayException{
    if(stringField == null) throw new OrderedArrayException("Record constructor: stringField parameter cannot be null");
    this.stringField = stringField;
    this.integerField = integerField;
  }
  
  public String getStringField(){
    return this.stringField;
  }
  
  public int getIntegerField(){
    return this.integerField;
  }
  
  
  @Override
  //[COMMENTO IN ITALIANO PER SOLI SCOPI DIDATTICI, 
  //DA NON CONSIDERARSI PARTE DELL'IMPLEMENTAZIONE:
  //guardare i pro e contro dell'uso di "instanceof" 
  //vs Object.getClass() nell'overriding di Object.equals().
  //In particolare, si presti attenzione a come l'uso di
  //"instanceof", combinato con un ulteriore overriding
  //di Object.equals() in una sottoclasse, potrebbe compromettere
  //la simmetria di Object.equals(). Una soluzione
  //(non sempre adeguata) è dichiarare "final" l'overriding
  //di equals nella sovraclasse.]
  public final boolean equals(Object rec){
    if(rec==null)
	  return false;
    if(!(rec instanceof Record))
      return false;
    if(this == rec)
    	return true;
    Record other = (Record)rec;
    return (this.stringField.equalsIgnoreCase(other.getStringField()) && 
    		this.integerField == other.getIntegerField());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.stringField.toLowerCase(),this.integerField);
  }
}

