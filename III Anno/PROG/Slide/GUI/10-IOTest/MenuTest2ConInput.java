
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuTest2ConInput {

    public static void main(String[] args) {
        MenuFrame frame = new MenuFrame("operazioni");
    }
}

class MenuFrame extends JFrame {

	private class MyJMenuItem extends JMenuItem {

		public MyJMenuItem(String label) {
			super(label);
		  	addActionListener(new ActionListener() {
	  							public void actionPerformed(ActionEvent e) {
									op = e.getActionCommand();
	    							System.out.println("Operazione scelta: " + op);
	    						}
							});
		}
	}


	private JTextArea input1;
	private JTextArea input2;
	private String op;
	private JTextArea risultato;


	public MenuFrame(String name) {
		setLayout(new GridLayout(4, 2, 20, 50)); // rows, cols, hgap (distanza orizzontale tra colonne), vgap (dist verticale)

		JMenuBar bar = new JMenuBar();
		add(bar);
		JMenu operazione = new JMenu(name);
		bar.add(operazione);
		operazione.add(new MyJMenuItem("somma"));
		operazione.add(new MyJMenuItem("prodotto"));
		operazione.add(new MyJMenuItem("sottrazione"));

		add(new JLabel(""));
		add(new JLabel("Dato1: "));
		input1 = new JTextArea("");
		input1.setBackground(Color.BLUE);
		input1.setForeground(Color.WHITE);
		add(input1);

		add(new JLabel("Dato2: "));
		input2 = new JTextArea("");
		input2.setBackground(Color.BLUE);
		input2.setForeground(Color.WHITE);
		add(input2);

		JButton inputButton = new JButton("Submit");
		add(inputButton);
		inputButton.addActionListener(new MenuFrameListener());

		risultato = new JTextArea("Risultato: ");
		risultato.setBackground(Color.YELLOW);
		risultato.setForeground(Color.BLACK);
		add(risultato);

	    setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}


  private class MenuFrameListener implements ActionListener {

	public void actionPerformed(ActionEvent e) { // qui distinguere somma da prodotto etc.... ora fa solo la somma!
			int uno = 0;
			try {
	        	uno = Integer.parseInt(input1.getText());
			} catch (Exception ecc) {System.out.println(ecc.getMessage());}
	        int due = 0;
	        try {
				due = Integer.parseInt(input2.getText());
			} catch (Exception ecc) {System.out.println(ecc.getMessage());}
			int ris = 0;
			if (op==null)
				ris = 0;
			else {
				switch(op) {
					case "somma":
						ris = uno + due;
						break;
					case "prodotto":
						ris = uno * due;
						break;
					default:
						ris = uno-due;
						break;
				}
			}
	        risultato.setText((new Integer(ris)).toString());
	        System.out.println(uno + op + due + " = " + risultato.getText());
    }
  }
}





