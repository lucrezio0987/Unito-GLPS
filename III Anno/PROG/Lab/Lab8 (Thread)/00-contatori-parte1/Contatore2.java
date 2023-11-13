import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Il contatore viene incrementato o decrementato
// a seconda del valore di runFlag
// Il bottone può  cambiare il valore di runFlag
// anche se il main sta eseguendo una computazione infinita.
// Questo perché il thread della GUI è separato da quello del main.

public class Contatore2 {
	private static boolean runFlag = true;

	public static void main(String[] argv) {
	 	JFrame f = new JFrame();
		f.setLayout(new FlowLayout());

	  	JTextField t = new JTextField(4);
		f.add(t);

		JButton onOff = new JButton("INCR-DECR");
		f.add(onOff);
		onOff.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					runFlag = !runFlag;
					System.out.println("Thread del listener: "+ Thread.currentThread().getName());
				}
			});
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
      	f.setVisible(true);

	 	int count = 0;
		System.out.println("Thread del main: "+ Thread.currentThread().getName());

		// il main entra in un ciclo infinito
      	while (true) {
			try {
				Thread.sleep(500);
			} catch(InterruptedException exc)
					{System.out.println("Eccezione: "+ exc.getMessage());}
			if (runFlag)
				t.setText(Integer.toString(count++));
			else t.setText(Integer.toString(count--));
		}
	}
}
