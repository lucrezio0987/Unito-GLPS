import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class ObserverContoApp2MVC {

	public static void main(String[] args) {
		ContoBancario cb = new ContoBancario(); // modello
      	FinestraConto f = new FinestraConto();  // prima vista
      	cb.addObserver(f); // aggiungo la prima vista come osservatrice del model conto corrente bancario
        cb.settaSaldoIniziale(0);

      	//IGestisci v = new GestisciOperazioni1();  // prima vista
      	IGestisci v = new GestisciOperazioni2();  // seconda vista
      	Controller c = new Controller(cb, v);   // controller
      	v.setController(c);					    // aggancio il controller alla vista
   	}
}


class ContoBancario extends Observable { // Model
	private int saldo;

	public ContoBancario() {
		saldo = 0;
	}

	public void settaSaldoIniziale(int val) {
		saldo = val;
		setChanged();
		notifyObservers();
	}

   	public void prelievo(int val) {
		saldo -= val;
      	setChanged();
      	notifyObservers();
   	}

   	public void versamento(int val) {
		saldo += val;
      	setChanged();
      	notifyObservers();
   	}

   	public int getSaldo() {
		return saldo;
   	}
}

class FinestraConto extends JFrame implements Observer {
	private JLabel display;
	private JPanel panelConto;

   	public FinestraConto() {
	    super("Conto Corrente");
	    panelConto = new JPanel();
	    add(panelConto);
      	display = new JLabel();
		display.setText("Saldo: ");
      	panelConto.add(display);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	setSize(500,100);
      	setVisible(true);
   	}

   	public void update(Observable ob, Object extra_arg) {
	    if (ob!=null && ob instanceof ContoBancario) {
	   		display.setText("Saldo = " + ((ContoBancario)ob).getSaldo());
		}
   	}
}


interface IGestisci {
	public JTextField getInputVal();
	public void setController(Controller c);
}

// prima versione della GUI
class GestisciOperazioni1 extends JFrame implements IGestisci { // seconda view
	private JButton button;
   	private JTextField inputVal;
   	private JPanel panel;

   	public GestisciOperazioni1() {
		super("GestisciOperazioni");
      	panel = new JPanel();
        panel.setBackground(Color.PINK);
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

// seconda versione della GUI
class GestisciOperazioni2 extends JFrame implements IGestisci { // seconda view
	private JButton button;
   	private JTextField inputVal;
   	private JPanel panel;

   	public GestisciOperazioni2() {
		super("GestisciOperazioni");
      	panel = new JPanel();
        panel.setBackground(Color.YELLOW);
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
	private IGestisci go;
	private JTextField inputVal;

	public Controller(ContoBancario conto, IGestisci view) {
		cb = conto;
		go = view;
		inputVal = view.getInputVal();
	}

	public void actionPerformed(ActionEvent e) {
		int val = 0;
		try {val = Integer.parseInt(inputVal.getText());}
		catch(NumberFormatException ecc)
					{inputVal.setText("errore"); return;}
		cb.versamento(val);
	}
}