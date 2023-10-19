import java.util.*;

class Filter {

    private List<Integer> list;
    Visualizer visualizer;

    public Filter(Visualizer v) {
        visualizer = v;
        list = new ArrayList<Integer>();
    }

    public void filter(int c) {
        list.add(c);
        if (list.size() % 2 == 0) {

            System.out.println("lista size: " + list.size());

            visualizer.visualize(list);
        }
    }
}
