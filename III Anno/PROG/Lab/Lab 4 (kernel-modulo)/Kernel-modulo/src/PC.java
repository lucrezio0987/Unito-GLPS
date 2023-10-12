import java.util.ArrayList;

public class PC {
    private Screens sr;
    private Processors pr;

    public PC(Screens sr, Processors pr) {
        this.sr = sr;
        this.pr = pr;
    }

    public String getConfiguration(){
        return  "Screen:     "+ sr.getType() + " " + sr.getPrice() + "\n" +
                "Processor:  "+ pr.getModel() + " "  + pr.getPrice();
    }

    public float getTotalPrice() {
        return sr.getPrice() + pr.getPrice();
    }
}
