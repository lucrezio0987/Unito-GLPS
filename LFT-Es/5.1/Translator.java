import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    private int val;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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

    public void statlist(int lnext) {
        stat();
        statlistp();
    }

    public void prog() {        
	// ...   ...
        int lnext_prog = code.newLabel();
        statlist(lnext_prog);
        code.emitLabel(lnext_prog);
        match(Tag.EOF);
        try {
        	code.toJasmin();
        }
        catch(java.io.IOException e) {
        	System.out.println("IO error\n");
        };
	// ... completare ...
        match(Tag.EOF);

    }

    public void stat(int val ) {
        switch(look.tag) {
	// ... completare ...
            case Tag.READ:
                match(Tag.READ);
                match('[');
	        idlist(val);
                match(']');

	// ... completare ...
            case Tag.ASSIGN:
                match(Tag.ASSIGN); // ASSIGN
                expr();
                match(Tag.TO); // TO
                idlist(val);
                break;

            case Tag.PRINT:
                match(Tag.PRINT);  // PRINT
                match(Token.lpq.tag); // [
                exprlist();
                match(Token.rpq.tag); // ]
		    break; 

////            case Tag.READ:
////                match(Tag.READ);  // READ
////                match(Token.lpq.tag); // [
////                idlist();
////                match(Token.rpq.tag); // ]
////                break; 

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
                optlist(/* completare */);
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
                statlist(/* completare */)
                match(Token.rpg.tag); // }
                break;

            default:
                break;
        }
     }

    private void idlist(val) {
        switch(look.tag) {
	    case Tag.ID:
        	int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
	// ... completare ...
            idlistp(/* completare */);
        default:
            error("syntax error");
            break;
    	}
    }

        private void idlistp(/* completare */) {
        switch(look.tag) {
            case ',':
                match(Token.comma.tag);
        	    int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                idlistp(/* completare */);
                break;
            default:
                break;
        }
    }

    private void expr( /* completare */ ) {
        switch(look.tag) {
	// ... completare ...
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
	// ... completare ...
            case '+':
                match(Token.plus.tag);
                match(Token.lpt.tag);
                exprlist();
                match(Token.rpt.tag);
                code.emit(OpCode.iadd)
                break;
               
            case '*':
                match(Token.mult.tag);
                match(Token.lpt.tag);
                exprlist();
                match(Token.rpt.tag);
                code.emit(OpCode.imul)
                break;
            
////            case '-':
////                match(Token.minus.tag);
////                expr();
////                expr();
////                break;

            case '/':
                match(Token.div.tag);
                expr();
                expr();
                code.emit(OpCode.idiv)
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
    
    private void bexpr() {
        match(Tag.RELOP);
        expr();
        expr();
    }

// ... completare ...
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "prova.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Translator translator = new Translator(lex, br);
            translator.prog();
            System.out.println("...TERMINATO...");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
