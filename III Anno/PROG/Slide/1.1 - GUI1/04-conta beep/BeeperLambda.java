import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// conta i beep

public class BeeperLambda extends JFrame {
   private JButton button = new JButton("Click Me");
   private JPanel panel = new JPanel();
   private JLabel display = new JLabel("0");
   private int i = 0;

   BeeperLambda() {
      panel.add(display);
      panel.add(button);
      add(panel);
      ActionListener listener = event -> {
		  		Toolkit.getDefaultToolkit().beep();
		        i++;
      			display.setText(Integer.toString(i));
				};
      button.addActionListener(listener);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setVisible(true);
   }


   public static void main(String[] args) {
      BeeperLambda beep = new BeeperLambda();
   }
}