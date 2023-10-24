## Frame

```Java
    JFrame frame = new JFrame("Titolo della Finestra");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

    frame.dispose();
```

### Label

```Java
    JLabel label = new JLabel("Testo");
    frame.add(label);

    label.setText("Nuovo Testo");
```

### Panel
```Java
    JPanel panel = new JPanel();
    frame.add(panel);

    panel.add(label2);
    panel.add(button);
```

### Layaout

```java
    frame.pack(); // dimensiona la finestra in modo ottimale rispetto al contenuto

    frame.setSize(300,300);
    frame.setLocation(400,200);
```

```Java
    JLabel label2 = new JLabel("Testo",JLabel.CENTER);
```

> Se aggiungo componenti direttamente al pannello, senza layout manager, non vengono ben visualizzati

```Java
    frame.setLayout(new FlowLayout());
    // posiziona i bottoni in modo sequenziale, centrati

    frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 40));
    // posiziona i bottoni da sinistra


    frame.setLayout(new GridLayout(3, 2));
    // rows, cols

    frame.setLayout(new GridLayout(3, 2, 20, 50));
    // rows, cols, hgap (distanza orizzontale tra colonne), vgap (dist verticale)

```

```Java
    frame.add(button);  // per default posiziona i bottoni al centro

    frame.add(b1, BorderLayout.NORTH);      frame.add(b1, "North");
    frame.add(b2, BorderLayout.SOUTH);      frame.add(b2, "South");
    frame.add(b3, BorderLayout.WEST);       frame.add(b3, "West");
    frame.add(b4, BorderLayout.EAST);       frame.add(b4, "East");
    frame.add(b5, BorderLayout.CENTER);     frame.add(b5, "Center");

```

```Java
    button.setBackground(Color.yellow); // Background-color
    button.setForeground(Color.yellow); // Text-color
```

### Button
```Java
    private JButton button = new JButton("Bottone 1");
    frame.add(button);
```

## ActionListener
   BeepListener e' innestata per nascondere i tipi che non servono al di fuori della classe principale siccome non utilizza lo stato di Beeper1, la definisco static

```java

    button.addActionListener(new Listener());

    private static class Listener implements ActionListener {
      public void actionPerformed(ActionEvent event) { /* ... */}
    }

    button.getActionListeners();
    button.removeActionListener(Listener);
    button.removeAllActionListeners();

```
  rimuove l'action listener
```java

    public class FrameListener extends JFrame {

      public FrameListener(final JButton button) {

        ActionListener Listener = new ActionListener() {
             public void actionPerformed(ActionEvent event) {
                button.removeActionListener(this);
             }
          };

        button.addActionListener(Listener);
      }
    }

```

```Java
    // Esepio operazione actionPerformed()
    public void actionPerformed(ActionEvent event) {
      System.out.println("Testo da stampare");
      Toolkit.getDefaultToolkit().beep();
      String s = event.getActionCommand();
    }
```

Posso far implementare ActionListener direttamente al Beeper2 (alla finestra) per non creare classe separata (interna)

```Java
  public class FrameListener extends JFrame implements ActionListener{

    public FrameListener() {
      // create Pannel/Frame ...
      // create Button ...
      button.addActionListener(this); // aggiunge action listener al bottone
    }

    public void actionPerformed(ActionEvent e) {
      //...
    }

   public static void main(String[] args) {
      FrameListener beep = new FrameListener();
   }

  }
```

Il bottone è listener di se stesso, cioe' contiene il listener per gestire gli eventi che genera.

```Java
  public class FrameListener extends JFrame{

    public FrameListener() {
      // create Pannel/Frame ...
      button = new ButtonListener("Bottone 1");

    }

    private static class ButtonListener extends JButton implements ActionListener {
      private ButtonListener(String testo){
        super(testo);
	  		addActionListener(this); // aggiunge action listener a se stesso
      }
      public void actionPerformed(ActionEvent event) {
        //...
   		}
    }

    public static void main(String[] args) {
      FrameListener beep = new FrameListener();
    }

  }
```

### Action Event
```Java
  public void actionPerformed(ActionEvent event) {
    event.getActionCommand();
    event.getModifiers();
    event.getWhen();
    event.paramString()
  }
```

### Adapter
```Java
    frame.addWindowListener(new WL());

    private static class WL extends WindowAdapter {
   		public void windowClosing(WindowEvent event) {
        // ...
   		}
   	}
```
```Java
  public void windowClosing(WindowEvent event) {/*...*/}
  public void windowActivated(WindowEvent event) {/*...*/}
  //...
```

### Classe anonima
```Java
  button.addActionListener( new ActionListener() {
                              //...
                            });

  frame.addWindowListener( new WindowAdapter() {
                              //...
                            });
```

```Java
  button.addActionListener( new ActionListener() {
                              //...
                            });

  frame.addWindowListener( new WindowAdapter() {
                              //...
                            });
```


Qui di seguito la classe del listener non e' interna a Beeper ==> le si deve passare il Beeper quando la si crea per permetterle di invocare i suoi metodi (non accede allo stato del Beeper)

```Java

public class FrameOBJ extends JFrame {

  public FrameOBJ() {
    button.addActionListener(new FrameListener(this));
  }

  public void metod_1() {/*...*/}

  public static void main(String[] args) {
    FrameOBJ f = new FrameOBJ();
  }
}



class FrameListener implements ActionListener {
  FrameOBJ frame;
  FrameListener(FrameOBJ frame){  this.frame = frame; }


  public void actionPerformed(ActionEvent event) {
    frame.metod_1();
  }
}

```

### Lambda
```Java
    ActionListener listener = event -> {/*...*/};
    button.addActionListener(listener);
```

## Mouse
[MouseMotionListerer]:
```Java
  addMouseMotionListener(FrameListener);

  class FrameListener(ActionEvent event) implements addMouseMotionListener {
    public void mouseDragged(MouseEvent e) {
      event.getX();
      event.getY();
    }
    public void mouseMoved(MouseEvent e) {/*...*/}
  }
```

[MouseListener]:
```Java
  addMouseListener(FrameListener);

  class FrameListener(ActionEvent event) implements MouseListener {
    public void mouseEntered(MouseEvent event) { /*...*/	}
    public void mouseExited(MouseEvent event) { /*...*/	}
    public void mousePressed(MouseEvent event) {/*...*/}
    public void mouseClicked(MouseEvent event) {/*...*/}
    public void mouseReleased(MouseEvent event) {/*...*/}
  }

```

[con le classi anonime]:
```Java
    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {/*...*/}
    });

    addMouseListener(new MouseAdapter() {
          public void mouseEntered(MouseEvent e) {/*...*/}
    });
```

## JOptionPane
  una versione di CiaoNome in cui l'input e l'output sono fatti non su consolle, bensi' su finestre di dialogo, usando metodi statici della classe JOptionPane

```Java
  String nome = JOptionPane.showInputDialog("Come ti chiami?");
  JOptionPane.showMessageDialog(null, "Ciao, " + nome);
```
### JTextArea

```Java
  JTextArea textArea = new JTextArea("Titolo");

  textArea.getText();
  textArea.setText("Testo");

```

### JMenuBar

```Java
  JMenuBar bar = new JMenuBar();
  JMenu operazione = new JMenu("Nome Menu");

  bar.add(operazione);

  operazione.add(new MyJMenuItem("Operazione 1"));
  operazione.add(new MyJMenuItem("Operazione 2"));
  operazione.add(new MyJMenuItem("Operazione 3"));

  class MyJMenuItem extends JMenuItem {

    public MyJMenuItem(String label) {
      super(label);
      addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
          String operazione = event.getActionCommand(); // label
        }
      });
	}
  }

```

## Observer-Observable

[Main]:
```Java

    public static void main(String[] args) {
		Observable_Obj observable = new Observable_Obj();
		Observer_Obj observer = new Observer_Obj();
      	GestoreFrame frame = new GestoreFrame(observable);

      	observable.addObserver(observer);
      	// aggiunge l'osservatore all'oggetto Observable
   	}

   	class GestoreFrame extends JFrame {

      public GestoreFrame(Observable_Obj observable) {
        JPanel panel = new JPanel();              add(panel);
      	JButton button = new JButton("Versa");    panel.add(button);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
              observable.metod_1();
            }
          });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
      }

   	}

```

[Observer]: quando viene eseguita una notifyObservers(), viene eseguita una update()
```Java
class Observer_Obj implements Observer {

   public void update(Observable ob, Object extra_arg) {
	   	if (ob!=null && ob instanceof Observable_Obj) {
			Observable_Obj observable  = (Observable_Obj) ob;
			observable.metod_2();
		}
   }
}

```

[Observable]:
```Java
  class Observable_Obj extends Observable {
      public void metod_1() {
          //...
          setChanged();
          notifyObservers();
      }
      public int metod_2() {
        return 0;
      }
  }
```

qui sotto il legame tra osservato e osservatore avviene attraverso l'uso del parametro Observable del metodo update;

```Java
    public static void main(String[] args) {

      Observable_Obj observable = new Observable_Obj();
      FinestraObserver observer_frame = new FinestraObserver();
      GestoreFrame frame = new GestoreFrame(observable);

      observable.addObserver(observer_frame);
    }
```

```Java
  class FinestraObserver extends JFrame implements Observer {

    public FinestraObserver() {
      JLabel display = new JLabel();    add(display);

      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
    }

    public void update(Observable ob, Object extra_arg) {
      if (ob!=null && ob instanceof Observable_Obj) {
          display.setText( ((Observable_Obj)ob).metod_2() );
      }
    }

  }
```
