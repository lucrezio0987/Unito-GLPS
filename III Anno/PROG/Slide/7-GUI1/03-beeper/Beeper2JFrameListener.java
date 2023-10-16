import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Posso far implementare ActionListener direttamente al Beeper2 (alla finestra)
// per non creare classe separata (interna)
public class Beeper2JFrameListener extends JFrame implements ActionListener{
   private JButton button;
   private JPanel panel;

   public Beeper2JFrameListener() {
      panel = new JPanel();
      add(panel);
      button = new JButton("Click Me");
      panel.add(button);
      pack();
      button.addActionListener(this); // aggiunge action listener al bottone
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }

   public void actionPerformed(ActionEvent e) {
         		Toolkit.getDefaultToolkit().beep();
         		System.out.println("BEEP!");
   }

   public static void main(String[] args) {
      Beeper2JFrameListener beep = new Beeper2JFrameListener();
   }
}
