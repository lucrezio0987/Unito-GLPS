import javax.swing.*;
//import java.awt.*;
import java.awt.event.*;


public class Two3 extends JFrame {
   private JTextField tf = new JTextField(30);

// qui usiamo classi interne anonime per l'implementaz dei listeners


   public Two3() {
	  super("Esempio con 2 listener - Adapters!");
	  add(new JLabel("Clicca e trascina"), "North");
	  add(tf, "South");
	  addMouseMotionListener(new MouseMotionAdapter() {
   			public void mouseDragged(MouseEvent e) {
	   			String s = "Mouse dragging: X="+e.getX()+"; Y="+e.getY();
	   			tf.setText(s);
			}
  	  });
	  addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				tf.setText("Il mouse è entrato");
			}
			public void mouseExited(MouseEvent e) {
				tf.setText("Il mouse è uscito");
			}
  	  });
	  setSize(400, 300);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }


   public static void main(String[] args) {
      Two3 prova = new Two3();
      prova.setVisible(true);
   }
}
