import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// L'esecuzione del metodo actionPerformed del listener
// del bottone START non termina
// Questo fa si' che il thread dell'interfaccia grafica
// non possa servire altri eventi. NB: tutti i listener dell'interfaccia
// grafica sono eseguiti dallo stesso thread di interfaccia (vd. le stampe)

public class Contatore1 extends JFrame {
	private int count;
	private boolean runFlag;
	private JButton onOff;
	private JButton start;
	private JLabel t;

	public Contatore1() {
		runFlag = true;
		count = 0;

		setSize(200,200);
		setLayout(new FlowLayout());

		t = new JLabel("0");
		add(t);

		onOff = new JButton("INCR-DECR");
		add(onOff);
		onOff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						runFlag = !runFlag;
						System.out.println("Thread del listener INCR-DECR: "+ Thread.currentThread().getName());
					}
				});
		start = new JButton("START");
		add(start);
		start.addActionListener(new StartL());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	public static void main(String args[]) {
		Contatore1 f = new Contatore1();
		System.out.println("Thread del main: "+ Thread.currentThread().getName());
   }


	class StartL implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("Thread del listener START: "+ Thread.currentThread().getName());
			while (true) {
				try {
					Thread.sleep(500);
				} catch(InterruptedException exc)
					{System.out.println("Eccezione: "+ exc.getMessage());}
				if (runFlag)
					t.setText(Integer.toString(count++));
				else t.setText(Integer.toString(count--));
				System.out.println(count);
			}
		}
	}// end StartL

}
