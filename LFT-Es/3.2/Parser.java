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

    public void prog() {
        statlist();
        match(Tag.EOF);
    }

    private void statlist() {
        stat();
        statlistp();
    }

    private void statlistp() {
        switch(look.tag) {
            case ';':
                match(';');
                stat();
                statlistp();
            default:
                break;
        }
    }

    private void stat() {
        switch(look.tag) {
            case Tag.ASSIGN:
                match(Tag.ASSIGN); // ASSIGN
                expr();
                match(Tag.TO); // TO
                idlist();
                break;

            case Tag.PRINT:
                match(Tag.PRINT);  // PRINT
                match(Token.lpq.tag); // [
                exprlist();
                match(Token.rpq.tag); // ]
		break; 

            case Tag.READ:
                match(Tag.READ);  // READ
                match(Token.lpq.tag); // [
                idlist();
                match(Token.rpq.tag); // ]
                break; 

            case Tag.WHILE:
                match(Tag.WHILE);  // WHILE
                match(Token.lpt.tag); // (
                bexpr();
                match(Token.rpt.tag); // )
                stat();
                break;

            case Tag.COND:
                match(Tag.COND);  //  CONDITIONAL
                match(Token.lpq.tag); // [
                optlist();
                match(Token.rpq.tag); // ]
                switch(look.tag) {
                    case Tag.END:
                        match(Tag.END); // END
                        break;

                    case Tag.ELSE:
                        match(Tag.ELSE); // ELSE
                        stat();
                        match(Tag.END); // END
                        break;

                    default:
                        break;
                }
                break;

            case '{':
                match(Token.lpg.tag); // {
                statlist();
                match(Token.rpg.tag); // }
                break;

            default:
                break;
        }
    }

    private void idlist() {
        match(Tag.ID);
        idlistp();
    }

    private void idlistp() {
        switch(look.tag) {
            case ',':
                match(Token.comma.tag);
                match(Tag.ID);
                idlistp();
                break;
            default:
                break;
        }
    }

    private void optlist() {
        optitem();
        optlistp();
    }

    private void optlistp() {
	switch(look.tag){
        case Tag.OPTION:
            optitem();
            optlistp();
            break;
        default:
            break;
        }
    }

    private void optitem() {
        match(Tag.OPTION);  // OPTION
        match(Token.lpt.tag); // [
        bexpr();
        match(Token.rpt.tag); // ]
        match(Tag.DO);  // DO
        stat();
    }

    private void bexpr() {
        match(Tag.RELOP);
        expr();
        expr();
    }

    private void expr() { 
        switch(look.tag) {
            case '+':
                match(Token.plus.tag);
                match(Token.lpt.tag);
                exprlist();
                match(Token.rpt.tag);
                break;
               
            case '*':
                match(Token.mult.tag);
                match(Token.lpt.tag);
                exprlist();
                match(Token.rpt.tag);
                break;
            
            case '-':
                match(Token.minus.tag);
                expr();
                expr();
                break;

            case '/':
                match(Token.div.tag);
                expr();
                expr();
                break;
            
            case Tag.NUM:
                match(Tag.NUM);
                break;

            case Tag.ID:
                match(Tag.ID);
                break;

            default:
                break;
        }
    }

    private void exprlist() {
        expr();
        exprlistp();
    }
    
    private void exprlistp() {
        switch(look.tag) {
            case ',':
                match(Token.comma.tag);
                expr();
                exprlistp();
                break;

            default:
                break;
        }
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}

