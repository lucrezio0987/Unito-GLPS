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
	    } else error("syntax error");
    }

    public void start() {
    // ... completare ...
	expr();
	match(Tag.EOF);
	// ... completare ...
    }

    private void expr() { 
        term();
        exprp();
}

    private void exprp() {
        switch (look.tag) {
            case '+':
                match('+');
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID) 
                    error("Errore, inseriti due operatori di fila");
                term();
                exprp();
                break;
            case '-':
                match('-');
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID) 
                    error("Errore, inseriti due operatori di fila");
                term();
                exprp();
                break;
            default:  
                if(look.tag == Tag.ID)
                    error("errore in exprp, carattere non valido " + look);
                else if(look.tag == Token.lpt.tag)
                    error("errore in exprp, carattere non valido. Atteso un operatore, invece -> (");
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
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID) 
                    error("Errore, inseriti due operatori di fila");
                fact();
                termp();
                break; 
            case '/':
                match('/');
                if(look.tag != Tag.NUM && look.tag != Token.lpt.tag && look.tag != Tag.ID) 
                    error("Errore, inseriti due operatori di fila");
                fact();
                termp();
                break;
            default: 
                if(look.tag == Tag.ID)
                    error("errore in termp, carattere non valido " + look);
                else if(look.tag == Token.lpt.tag)
                    error("errore in termp, carattere non valido. Atteso un operatore, invece -> (");
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
                        error("errore in fact, carattere non valido. Atteso un numero o una parentesi, invece -> " + look);
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

