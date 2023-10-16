import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Beeper5NestedClasses extends JFrame {
   private JButton button;
   private JPanel panel;

   public Beeper5NestedClasses() {
      panel = new JPanel();
      add(panel);
      button = new JButton("Click Me");
      button.addActionListener(new ActionListener() { // classe anonima
		  						public void actionPerformed(ActionEvent e) {
		        					Toolkit.getDefaultToolkit().beep();
		        					System.out.println("BEEP!");
		 						}
   	  						   });
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
      Beeper5NestedClasses beep = new Beeper5NestedClasses();
      beep.setVisible(true);
   }
}
