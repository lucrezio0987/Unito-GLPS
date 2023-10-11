import java.util.Comparator;

public class ArchComparator<E> implements Comparator<Arch<E>> {

	@Override
	public int compare(Arch<E> arch1, Arch<E> arch2) {
	  if (arch1.equals(arch2))
	    return 0;
		
	  int compareSource = arch1.getSorgente().compareTo(arch2.getSorgente());
	  if (compareSource != 0) 
	      return compareSource;

		int compareDest = arch1.getDestinazione().compareTo(arch2.getDestinazione());
		if (compareDest != 0)
	    return compareDest;

		return Float.compare(arch1.getDistance(), arch2.getDistance());
	}
}