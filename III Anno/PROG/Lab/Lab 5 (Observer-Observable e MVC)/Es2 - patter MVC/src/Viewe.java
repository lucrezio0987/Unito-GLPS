import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class Viewe extends JFrame implements Observer {
    JFrame frame;
    JButton button;
    JPanel panel;
    JLabel label;
    String proverbio = "vuoto";

    public Viewe(){
        frame = new JFrame("Es2");
        panel = new JPanel();
        button = new JButton("Click Me");
        label = new JLabel(proverbio);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.add(button);
        panel.add(label);

        button.addActionListener(new Controller(new Model()));

    }
    public void start() {
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof Model) {
            label.setText(((Model) o).getProverio());
        }
    }
}
