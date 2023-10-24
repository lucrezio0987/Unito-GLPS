
import javax.swing.*;
import java.awt.*;

class GLConJFrame {

	private JFrame f = new JFrame("Il frame");
	private JButton b1 = new JButton("UNO");
	private JButton b2 = new JButton("DUE");
	private JButton b3 = new JButton("TRE");
	private JButton b4 = new JButton("QUATTRO");
	private JButton b5 = new JButton("CINQUE");



	public GLConJFrame() {
		//f.setLayout(new GridLayout(3, 2));
		f.setLayout(new GridLayout(3, 2, 20, 50)); // rows, cols, hgap (distanza orizzontale tra colonne), vgap (dist verticale)
		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(b4);
		f.add(b5);
		f.pack(); // se non fisso dimensioni iniziali con setSize crea JFrame di dim suff per contenere i bottoni
		f.setVisible(true);
	}
}

public class GridLayoutApp {
    public static void main(String[] args) {
        GLConJFrame finestra = new GLConJFrame();
    }
}
