public abstract class Processors implements Part{
    public String getModel() {
        return getClass().getName();
    }
}