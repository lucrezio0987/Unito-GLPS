import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


// visualizza a video lo stato del conto corrente utilizzando direttamente lo standard output (System.out.println())
public class ObserverContoApp0 {

	public static void main(String[] args) {
		ContoBancario cb = new ContoBancario();
      	MostraConto f = new MostraConto();
      	GestisciOperazioni v = new GestisciOperazioni(cb);
      	cb.addObserver(f); // aggiunge l'osservatore all'oggetto Observable ContoBancario
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

class MostraConto implements Observer {

   public void update(Observable ob, Object extra_arg) {
	   	if (ob!=null && ob instanceof ContoBancario) {
			ContoBancario cb = (ContoBancario) ob;
			System.out.println("Saldo = " + cb.getSaldo());
		}
   }
}

class GestisciOperazioni extends JFrame {
	private JButton button;
   	private JTextField inputVal;
   	private JPanel panel;
   	private ContoBancario cb;

   	public GestisciOperazioni(ContoBancario conto) {
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
      	setSize(200,100);
      	setVisible(true);

   	}
}