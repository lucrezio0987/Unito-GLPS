package es2.main;

import es2.controller.Controller;
import es2.model.Model;
import es2.view.View;

public class ProverbsApp {

    public static void main(String[] args) {

        Model model = new Model();
        View view = new View("Proverbs App");
        Controller controller = new Controller(model);

        view.setListener(controller);
        model.addObserver(view);
        model.chooseProverb();
    }
}

java.awt.event.ActionEvent[ACTION_PERFORMED,cmd=1,when=1699014918358,modifiers=Button1] on javax.swing.JButton[,0,0,60x40,alignmentX=0.0,alignmentY=0.5,border=javax.swing.plaf.BorderUIResource$CompoundBorderUIResource@37e7364f,flags=296,maximumSize=,minimumSize=,preferredSize=java.awt.Dimension[width=60,height=40],defaultIcon=,disabledIcon=,disabledSelectedIcon=,margin=javax.swing.plaf.InsetsUIResource[top=2,left=14,bottom=2,right=14],paintBorder=true,paintFocus=true,pressedIcon=,rolloverEnabled=true,rolloverIcon=,rolloverSelectedIcon=,selectedIcon=,text=1,defaultCapable=true]
