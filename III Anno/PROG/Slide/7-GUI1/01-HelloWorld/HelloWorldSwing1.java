
import javax.swing.*;
import java.awt.*;

public class HelloWorldSwing1 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setLayout(new FlowLayout()); // posiziona i bottoni in modo sequenziale, centrati
        JLabel label = new JLabel("Hello World");
        frame.add(label);
        JLabel label1 = new JLabel("Altro Hello World");
        frame.add(label1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // fa chiudere la finestra quando il main termina
        frame.pack(); // dimensiona la finestra in modo ottimale rispetto al contenuto
        frame.setVisible(true);
    }
}
