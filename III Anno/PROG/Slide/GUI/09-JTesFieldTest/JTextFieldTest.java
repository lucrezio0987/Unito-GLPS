
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JTextFieldTest {

    public static void main(String[] args) {
        MenuFrame frame = new MenuFrame("Raddoppia");
    }
}

class MenuFrame extends JFrame {

	private JTextArea input1;
	private JTextArea risultato;


	public MenuFrame(String name) {
		setLayout(new GridLayout(2, 2, 20, 50)); // rows, cols, hgap (distanza orizzontale tra colonne), vgap (dist verticale)

		add(new JLabel("Dato da raddoppiare: "));
		input1 = new JTextArea("");
		input1.setBackground(Color.BLUE);
		input1.setForeground(Color.WHITE);
		add(input1);

		JButton inputButton = new JButton("Submit");
		add(inputButton);

		risultato = new JTextArea("Risultato: ");
		risultato.setBackground(Color.YELLOW);
		risultato.setForeground(Color.BLACK);
		add(risultato);
	    inputButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
							int uno = 0;
							try {
	        					uno = Integer.parseInt(input1.getText());
							} catch (Exception ecc) {System.out.println(ecc.getMessage());}
							int ris = uno*2;
	        				risultato.setText((new Integer(ris)).toString());
	        				System.out.println(uno + "*2 = " + risultato.getText());
    				}
    			});
		setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}



}





