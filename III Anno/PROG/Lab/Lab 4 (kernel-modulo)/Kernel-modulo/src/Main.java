import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PC pc1 = new PC(new LCD(), new Intel());
        PC pc2 = new PC(new Touch(), new AMD());
        PC pc3 = new PC(new LCD(), new AMD());

        System.out.println("\n PC1: ");
        System.out.println(pc1.getConfiguration());
        System.out.println("Tot: " + pc1.getTotalPrice());

        System.out.println("\n PC2: ");
        System.out.println(pc2.getConfiguration());
        System.out.println("Tot: " + pc2.getTotalPrice());

        System.out.println("\n PC3: ");
        System.out.println(pc3.getConfiguration());
        System.out.println("Tot: " + pc3.getTotalPrice());
    }
}