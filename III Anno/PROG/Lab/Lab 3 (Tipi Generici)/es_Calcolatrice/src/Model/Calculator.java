package Model;
import java.util.List;

public class Calculator {
    public static <T extends Comparable<T>> void print(List <? extends Element<T>> list) {
        if (list != null) {
            for (Element<T> el : list)
                System.out.print(el + " ");
        }
    }
    public static <T extends Comparable<T>> Element<T> sum (List <? extends Element<T>> list){
        if (list == null || list.isEmpty()) {
            return null;
        }
        Element<T> sum = list.get(0);
        for (int i = 1; i < list.size(); ++i) {
            sum = sum.add(list.get(i));
        }
        return sum;
    }
    public static <T extends Comparable<T>> Element<T> max(List <? extends Element<T>> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Element<T> max = list.get(0);
        for (int i = 1; i < list.size(); ++i) {
            if (max.compareTo(list.get(i)) < 0)
                max = list.get(i);
        }
        return max;
    }
}
