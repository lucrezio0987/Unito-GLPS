package Model;
public abstract class Element<T extends Comparable<T>> implements Comparable<Element <T>>{
    T value;
    public Element(T element) {
        this.value = element;
    }
    public abstract Element<T> add(Element<T> value);

    public int compareTo(Element<T> element) {
        return value.compareTo(element.value);
    }

    public String toString() {
        return value.toString();
    }

    public static class classInt extends Element<Integer>{
        public classInt(Integer element) {
            super(element);
        }

        public Element<Integer> add(Element<Integer> element) {
            classInt el = (classInt) element;
            return new classInt(value + el.value);
        }
    }

    public static class classDouble extends Element<Double> {
        public classDouble(Double element) {
            super(element);
        }

        public Element<Double> add(Element<Double> element) {
            return new classDouble(value + element.value);
        }
    }

    public static class classString extends Element<String> {
        public classString(String element) {
            super(element);
        }

        public Element<String> add(Element<String> element) {
            return new classString(value + element.value);
        }
    }
}




