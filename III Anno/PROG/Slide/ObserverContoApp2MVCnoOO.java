import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class ObserverContoApp2MVCnoOO {

	public static void main(String[] args) {
		ContoBancario cb = new ContoBancario(); // modello
      	FinestraConto f = new FinestraConto(cb);  // prima vista
      	GestisciOperazioni v = new GestisciOperazioni();  // seconda vista
      	Controller c = new Controller(cb, v, f);   // controller
      	v.setController(c);					    // aggancio il controller alla vista
   	}
}


class ContoBancario { // Model
	private int saldo;

	public ContoBancario() {
		saldo = 0;
	}
   	public void versamento(int val) {
		saldo += val;
   	}
   	public int getSaldo() {
		return saldo;
   	}
}

class FinestraConto extends JFrame { // prima view
	private JLabel display;
	private JPanel panelConto;
	private ContoBancario cc;

   	public FinestraConto(ContoBancario c) {
	    super("Conto Corrente");
	    cc = c;
	    panelConto = new JPanel();
	    panelConto.setBackground(Color.WHITE);
	    add(panelConto);
      	display = new JLabel();
		display.setText("Saldo: ");
      	panelConto.add(display);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	setSize(500,100);
      	setVisible(true);
   	}

   	public void updateView() {
	   	display.setText("Saldo = " + cc.getSaldo());
   	}
}


class GestisciOperazioni extends JFrame { // seconda view
	private JButton button;
   	private JTextField inputVal;
   	private JPanel panel;

   	public GestisciOperazioni() {
		super("GestisciOperazioni");
      	panel = new JPanel();
      	add(panel);

      	button = new JButton("Versa");
      	panel.add(button);
      	inputVal = new JTextField("0", 8);
      	panel.add(inputVal);

      	setLocation(100,100);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	setSize(400,100);
      	setVisible(true);

   	}

   	public JTextField getInputVal() {
		return inputVal;
	}

	public void setController(Controller c) {
		button.addActionListener(c); // aggancia il listener del controller al bottone
	}
}

class Controller implements ActionListener {
	private ContoBancario cb;
	private GestisciOperazioni go;
	private FinestraConto fc;
	private JTextField inputVal;

	public Controller(ContoBancario conto, GestisciOperazioni view, FinestraConto f) {
		cb = conto;
		go = view;
		fc = f;
		inputVal = view.getInputVal();
	}

	public void actionPerformed(ActionEvent e) {
		int val = 0;
		try {val = Integer.parseInt(inputVal.getText());}
		catch(NumberFormatException ecc)
					{inputVal.setText("errore"); return;}
		cb.versamento(val);
		fc.updateView();
	}
}