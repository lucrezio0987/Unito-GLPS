## Application
```javafx.application.Application```

  - Lo **Stage** rappresenta la finestra
  - La **Scene** è il contenitore principale

  ```Java
    public class JavaFXApplication1 extends Application {

        @Override
        public void start(Stage primaryStage) { ... }

        public static void main(String[] args) { launch(args); }
    }
  ```

## Componenti

  - **StackPane**: Una Scene va associata a un pannello per definire il layout

  ```Java
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        Text sceneTitle = new Text("Welcome");
        sceneTitle.setId("welcome-text"); // si può impostare un ID Per i fogli di stile CSS

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
  ```

##  Event Handlers

  - Alle componenti grafiche possono essere associati i **Listener**
  ```Java
    btn.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent event) {
                            ...
                        }
                    });
  ```

## Forms (moduli)

  - Pannello **GridPane** per inserire i componenti grafici in una griglia
  - **Label** per scrivere i titoli dei campi delle form
  - **TextField** per definire i campi di input delle form
  - **Bottoni** con listener per sottomettere le form
  - **Text** per scrivere messaggi di output sulla finestra

  ```Java
    public void start(Stage primaryStage) {
      primaryStage.setTitle("JavaFX Welcome");
      GridPane grid = new GridPane(); // pannello a griglia

      Text sceneTitle           = new Text("Welcome");
      Label userName            = new Label("User Name:");
      TextField userTextField   = new TextField();
      Label pw                  = new Label("Password:");
      PasswordField pwBox       = new PasswordField();
      Button btn                = new Button("Sign in");
      final Text actiontarget   = new Text();

      sceneTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,20));

      btn.setOnAction(new EventHandler<ActionEvent>() {
                          @Override
                          public void handle(ActionEvent e) {
                              actiontarget.setFill(Color.FIREBRICK);
                              actiontarget.setText("Sign in button pressed");
                          }
                      });

      grid.add(sceneTitle    , 0, 0, 2, 1); // columnindex, rowindex, column span, row span
      grid.add(userName      , 0, 1);
      grid.add(userTextField , 1, 1);
      grid.add(pw            , 0, 2);
      grid.add(pwBox         , 1, 2);
      grid.add(btn           , 1, 3);        // aggiungo il bottone alla griglia... omesso
      grid.add(actiontarget  , 1, 6);

      Scene scene = new Scene(grid, 300, 275);

      primaryStage.setScene(scene);
      primaryStage.show();
    }
  ```

## Tipi di Pannelli

  - **GridPane** fa inserire i componenti figli in una griglia
  - **StackPane** fa sovrapporre i figli in uno stack
  - **BorderPane** fa inserire i figli a nord, est, ovest, sud, centro come per il BorderLayout di SWING.

