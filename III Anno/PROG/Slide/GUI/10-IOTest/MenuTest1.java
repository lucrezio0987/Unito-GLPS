
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuTest1 {

    public static void main(String[] args) {
        MenuFrame frame = new MenuFrame("operazioni");
		frame.setSize(400, 100);
		frame.setLocation(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class MenuFrame extends JFrame {

	public MenuFrame(String name) {
		JMenuBar bar = new JMenuBar();
		add(bar, "North");
		JMenu operazione = new JMenu(name);
		bar.add(operazione);
		operazione.add(new MyJMenuItem("somma"));
		operazione.add(new MyJMenuItem("prodotto"));
		operazione.add(new MyJMenuItem("sottrazione"));
	}

}


class MyJMenuItem extends JMenuItem {

	public MyJMenuItem(String label) {
		super(label);
	  	addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
    			System.out.println("Selected: " + e.getActionCommand());
    		}
    	});
	}
}



