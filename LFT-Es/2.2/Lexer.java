import java.io.*; 
import java.util.*;

public class Lexer {
    public static int line = 1;
    private char peek = ' ';
    public int flag_n = 0;
    public int flag_u = 0;

    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        if (peek==' ') // se il è rimasto un peek dalla lettura precedente (es: dopo aver letto print) non leggere un nuovo carattere ma gestisce quello gia presente;
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') {
                flag_n = 0;
                flag_u = 0;
                line++;
            }

            readch(br);
            flag_u++;

            if (Character.isDigit(peek) && flag_n == 0) {
                System.err.println("ERRORE1: inizia per numero");
                return null;
            }
            flag_n = 1;
        }
        
        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

	// ... gestire i casi di ( ) [ ] { } + - * / ; , ... //
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
            case '/':
                peek = ' ';
                return Token.div;
            case ';':
                peek = ' ';
                return Token.semicolon;
            case ',':
                peek = ' ';
                return Token.comma;
/* ----------------------------------------------------------------- */
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("ERRORE3: Erroneous character" + " after & : "  + peek );
                    return null;
                }
           
	// ... gestire i casi di || < > <= >= == <> ... //
            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("ERRORE3: Erroneous character" + " after | : "  + peek );
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
                     System.err.println("ERRORE4: Erroneous character"
                            + " after = : "  + peek );
                    return null;  
                }            
            
/* ----------------------------------------------------------------- */
            case (char)-1:
                return new Token(Tag.EOF);
            default:
                if (peek == '_' && flag_u == 1) {
                    String temp = "";
                    while (peek == '_' && (peek != (char)-1)) {
                        temp = temp + peek;
                        readch(br);
                    }

                    if((peek == (char) -1 || peek == '\n' || peek == '\r')) {
                        System.err.println("ERRORE5: Solo underscore presenti");
                        return null;  
                    }

                    int i = 0;
                    while (i < (temp.length() - 1)) {
                        System.out.println("Scan: " + Token.under);
                        i++;
                    }

                    return Token.under;
                } else if (Character.isLetter(peek)) {

// ... gestire il caso degli identificatori e delle parole chiave    //
/* ----------------------------------------------------------------- */

                    String temp = "";
                    flag_u++;
                    while ((peek != ' ' && peek != (char)-1) && (Character.isLetter(peek) || Character.isDigit(peek))) {
                        temp = temp + peek;
                        readch(br);
                    }
                    if (temp.equals("assign")) {
                        //peek=' ';
                        return Word.assign;
                    } else if (temp.equals("to")){
                        //peek=' ';
                        return Word.to;
                    } else if (temp.equals("conditional")){
                        //peek=' ';
                        return Word.conditional;
                    } else if (temp.equals("option")){
                        //peek=' ';
                        return Word.option;
                    } else if (temp.equals("do")){
                        //peek=' ';
                        return Word.dotok;
                    } else if (temp.equals("else")){
                        //peek=' ';
                        return Word.elsetok;
                    } else if (temp.equals("while")){
                        //peek=' ';
                        return Word.whiletok;
                    } else if (temp.equals("begin")){
                        //peek=' ';
                        return Word.begin;
                    } else if (temp.equals("end")){
                        //peek=' ';
                        return Word.end;
                    } else if (temp.equals("print")){
                        //peek=' ';
                        return Word.print;
                    } else if (temp.equals("read")){
                        //peek=' ';
                        return Word.read;
                    } else {
                        return new Word(Tag.ID, temp);
                    }

                } else if (Character.isDigit(peek)) {

	// ... gestire il caso dei numeri ... //
                    flag_u++;
                    String temp = "";
                    while (Character.isDigit(peek)) {
                        temp = temp + peek;
                        readch(br);
                    }
                    return new NumberTok(Tag.NUM, temp);
                } else {
                    if(peek == '_') { peek=' '; return Token.under;} 
                        System.err.println("ERRORE6: Erroneous character: " + peek );
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
