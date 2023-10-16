import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// conta i beep

public class BeeperNestedAnonima extends JFrame {
   private JButton button = new JButton("Click Me");
   private JPanel panel = new JPanel();
   private JLabel display = new JLabel("0");
   private int i = 0;


// Definisco l'action listener come classe anonima (nested)
// con vantaggio di sintesi del codice

   BeeperNestedAnonima() {
      panel.add(display);
      panel.add(button);
      add(panel);
      button.addActionListener(new ActionListener() {
   			public void actionPerformed(ActionEvent e) {
      			Toolkit.getDefaultToolkit().beep();
      			i++;
      			display.setText(Integer.toString(i));
   			}
			});
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setVisible(true);
   }


   public static void main(String[] args) {
      BeeperNestedAnonima beep = new BeeperNestedAnonima();
   }




}