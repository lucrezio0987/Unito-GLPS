import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

// Esempio di creazione dinamica di contatori
// Il bottone START crea un nuovo contatore ogni volta
// che viene premuto
// Il bottone ON-OFF ferma o fa ripartire simultaneamente tutti i contatori

// la sincronizzazione avvenga attraverso il lock
// mettendo i thread in wait

public class Contatori extends JFrame {

	private JPanel p2;
	private boolean stop = true;


	private class ThreadCont extends Thread {
		public void run() {
			JTextField t = new JTextField("0",4);
			p2.add(t);
			setVisible(true);
			int count = 0;
			int sleepTime = (int)(Math.random() * 500);
			System.out.println(sleepTime);
		    while (true) {
				passa();
			   try {
				   Thread.sleep(sleepTime);
			   } catch(InterruptedException e) {}
				t.setText(Integer.toString(count++));
		   }
	   }
	}

	public Contatori() {
		JPanel p1 = new JPanel();
		JButton onOff = new JButton("ON-OFF");
		onOff.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cambiaStato();
				}
			});
		p1.add(onOff);
		JButton start = new JButton("START");
				// ad ogni click su START lancio un nuovo thread che genera label e conta
		start.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ThreadCont tc = new ThreadCont();
					tc.start();
				}
			});
		p1.add(start);
		add(p1,"North");

		p2 = new JPanel();
		add(p2,"Center");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,200);
	    setVisible(true);

	}

	synchronized void passa() {
		while (stop)
			try {
				   wait(); // attesa passiva
			     } catch(InterruptedException e) {}
	}

	synchronized void cambiaStato() {
		stop = !stop;
		if (!stop) notifyAll();
	}


	public static void main(String args[]) {
      Contatori f = new Contatori();
   }
}




