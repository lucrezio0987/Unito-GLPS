
import javax.swing.*;

class MyFrame extends JFrame {
	public MyFrame(String s) {
		super(s);
		setSize(400, 400);
	}
}

public class FinestraApp1 {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame("Ciao!");

		// se aggiungo componenti direttamente al pannello, senza layout manager,
		// non vengono ben visualizzati:
        JLabel label1 = new JLabel("Hello World");
        frame.add(label1);
        JLabel label2 = new JLabel("Hello W");
        frame.add(label2);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
