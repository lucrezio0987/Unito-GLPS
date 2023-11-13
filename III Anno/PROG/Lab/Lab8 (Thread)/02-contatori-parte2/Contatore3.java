import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Facendo creare un nuovo thread al listener (actionPerformed) e ciclando all'infinito in tale thread
// si permette all'interfaccia grafica
// di servire altri eventi

public class Contatore3 extends JFrame {
	private int count = 0;
	private boolean runFlag = true;
	private JButton onOff;
	private JButton start;
	private JTextField t;
	ThreadCont tc;

	public Contatore3() {
		setSize(200,200);
		setLayout(new FlowLayout());

		t = new JTextField(4);
		add(t);

		onOff = new JButton("INCR-DECR");
		add(onOff);
		onOff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println("INCR-DECR");
						runFlag = !runFlag;
					}
			});
		tc = new ThreadCont();
		start = new JButton("START");
		add(start);
		start.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tc.start(); // qui viene creato nuovo thread per gestire il contatore
					}
			});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String args[]) {
		Contatore3 f = new Contatore3();
		System.out.println("Thread del main: "+ Thread.currentThread().getName());
   	}


	private class ThreadCont extends Thread {

		public void run() {
			System.out.println("Thread del contatore con thread aggiuntivo: "+ Thread.currentThread().getName());
			while (true) {
				try {
					Thread.sleep(500);
				} catch(InterruptedException exc)
						{System.out.println("Eccezione: " + exc.getMessage());}
				if (runFlag)
					t.setText(Integer.toString(count++));
				else t.setText(Integer.toString(count--));
				System.out.println(count);
			}
		}
	}// end ContConThread

}
