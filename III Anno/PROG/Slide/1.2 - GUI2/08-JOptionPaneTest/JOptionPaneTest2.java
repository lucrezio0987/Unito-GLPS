
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JOptionPaneTest2 {

    public static void main(String[] args) {
        MenuFrame frame = new MenuFrame("Raddoppia");
		frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class MenuFrame extends JFrame {

	private JTextArea risultato;


	public MenuFrame(String name) {
		String dato = JOptionPane.showInputDialog("Inserisci il numero da raddoppiare:");
		risultato = new JTextArea("Risultato: ");
		risultato.setBackground(Color.YELLOW);
		risultato.setForeground(Color.BLACK);
		add(risultato);

		int val = 0;
		try {
	        val = Integer.parseInt(dato);
			} catch (Exception ecc) {System.out.println(ecc.getMessage());}
		val = 2*val;
	    risultato.setText((new Integer(val)).toString());
	    System.out.println(dato + "*2 = " + risultato.getText());

	}

}





