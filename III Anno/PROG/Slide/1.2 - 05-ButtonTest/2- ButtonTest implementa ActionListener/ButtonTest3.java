/**
   @version 1.32 2004-05-04
   @author Cay Horstmann
*/

// ButtonPanel implementa l'ActionListener

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ButtonTest3 {
   public static void main(String[] args) {
      ButtonFrame frame = new ButtonFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }
}

/**
   A frame with a button panel
*/
class ButtonFrame extends JFrame {
   public static final int DEFAULT_WIDTH = 300;
   public static final int DEFAULT_HEIGHT = 200;

   public ButtonFrame() {
      setTitle("ButtonTest");
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // add panel to frame
      ButtonPanel panel = new ButtonPanel();
      add(panel);
   }
}

/**
   A panel with three buttons.
*/
class ButtonPanel extends JPanel implements ActionListener {
   public ButtonPanel() {
      // create buttons
      JButton yellowButton = new JButton("Yellow");
      JButton blueButton = new JButton("Blue");
      JButton redButton = new JButton("Red");

      // add buttons to panel
      add(yellowButton);
      add(blueButton);
      add(redButton);

      // associate actions with buttons (il panel stesso funge da action listener (this))
      yellowButton.addActionListener(this);
      blueButton.addActionListener(this);
      redButton.addActionListener(this);
   }


   public void actionPerformed(ActionEvent event) {
      String command = event.getActionCommand(); // restituisce la label del bottone sorgente dell'evento
      if (command.equals("Yellow")) setBackground(Color.YELLOW);
      else if (command.equals("Blue")) setBackground(Color.BLUE);
      else if (command.equals("Red")) setBackground(Color.RED);
   }
}



