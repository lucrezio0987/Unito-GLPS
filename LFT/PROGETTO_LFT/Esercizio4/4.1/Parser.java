import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
	    throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
	    if (look.tag == t) {
	        if (look.tag != Tag.EOF) move();
	    } else error("syntax error, character '" + look.tag + "'");
    }

    public void start() {
	expr();
	match(Tag.EOF);
    }

    private void expr() { 
        term();
        exprp();
}

    private void exprp() {
        switch (look.tag) {
            case '+':
                match('+');
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID && look.tag != Tag.EOF) 
                    error("Errore, inseriti due operatori di fila - line " + lex.line);
                term();
                exprp();
                break;
            case '-':
                match('-');
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID && look.tag != Tag.EOF) 
                    error("Errore, inseriti due operatori di fila - line " + lex.line);
                term();
                exprp();
                break;
            default:  
                if(look.tag == Tag.ID)
                    error("errore in exprp, carattere non valido " + look + " - line " + lex.line);
                else if(look.tag == Token.lpt.tag)
                    error("errore in exprp, carattere non valido. Atteso un operatore, invece -> ( - line " + lex.line);
                else break;
        } 
	}
    
    private void term() {
        fact();
        termp();
    }

    private void termp() {
        switch (look.tag) {
            case '*':
                match('*');
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID && look.tag != Tag.EOF) 
                    error("Errore, inseriti due operatori di fila - line " + lex.line);
                fact();
                termp();
                break; 
            case '/':
                match('/');
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID && look.tag != Tag.EOF) 
                    error("Errore, inseriti due operatori di fila - line " + lex.line);
                fact();
                termp();
                break;
            default: 
                if(look.tag == Tag.ID)
                    error("errore in termp, carattere non valido " + look + "- line " + lex.line);
                else if(look.tag == Token.lpt.tag)
                    error("errore in termp, carattere non valido. Atteso un operatore, invece -> ( - line" + lex.line);
                else break;
        } 
    }

    private void fact() {
            switch(look.tag) {
                case Tag.NUM:
                    match(Tag.NUM);
                    break;
                case '(':
                    match('(');
                    expr();
                    match(')');
                    break;
                default: 
                    if (look.tag != '(' && look.tag != Tag.NUM)
                        error("errore in fact, carattere non valido. Atteso un numero o una parentesi aperta, invece -> " + look  + " - line " + lex.line);
                    break;
            }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}

