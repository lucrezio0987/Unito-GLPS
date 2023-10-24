## Frame

```Java
    JFrame frame = new JFrame("Titolo della Finestra");

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
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
