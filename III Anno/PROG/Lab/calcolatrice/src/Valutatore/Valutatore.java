package Valutatore;

import java.io.*;

public class Valutatore {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Valutatore(Lexer l, BufferedReader br) { 
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

    public int start() {
	int expr_val;
    expr_val = expr();
    match(Tag.EOF);

    return expr_val;
    }

    private int expr() { 
        int term_val, exprp_val;
        term_val = term();
        exprp_val = exprp(term_val);

        return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val;
        switch (look.tag) {
        case '+':
            match('+');
            term_val = term();
            exprp_val = exprp(exprp_i + term_val);
            break;
        case '-':
            match('-');
            term_val = term();
            exprp_val = exprp(exprp_i - term_val);
            break;
        default: 
            exprp_val=exprp_i;
            if(look.tag == Tag.ID)
                error("errore in exprp, carattere non valido " + look + " - line " + lex.line);
            else if(look.tag == Token.lpt.tag)
                error("errore in exprp, carattere non valido. Atteso un operatore, invece -> ( - line " + lex.line);
            else break;
        }

        return exprp_val;
    }

    private int term() { 
        int fact_val, termp_val;

        fact_val = fact();
        termp_val = termp(fact_val);

        return termp_val;
    }
    
    private int termp(int termp_i) { 
        int fact_val, termp_val;
        switch (look.tag) {
        case '*':
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                break;
        case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                break;
        default: 
                termp_val=termp_i;
                if(look.tag == Tag.ID)
                    error("errore in termp, carattere non valido " + look + " - line " + lex.line);
                else if(look.tag == Token.lpt.tag)
                    error("errore in termp, carattere non valido. Atteso un operatore, invece -> ( - line " + lex.line);
                else break;
        }

        return termp_val;
    }
    
    private int fact() { 
        int fact_val;
        switch(look.tag) {
                case Tag.NUM:
                    fact_val = ((NumberTok) look).lexeme;
                    match(Tag.NUM);
                    break;
                case '(':
                    match('(');
                    fact_val = expr();
                    match(')');
                    break;
                default: 
                    fact_val=0;
                    if (look.tag != '(' && look.tag != Tag.NUM)
                        error("errore in fact, carattere non valido. Atteso un numero o una parentesi, invece -> " + look + " - line " + lex.line);
                    break;
            }
        return fact_val;
    }
/*
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Valutatore valutatore = new Valutatore(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }

 */
}
