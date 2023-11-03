package Model;

import javax.swing.*;
import java.util.Observable;

public class Model extends Observable {
    String TextString = "";

    public String getTextString(){
        return TextString;
    }

    public void addElement(String el){
        TextString += el;
        setChanged();
        notifyObservers();
    }

}
