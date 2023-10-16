
import javax.swing.*;

public class HelloWorldSwing0 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("HelloWorldSwing");
        
        JLabel label = new JLabel("Hello World",JLabel.CENTER);
        frame.add(label);
        
        JLabel label1 = new JLabel("Hello",JLabel.CENTER);
        frame.add(label1);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setLocation(400,200);
        frame.setVisible(true);
    }
}