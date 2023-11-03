package Valutatore;

import java.io.*;
import java.util.*;

public class Lexer {
    public static int line = 1;
    private char peek = ' ';

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        boolean flag_commenti=true;
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r' || peek == '/') {
                if(peek=='/') {
                    readch(br);
                    if(peek=='/') {
                        while(peek != '\n') 
                            readch(br);
                    } else if (peek=='*') {
                        while(flag_commenti){
                            readch(br);
                            if(peek=='*') {
                                readch(br);
                                if(peek=='/') flag_commenti=false; 
                            }
                        } 
                    } else return Token.div;
                }
            if (peek == '\n') {
                line++;
            }
            readch(br);
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;
            case '(':
                peek = ' ';
                return Token.lpt;
            case ')':
                peek = ' ';
                return Token.rpt;
            case '[':
                peek = ' ';
                return Token.lpq;
            case ']':
                peek = ' ';
                return Token.rpq;
            case '{':
                peek = ' ';
                return Token.lpg;
            case '}':
                peek = ' ';
                return Token.rpg;
            case '+':
                peek = ' ';
                return Token.plus;
            case '-':
                peek = ' ';
                return Token.minus;
            case '*':
                peek = ' ';
                return Token.mult;
            case ';':
                peek = ' ';
                return Token.semicolon;
            case ',':
                peek = ' ';
                return Token.comma;
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("ERRORE: Erroneous character after & : "  + peek  + " - line " + line);
                    return null;
                }
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("ERRORE: Erroneous character after | : "  + peek  + " - line " + line);
                    return null;
                }
            case '<':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.le;
                } else if (peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } 
                 else {
                    peek = ' ';
                    return Word.lt;   
                }
            case '>':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else {
                    peek = ' ';
                    return Word.gt;   
                }
             case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                     System.err.println("ERRORE: Erroneous character after = : "  + peek  + " - line " + line);
                    return null;  
                }            
            case (char)-1:
                return new Token(Tag.EOF);
            default:
                if (Character.isLetter(peek)) {
                    String s = "";
                    while ((peek != ' ' && peek != (char)-1) && (Character.isLetter(peek) || Character.isDigit(peek) || (peek == '_'))) {
                        s = s + peek;
                        readch(br);
                    }
                    if (s.equals("assign")) {
                        return Word.assign;
                    } else if (s.equals("to")){
                        return Word.to;
                    } else if (s.equals("conditional")){
                        return Word.conditional;
                    } else if (s.equals("option")){
                        return Word.option;
                    } else if (s.equals("do")){
                        return Word.dotok;
                    } else if (s.equals("else")){
                        return Word.elsetok;
                    } else if (s.equals("while")){
                        return Word.whiletok;
                    } else if (s.equals("begin")){
                        return Word.begin;
                    } else if (s.equals("end")){
                        return Word.end;
                    } else if (s.equals("print")){
                        return Word.print;
                    } else if (s.equals("read")){
                        return Word.read;
                    } else {
                        return new Word(Tag.ID, s);
                    }
                } else if (Character.isDigit(peek)) {
                    String s = "";
                    while (Character.isDigit(peek)) {
                        s = s + peek;
                        readch(br);
                    }
                    return new NumberTok(Tag.NUM, s);
                } else { 
                    System.err.println("ERRORE: Erroneous character: " + peek  + " - line " + line);
                    return null;
                }
        } 
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                    System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }
}
