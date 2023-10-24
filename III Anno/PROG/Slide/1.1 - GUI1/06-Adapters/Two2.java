import javax.swing.*;
//import java.awt.*;
import java.awt.event.*;


public class Two2 extends JFrame {
   private JTextField tf = new JTextField(30);

// qui usiamo classi interne per l'implementaz dei listeners

  private class MA extends MouseAdapter {
	public void mouseEntered(MouseEvent e) {
		tf.setText("Il mouse e' entrato");
	}
	public void mouseExited(MouseEvent e) {
		tf.setText("Il mouse e' uscito");
	}
  }

  private class MMA extends MouseMotionAdapter {
   public void mouseDragged(MouseEvent e) {
	   String s = "Mouse dragging: X="+e.getX()+"; Y="+e.getY();
	   tf.setText(s);
	}
  }

   public Two2() {
	  super("Esempio con 2 listener");
	  add(new JLabel("Clicca e trascina"), "North");
	  add(tf, "South");
	  addMouseMotionListener(new MMA());
	  addMouseListener(new MA());
	  setSize(400, 300);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }


   public static void main(String[] args) {
      Two2 prova = new Two2();
      prova.setVisible(true);
   }
}
