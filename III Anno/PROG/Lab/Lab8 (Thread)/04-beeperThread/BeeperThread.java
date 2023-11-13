import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// conta i beep - Stampa i thread

public class BeeperThread extends JFrame {
	private JButton button = new JButton("Click Me");
	private JPanel panel = new JPanel();
	private JLabel display = new JLabel("0");
	private int i = 0;

	BeeperThread() {
		System.out.println("Costruttore nel thread " + Thread.currentThread().getName());
		System.out.println("Priorita' " + Thread.currentThread().getPriority());
		panel.add(display);
		panel.add(button);
		add(panel);
		button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Listener nel thread " + Thread.currentThread().getName());
					System.out.println("Priorita' " + Thread.currentThread().getPriority());
					Toolkit.getDefaultToolkit().beep();
					i++;
					display.setText(Integer.toString(i));
				}
			});
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.out.println("Listener nel thread " + Thread.currentThread().getName());
					System.out.println("Priorita' " + Thread.currentThread().getPriority());
					System.exit(0);
				}
			});
	}

	public static void main(String[] args) {
		System.out.println("Main nel thread " + Thread.currentThread().getName());
									// crea primo beeper
		BeeperThread beep = new BeeperThread();
		beep.setLocation(40, 40);
		beep.pack();
		beep.setVisible(true);
									// crea secondo beeper
		BeeperThread beep2 = new BeeperThread();
		beep.setLocation(90, 90);
		beep2.pack();
		beep2.setVisible(true);
	}
}