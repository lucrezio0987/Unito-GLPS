/**
   @version 1.32 2004-05-04
   @author Cay Horstmann
*/

// ButtonPanel implementa l'ActionListener

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ButtonTest4 {
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
class ButtonPanel extends JPanel {
   public ButtonPanel() {
      // create buttons
      JButton yellowButton = new JButton("Yellow");
      JButton blueButton = new JButton("Blue");
      JButton redButton = new JButton("Red");

      // add buttons to panel
      add(yellowButton);
      add(blueButton);
      add(redButton);

      yellowButton.addActionListener(new ActionListener() {
		  			public void actionPerformed(ActionEvent event) {
      					setBackground(Color.YELLOW);
   					}
   					});
      blueButton.addActionListener(new ActionListener() {
		  			public void actionPerformed(ActionEvent event) {
      					setBackground(Color.BLUE);
   					}
   					});
      redButton.addActionListener(new ActionListener() {
		  			public void actionPerformed(ActionEvent event) {
      					setBackground(Color.RED);
   					}
   					});
   }



}



