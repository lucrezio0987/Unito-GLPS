
import javax.swing.*;
import java.awt.*;

class ButtonsFrame extends JFrame {
	public ButtonsFrame(String s) {
		super(s);
		setSize(400, 200);
		//setLayout(new FlowLayout()); // posiziona i bottoni centrati
		setLayout(new FlowLayout(FlowLayout.LEFT, 20, 40));  // posiziona i bottoni da sinistra

		JButton b1 = new JButton("schiaccia");
		JButton b2 = new JButton("pigia");
		JButton b3 = new JButton("spingi");
		b1.setBackground(Color.yellow);
		b1.setForeground(Color.red);
		add(b1);
		add(b2);
		add(b3);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}
}

public class FlowLayoutApp {
    public static void main(String[] args) {
        ButtonsFrame frame = new ButtonsFrame("Ciao!");
    }
}
