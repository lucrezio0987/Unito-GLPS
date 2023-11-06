import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class ObserverContoEcc {

	public static void main(String[] args) {
		FinestraConto f = new FinestraConto();

      	ContoBancario cb1 = new ContoBancario();
      	GestisciOperazioni v1 = new GestisciOperazioni(cb1);
      	cb1.addObserver(f);

     	ContoBancario cb2 = new ContoBancario();
      	GestisciOperazioni v2 = new GestisciOperazioni(cb2);
        cb2.addObserver(f);

   	}
}



class ContoBancario extends Observable {
	private static int conti = 0;
   	private int numConto;
   	private int saldo;

   	public ContoBancario() {
		numConto = conti++;
		saldo = 0;
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

   public int getNumero() {
	   return numConto;
   }
}


class FinestraConto extends JFrame implements Observer {
	private JTextArea display;
   	private JScrollPane scrollPane;

   public FinestraConto() {
	   	display = new JTextArea(8,20);
      	scrollPane = new JScrollPane(display);
      	add(scrollPane);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	pack();
      	setVisible(true);
   }

   public void update(Observable ob, Object extra_arg) {
	   	if (ob != null && ob instanceof ContoBancario) {
	   		ContoBancario cb = (ContoBancario)ob;
      		display.append("Conto " + cb.getNumero() + ":   " + cb.getSaldo() + "\n");
		}
   }
}


class GestisciOperazioni extends JFrame implements ActionListener
{  private JButton button;
   private JTextField inputVal;
   private JPanel panel;
   private ContoBancario cb;

   public GestisciOperazioni(ContoBancario conto) {
	   	cb = conto;
      	setTitle("Conto " + cb.getNumero());

      	panel = new JPanel();
      	add(panel);

      	button = new JButton("Versa");
      	panel.add(button);
      	button.addActionListener(this);

      	inputVal = new JTextField("0", 8);
      	panel.add(inputVal);

      	setLocation(conto.getNumero()*200,200);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	pack();
      	setVisible(true);

   }

   public void actionPerformed(ActionEvent e) {
	   	int val = 0;
      	try {
         	val = Integer.parseInt(inputVal.getText());
        } catch(NumberFormatException ecc)
            { inputVal.setText("errore"); return;}
      	cb.versamento(val);
   }
}
