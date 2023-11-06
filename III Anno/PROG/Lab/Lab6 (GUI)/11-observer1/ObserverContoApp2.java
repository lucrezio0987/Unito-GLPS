import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


// qui il legame tra osservato e osservatore avviene attraverso l'uso del parametro Observable del metodo update;
public class ObserverContoApp2 {

	public static void main(String[] args) {
		ContoBancario cb = new ContoBancario();
      	FinestraConto f = new FinestraConto();
      	cb.addObserver(f); // aggiunge l'osservatore all'oggetto Observable ContoBancario
      	GestisciOperazioni v = new GestisciOperazioni(cb);
   	}
}


class ContoBancario extends Observable {
	private int saldo = 0;

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

   public FinestraConto() {
	    super("Conto Corrente");
      	display = new JLabel();
		display.setText("Saldo = ");
      	add(display);
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

class GestisciOperazioni extends JFrame {
	private JButton button;
   	private JTextField inputVal;
   	private JPanel panel;
   	private ContoBancario cb;

   	public GestisciOperazioni(ContoBancario conto) {
		super("GestisciOperazioni");
		cb = conto;
      	panel = new JPanel();
      	add(panel);

      	button = new JButton("Versa");
      	panel.add(button);
      	button.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
				   						int val = Integer.parseInt(inputVal.getText());
			      						cb.versamento(val);
   									}
									});
      	inputVal = new JTextField("0", 8);
      	panel.add(inputVal);

      	setLocation(100,100);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	setSize(400,100);
      	setVisible(true);

   	}
}