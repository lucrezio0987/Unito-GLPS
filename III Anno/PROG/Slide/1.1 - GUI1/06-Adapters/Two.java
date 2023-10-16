import javax.swing.*;
//import java.awt.*;
import java.awt.event.*;


public class Two extends JFrame implements MouseListener, MouseMotionListener {
   private JTextField tf = new JTextField(30);

// qui implementiamo direttamente le interfacce ma vedremo che ci sono controindicaz (impossibile usare gli adapter)
// per via dell'ereditarietà che non può essere multipla


   public Two() {
	  super("Esempio con 2 listener");
	  add(new JLabel("Clicca e trascina"), "North");
	  add(tf, "South");
	  addMouseMotionListener(this);
	  addMouseListener(this);
	  setSize(400, 300);
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

   public void mouseDragged(MouseEvent e) { // di MouseMotionListerer
	   String s = "Mouse dragging: X="+e.getX()+"; Y="+e.getY();
	   tf.setText(s);
	}

	public void mouseEntered(MouseEvent e) { // di MouseListener
		tf.setText("Il mouse è entrato");
	}

	public void mouseExited(MouseEvent e) { // di MouseListener
		tf.setText("Il mouse è uscito");
	}

	public void mouseMoved(MouseEvent e) {} // di MouseMotionListener
	public void mousePressed(MouseEvent e) {} // di MouseListener
	public void mouseClicked(MouseEvent e) {} // di MouseListener
	public void mouseReleased(MouseEvent e) {} // di MouseListener

   public static void main(String[] args) {
      Two prova = new Two();
      prova.setVisible(true);
   }
}
