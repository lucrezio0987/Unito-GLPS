/**
   @version 1.32 2004-05-04
   @author Cay Horstmann
*/

// ButtonTest senza classi interne

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ButtonTest1 {
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

      // create button actions
      ColorAction yellowAction = new ColorAction(Color.YELLOW,this);
      ColorAction blueAction = new ColorAction(Color.BLUE,this);
      ColorAction redAction = new ColorAction(Color.RED,this);

      // associate actions with buttons
      yellowButton.addActionListener(yellowAction);
      blueButton.addActionListener(blueAction);
      redButton.addActionListener(redAction);
   }
}// end ButtonPanel


/**
	      An action listener that sets the panel's background color.
	*/
class ColorAction implements ActionListener {
	private Color backgroundColor;
	private ButtonPanel bp;

	public ColorAction(Color c, ButtonPanel p) {
		backgroundColor = c;
		bp = p;
	}

	public void actionPerformed(ActionEvent event) {
		bp.setBackground(backgroundColor);
	}
} // end ColorAction





