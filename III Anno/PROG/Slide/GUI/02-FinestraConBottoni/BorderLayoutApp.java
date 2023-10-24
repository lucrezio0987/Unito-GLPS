
import javax.swing.*;
import java.awt.*;

class BLConJFrame {

	private JFrame f = new JFrame("Il frame");
	private JButton b1 = new JButton("UNO");
	private JButton b2 = new JButton("DUE");
	private JButton b3 = new JButton("TRE");
	private JButton b4 = new JButton("QUATTRO");
	private JButton b5 = new JButton("CINQUE");



	public BLConJFrame() {
		f.setSize(400, 200);
		f.add(b1, BorderLayout.NORTH);
		//f.add(b2, BorderLayout.SOUTH);
		f.add(b3, "West");
		f.add(b4, "East");
		f.add(b5, "Center"); // per default posiziona i bottoni al centro
		//f.pack(); // se non fisso dimensioni iniziali con setSize crea JFrame di dim suff per contenere i bottoni
		f.setVisible(true);
	}
}

public class BorderLayoutApp {
    public static void main(String[] args) {
        BLConJFrame finestra = new BLConJFrame();
    }
}
