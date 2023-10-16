import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Beeper6Lambda extends JFrame {
   private JButton button;
   private JPanel panel;

   public Beeper6Lambda() {
      panel = new JPanel();
      add(panel);
      button = new JButton("Click Me");
      ActionListener listener = event -> {Toolkit.getDefaultToolkit().beep();
		        						  System.out.println("BEEP!");};
	  button.addActionListener(listener);
      /*button.addActionListener(new ActionListener() { // classe anonima
		  						public void actionPerformed(ActionEvent e) {
		        					Toolkit.getDefaultToolkit().beep();
		        					System.out.println("BEEP!");
		 						}
   	  						   });*/
      panel.add(button);
      pack();
      			// aggiungo listener - classe anonima
      addWindowListener(new WindowAdapter() {
   								public void windowClosing(WindowEvent e) {
									System.exit(0);
   								}
   							});
   }

   public static void main(String[] args) {
      Beeper6Lambda beep = new Beeper6Lambda();
      beep.setVisible(true);
   }
}
