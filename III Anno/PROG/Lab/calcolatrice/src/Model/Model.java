package Model;

import Valutatore.Lexer;
import Valutatore.Valutatore;

import java.io.*;
import java.util.*;

public class Model extends Observable {
    String TextString;
    String TextResutl;

    public Model() {
        TextString = "";
        TextResutl = "";
    }

    public String getTextString(){
        return TextString;
    }
    public String getTextResutl(){
        return TextResutl;
    }

    public void addElement(String el){
        if(el.equals("=")){
            TextResutl = result();
        } else if (el.equals("res")) {
            TextResutl = "";
            TextString = "";
        }
        else {
            if(!Character.isDigit(el.charAt(0))) TextString += " ";
            TextString += el;
            if(!Character.isDigit(el.charAt(0))) TextString += " ";
        }
        setChanged();
        notifyObservers();
    }

    public String result(){
        Lexer lex = new Lexer();
        BufferedReader br = new BufferedReader(new StringReader(TextString));
        Valutatore valutatore = new Valutatore(lex, br);
        return Integer.toString(valutatore.start());
    }

}
