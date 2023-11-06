
import javax.swing.*;

class MyFrame extends JFrame {
	public MyFrame(String s) {
		super(s);
		setSize(400, 400);
	}
}

public class FinestraApp0 {
    public static void main(String[] args) {
        MyFrame frame = new MyFrame("Ciao!");

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
