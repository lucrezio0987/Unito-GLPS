package com.example.prva.controller;

import com.example.prva.model.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class ClientController {
    @FXML
    private TextField   localAddressReceived, localAddressSent, localAddressSend, localAddressLog,
                        addressMailSent, addressMailRecived, addressMailSend,
                        objectMailSent, objectMailRecived, objectMailSend;
    @FXML
    private Button      Cancella_Tutto_Ricevuta, Cancella_Tutto_Inviata,
                        forwardBtnSent, forwardBtnReceived,sendBtnClear,
                        deleteBtnSent, replyBtnReceived, deleteBtnRecived, sendBtn, replyAllBtnReceived,
                        reconnectBtnSent, reconnectBtnReceived, reconnectBtnSend,
                        reconnectServerBtnLog, connectServerBtnLog, disconnectServerBtnLog, reconnectBtnLog;
    @FXML
    private Label       addressLabelSent, objectLabelSent, addressLabelReceived, objectLabelReceived,
                        countMailSent, countMailReceived;
    @FXML
    private TextArea    textMailSent, textMailReceived, textMailSend, textLog;
    @FXML
    private ImageView   imgEmailReceived, imgEmailSent;
    @FXML
    private VBox        Lista_posta_inviata, Lista_posta_ricevuta;
    @FXML
    private TabPane     tabPanel;
    @FXML
    private Tab         tabSend;

    @FXML
    private Circle      ConnectLedReceived, ConnectLedSend, ConnectLedSent, ConnectLedLog,
                        DisconnectLedReceived, DisconnectLedSend, DisconnectLedSent, DisconnectLedLog;

    MailModel mailModel;
    MailCardModel mailCardModel;

    //TODO: AGGIUNGERE COMMENTI!!!!!!

    //TODO: far funzionare i pulsati del server

    //TODO: aggiungere una connessione Client -> Server su un altra porta che invia un oggetto MailModifyInfo che notifica la lettura o la cancelazione di una mail

    /* TODO: fare in modo che il Server quando un client si connette non gli invii tutti i messaggi ma solo quelli che non ha gia
          IDEE:
             1. il client potrebbe mandare l'ultima mail (uuid) che ha nella lista in modo che il server possa controllare se è effettivamente l'ultima
             2. servirebbe anceh una struttura dati di qualche genere per indicare quali mail sono state lette o cancellate tra quelle gia possedute
                (ad esempio una mappa con uuid come chiave e come valore 0 se è stata letta e 1 se è stata cancellata)
                (ovviamente conterrebbe solo quelle che sono state modificate dall'ultimo aggiornamento, quindi viene svuotata alla fine di ogni aggioranmento)
             3. è possibile che serve costruire un macrooggetto Operazione (passato con Json) contenente: lista mail da agggiungere, mappa mail modificate
     */

    /* TODO: lo stesso sistema serve per il client, nel senso che quando il server è offline deve avere un modo per salvarsi le modifiche fatte e le mail inviate in una lista a parte
             e mandandare tutto quando il server viene connesso
     */

    /*TODO: fare in modo che i client sullo stesso host (quindi se li avvi su stesso pc ad esempio) possano essere distinguibili:
        IDEE:
            1. ho notato che l'oggetto Socket oltre a contenere la portaLocale e l'indirizzo locale contiene anche un altra porta che sembra variaare all'interno della stessa rete
               è possibile che si possa usare quella ma bisogna informarsi
            2. l'alternativa potrebbe essere che il messaggio viene mandato a tutti su quella rete poi i client in ascolto controllano che il messaggio "sia effettivamente destinato a loro"
               ad esempio controllando che il ricevende sia uguale al localAddress, ma sarebbe da evitare per ragioni di sicurezza
            3. non mi piace dato il numero di porte che utilizza anche il client (almeno 2 per ogni client: ricezione mail, ricezione messaggi brodcast dal server),
               ma un altra idea potrebbe essere fare in modo che il client si metta su "una porta libera" (causerebbe più problemi e renderebbe il sistema poco scalabile a parer mio)
     */

    //TODO: IN SERVER_MODEL_2 CHIAMI IL METODO readCSV NELL'INVIO DELL'ARRAY AL CLIENT SENZA CONTROLLARE CHE ESISTA, ABBIAMO RICHIAMATO sendCSV


    public void initModel(String localAddressMail) {
        mailModel = new MailModel(this);
        mailCardModel = new MailCardModel(mailModel);

        showMailPanelReceived(false);
        showMailPanelSent(false);

        textMailSent.textProperty().bind(mailModel.getTextMailSentProperty());
        addressMailSent.textProperty().bind(mailModel.getAddressMailSentProperty());
        objectMailSent.textProperty().bind(mailModel.getObjectMailSentProperty());

        textMailReceived.textProperty().bind(mailModel.getTextMailReceivedProperty());
        addressMailRecived.textProperty().bind(mailModel.getAddressMailReceivedProperty());
        objectMailRecived.textProperty().bind(mailModel.getObjectMailReceivedProperty());

        mailModel.getTextMailSendProperty().bindBidirectional(textMailSend.textProperty());
        mailModel.getAddressMailSendProperty().bindBidirectional(addressMailSend.textProperty());
        mailModel.getObjectMailSendProperty().bindBidirectional(objectMailSend.textProperty());

        textLog.textProperty().bind(mailModel.getTextLogProperty());

        mailModel.getLocalAddressProperty().bindBidirectional(localAddressReceived.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressSent.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressSend.textProperty());
        mailModel.getLocalAddressProperty().bindBidirectional(localAddressLog.textProperty());

        localAddressSend.textProperty().set(localAddressMail);
        setConnection(mailModel.connect());

        Cancella_Tutto_Inviata.setOnAction(event -> {
            // mailModel.addLog("null", "MailReceivedList: Prima della cancellazione ( " + mailModel.getListMailReceived().toString() + " )");
            System.out.println("MailReceivedList: Prima della cancellazione ( " + mailModel.getListMailReceived().toString() + " )");
            deleteMailSent();
            System.out.println("MailSentList: Cancellata ( " + mailModel.getListMailSent().toString() + " )\n");
        });

        Cancella_Tutto_Ricevuta.setOnAction(event -> {
            System.out.println("MailReceivedList: Prima della cancellazione ( " + mailModel.getListMailReceived().toString() + " )");
            deleteMailReceived();
            System.out.println("MailReceivedList: Cancellata ( " + mailModel.getListMailReceived().toString() + " )\n");
        });

        sendBtn.setOnAction(event -> {
            Mail mail = mailModel.sendMail();
        });

        deleteBtnSent.setOnAction(event -> {
            String idMail = mailModel.deleteActualMailSent();
            Lista_posta_inviata.getChildren().removeIf(card -> card.getId().equals(idMail));
            showMailPanelSent(false);
            setCountMailSent();
        });

        deleteBtnRecived.setOnAction(event -> {
            String idMail = mailModel.deleteActualMailReceived();
            Lista_posta_ricevuta.getChildren().removeIf(card -> card.getId().equals(idMail));
            showMailPanelReceived(false);
            setCountMailReceived();
        });

        sendBtnClear.setOnAction( event -> { mailModel.sendMailClear(); });

        replyBtnReceived.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.reply();
        });

        replyAllBtnReceived.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.replyAll();
        });

        forwardBtnSent.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.forwardSent();
        });

        forwardBtnReceived.setOnAction(event ->{
            tabPanel.getSelectionModel().select(tabSend);
            mailModel.forwardReceived();
        });

        reconnectBtnSent.setOnAction(event ->       { clearMail(); mailModel.connect();});
        reconnectBtnReceived.setOnAction(event ->   { clearMail(); mailModel.connect();});
        reconnectBtnSend.setOnAction(event ->       { clearMail(); mailModel.connect();});
        reconnectBtnLog.setOnAction(event ->        { clearMail(); mailModel.connect();});
    }

    public void setConnection(boolean connect) {
        if(connect){
            ConnectLedReceived.setVisible(true);
            ConnectLedSend.setVisible(true);
            ConnectLedSent.setVisible(true);
            ConnectLedLog.setVisible(true);

            DisconnectLedReceived.setVisible(false);
            DisconnectLedSend.setVisible(false);
            DisconnectLedSent.setVisible(false);
            DisconnectLedLog.setVisible(false);
        } else {
            ConnectLedReceived.setVisible(false);
            ConnectLedSend.setVisible(false);
            ConnectLedSent.setVisible(false);
            ConnectLedLog.setVisible(false);

            DisconnectLedReceived.setVisible(true);
            DisconnectLedSend.setVisible(true);
            DisconnectLedSent.setVisible(true);
            DisconnectLedLog.setVisible(true);

        }
    }

    public void setCountMailSent() { countMailSent.setText(String.valueOf(mailModel.getListMailSent().size())); }
    public void setCountMailReceived() { countMailReceived.setText(String.valueOf(mailModel.getListMailReceived().size())); }

    private void clearMail() {
        Lista_posta_inviata.getChildren().clear();
        Lista_posta_ricevuta.getChildren().clear();
        showMailPanelSent(false);
        showMailPanelReceived(false);
        setCountMailSent();
        setCountMailReceived();
    }
    private void deleteMailSent(){
        mailModel.deleteMailSentList();
        Lista_posta_inviata.getChildren().clear();
        showMailPanelSent(false);
        setCountMailSent();
    }

    private void deleteMailReceived(){
        mailModel.deleteMailReceivedList();
        Lista_posta_ricevuta.getChildren().clear();
        showMailPanelReceived(false);
        setCountMailReceived();
    }

    private void showMailPanelReceived(boolean bool) {
        if(bool) {
            imgEmailReceived.setVisible(false);

            textMailReceived.setVisible(true);
            addressMailRecived.setVisible(true);
            objectMailRecived.setVisible(true);
            addressLabelReceived.setVisible(true);
            objectLabelReceived.setVisible(true);
            replyBtnReceived.setVisible(true);
            deleteBtnRecived.setVisible(true);
            replyAllBtnReceived.setVisible(true);

            forwardBtnReceived.setVisible(true);
        } else {
            imgEmailReceived.setVisible(true);

            textMailReceived.setVisible(false);
            addressMailRecived.setVisible(false);
            objectMailRecived.setVisible(false);
            addressLabelReceived.setVisible(false);
            objectLabelReceived.setVisible(false);
            replyBtnReceived.setVisible(false);
            deleteBtnRecived.setVisible(false);
            replyAllBtnReceived.setVisible(false);

            forwardBtnReceived.setVisible(false);
        }
    }
    private void showMailPanelSent(boolean bool) {
        if(bool) {
            imgEmailSent.setVisible(false);

            textMailSent.setVisible(true);
            addressMailSent.setVisible(true);
            objectMailSent.setVisible(true);
            addressLabelSent.setVisible(true);
            objectLabelSent.setVisible(true);
            deleteBtnSent.setVisible(true);
            forwardBtnSent.setVisible(true);

        } else {
            imgEmailSent.setVisible(true);

            textMailSent.setVisible(false);
            addressMailSent.setVisible(false);
            objectMailSent.setVisible(false);
            addressLabelSent.setVisible(false);
            objectLabelSent.setVisible(false);
            deleteBtnSent.setVisible(false);
            forwardBtnSent.setVisible(false);
        }
    }

    public synchronized void createCardReceived(Mail mail){
        VBox card = mailCardModel.buildCard("Mittente:", mail);
        Lista_posta_ricevuta.getChildren().add(0, card);
        setCountMailReceived();
    }

    public synchronized void createCardSent(Mail mail){
        VBox card = mailCardModel.buildCard("Destinatari:", mail);
        Lista_posta_inviata.getChildren().add(0, card);
        setCountMailSent();
    }

    public void termModel() {
        mailModel.stop();
    }

    public class MailCardModel {

        private MailModel mailModel = null;

        public MailCardModel(MailModel mailModel) { this.mailModel = mailModel;}

        public VBox buildCard(String soggetto, Mail mail) {
            VBox vbox = new VBox();
            vbox.setId(mail.getUuid());
            vbox.getStyleClass().add("class-card-posta");
            if(mail.getRead())
                vbox.getStyleClass().add("read");

            HBox hbox1 = null;

            if(soggetto.equals("Mittente:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        mailModel.openMailReceived(mail.getUuid());
                        showMailPanelReceived(true);
                        mailModel.setMailRead(mail.getUuid(), true);
                        ((Node) e.getSource()).getStyleClass().add("read");
                    }
                });
                hbox1 = createHBox(soggetto, mail.getSender());
            } else if(soggetto.equals("Destinatari:")){
                vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        mailModel.openMailSent(mail.getUuid());
                        showMailPanelSent(true);
                        mailModel.setMailRead(mail.getUuid(), true);
                        ((Node) e.getSource()).getStyleClass().add("read");
                    }
                });
                hbox1 = createHBox(soggetto, mail.getRecipients());
            }
            else System.err.println("ERRORE");

            HBox hbox2 = createHBox("Oggetto:", mail.getObject());
            HBox hbox3 = createHBox("Data:", mail.getDate(), "Ora", mail.getTime());

            vbox.getChildren().addAll(hbox1, hbox2, hbox3);

            return vbox;
        }
        private static HBox createHBox(String label1Text, String text1) {
            HBox hbox = new HBox();
            Label label1 = createLabel(label1Text);
            Text text = new Text(text1);

            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll(label1, text);

            return hbox;
        }

        private static HBox createHBox(String label1Text, String text1, String label2Text, String text2) {
            HBox hbox = new HBox();
            Label label1 = createLabel(label1Text);
            Text text1Node = new Text(text1);
            Label label2 = createLabel(label2Text);
            Text text2Node = new Text(text2);

            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.getChildren().addAll(label1, text1Node);
            hbox.getChildren().addAll(label2, text2Node);

            return hbox;
        }

        private static Label createLabel(String labelText) {
            Label label = new Label(labelText);
            label.setMaxHeight(Double.MAX_VALUE);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setStyle("-fx-padding:10;");
            label.setUnderline(true);
            label.setFont(new Font(12.0));
            return label;
        }

    }
}