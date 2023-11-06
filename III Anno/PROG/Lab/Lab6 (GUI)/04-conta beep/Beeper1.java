import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// conta i beep

public class Beeper1 extends JFrame {
   private JPanel panel = new JPanel();
   private JButton button = new JButton("Click Me");
   private JLabel display = new JLabel("0");
   private int i = 0;

   public Beeper1() {
      panel.add(display);
      panel.add(button);
      add(panel);
      button.addActionListener(new BeepListener(this));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void incr() {
	   i++;
   }

   public void setDisplay() {
	  display.setText(Integer.toString(i));
   }

   public static void main(String[] args) {
      Beeper1 beep = new Beeper1();
      beep.pack();
      beep.setVisible(true);
   }

}

// qui la classe del listener non è interna a Beeper ==> le si deve passare il Beeper quando la si crea
// per permetterle di invocare i suoi metodi (non accede allo stato del Beeper)
class BeepListener implements ActionListener {
   Beeper1 frame;

   BeepListener (Beeper1 f) {
   	  frame = f;
   }

   public void actionPerformed(ActionEvent e) {
      Toolkit.getDefaultToolkit().beep();
      frame.incr();
      frame.setDisplay();
   }
}
