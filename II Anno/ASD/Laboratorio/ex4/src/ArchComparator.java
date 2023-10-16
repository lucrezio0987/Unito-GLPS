import java.util.Comparator;

public class ArchComparator<E> implements Comparator<Arch<E>> {
    @Override
    public int compare(Arch<E> arch1, Arch<E> arch2) {
		  return Float.compare(arch1.getDistance(), arch2.getDistance());
    }
}
