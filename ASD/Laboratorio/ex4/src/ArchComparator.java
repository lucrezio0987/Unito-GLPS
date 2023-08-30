import java.util.Comparator;

public class ArchComparator<E> implements Comparator<Arch<E>> {
    @Override
    public int compare(Arch<E> arch1, Arch<E> arch2) {
        if (arch1.equals(arch2)) {
            return 0;
        }

        Node<E> source1 = new Node<>(arch1.getSorgente());
        Node<E> source2 = new Node<>(arch2.getSorgente());
        
        int compareSource = source1.compareTo(source2);
        if (compareSource != 0) {
            return compareSource;
        }

        Node<E> dest1 = new Node<>(arch1.getDestinazione());
        Node<E> dest2 = new Node<>(arch2.getDestinazione());

        int compareDest = dest1.compareTo(dest2);
        if (compareDest != 0) {
            return compareDest;
        }

        return Float.compare(arch1.getDistance(), arch2.getDistance());
    }
}
