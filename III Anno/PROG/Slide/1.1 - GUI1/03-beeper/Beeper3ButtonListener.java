import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Beeper3ButtonListener extends JFrame {
   private BeeperButton button;
   private JPanel panel;

   public Beeper3ButtonListener() {
      panel = new JPanel();
      add(panel);
      button = new BeeperButton("Click Me");
      panel.add(button);
      pack();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }

   public static void main(String[] args) {
      Beeper3ButtonListener beep = new Beeper3ButtonListener();
   }

// Il bottone è listener di se stesso, cioè contiene
// il listener per gestire gli eventi che genera.
	private static class BeeperButton extends JButton implements ActionListener {
		private BeeperButton(String s) {
			super(s);
	  		addActionListener(this); // aggiunge action listener a se stesso
		}
		public void actionPerformed(ActionEvent e) {
      		Toolkit.getDefaultToolkit().beep();
      		System.out.println("BEEP!");
   		}
	}
}
