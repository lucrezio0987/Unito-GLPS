import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Forma più compatta di Contatore3.
// L'esecuzione del ciclo infinito del listener
// del bottone START viene fatta in un nuovo thread
// Questo fa si' che il thread dell'interfaccia grafica
// possa servire altri eventi

public class ContConThread extends JFrame
{  private int count = 0;
   private JButton onOff = new JButton("INCR-DECR");
   private JButton start = new JButton("START");
   private JTextField t = new JTextField(4);
   private boolean runFlag = true;
   private ThreadCont tc = new ThreadCont();

   public ContConThread()
   {  setLayout(new FlowLayout());
      add(t);
      onOff.addActionListener(new ActionListener() {
		  	public void actionPerformed(ActionEvent e) {
				runFlag = !runFlag;
         		System.out.println("Thread: "+ Thread.currentThread().getName());
      		}
   		});
      add(onOff);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      start.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
				tc.start();
      		}
   		});
      add(start);
      setSize(200,200);
      setVisible(true);

   }

   private class ThreadCont extends Thread {
	   public void run() {
		   while (true) {
			   	try {
				   Thread.sleep(500);
            	}
            	catch(InterruptedException exc)
            		{System.out.println("Eccezione: " + exc.getMessage());}
            	if (runFlag)
            		t.setText(Integer.toString(count++));
            	else t.setText(Integer.toString(count--));
            	System.out.println("THREAD: "+ getName());
         	}
      }
   }

   public static void main(String args[])
   {   ContConThread f = new ContConThread();
   		System.out.println("Thread del main: "+ Thread.currentThread().getName());

   }
}
