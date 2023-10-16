import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// conta i beep
// implementa il listener
// Beeper2 (la finestra) fa da listener del bottone

public class Beeper2 extends JFrame implements ActionListener {
   private JButton button = new JButton("Click Me");
   private JPanel panel = new JPanel();
   private JLabel display = new JLabel("0");
   private int i = 0;

   Beeper2() {
      panel.add(display);
      panel.add(button);
      add(panel);
      button.addActionListener(this);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void actionPerformed(ActionEvent e) {
      Toolkit.getDefaultToolkit().beep();
      i++;
      display.setText(Integer.toString(i));
   }


   public static void main(String[] args) {
      Beeper2 beep = new Beeper2();
      beep.pack();
      beep.setVisible(true);
   }
}

