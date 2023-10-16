import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// conta i beep

public class Beepernested extends JFrame {
   private JButton button = new JButton("Click Me");
   private JPanel panel = new JPanel();
   private JLabel display = new JLabel("0");
   private int i = 0;

   public Beepernested() {
      panel.add(display);
      panel.add(button);
      add(panel);
      button.addActionListener(new BeepListener());
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      pack();
      setVisible(true);
   }


   public static void main(String[] args) {
      Beepernested beep = new Beepernested();
     // beep.pack(); // inseriamo tutto nel costruttore
     // beep.setVisible(true);
   }


   private class BeepListener implements ActionListener {
// poiché BeepListener è interna a Beepernested riesce ad accedere
// alle sue variabili (i, display) -->
// non serve definire metodi per gestirle, cosa che era nella versione iniziale.
// NB: questa classe NON puo' essere static
       public void actionPerformed(ActionEvent e) {
         Toolkit.getDefaultToolkit().beep();
         i++;
         display.setText(Integer.toString(i));
      }
   } // end BeepListener
}// end BeeperNested