import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    private enum flag {assign, read};

    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;
    private int val_expr;

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

    public void prog() {        
	// ... completare ...
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
    }

    public void statlist(int next) {
        stat(next);
        statlistp(next);
    }

    public void statlistp(int next) {
        switch(look.tag) {
            case ';':
                match(';');
                //int next_statlistp = code.newLabel();
                stat(next);
			    //code.emitLabel(next_statlistp);
			    statlistp(next);
        }
    }

    public void stat(int stat_next) {
        switch(look.tag) {
	// ... completare ...
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                expr();
                match(Tag.TO);
                idlist(flag.assign);
                break;

            case Tag.PRINT:
                match(Tag.PRINT);
                match(Token.lpq.tag); // [
                exprlist(OpCode.ineg);
                match(Token.rpq.tag); // ]
                break;

            case Tag.READ:
                match(Tag.READ);
                match('[');
	            idlist(flag.read);
                match(']');
                break;

            case Tag.WHILE:
                match(Tag.WHILE);
                match(Token.lpt.tag); // (
                
                int return_while = code.newLabel(); 
                int true_while   = code.newLabel();   
                int end_while    = code.newLabel();
                code.emitLabel(return_while);
                bexpr(true_while, end_while);

                match(Token.rpt.tag); // )

                code.emitLabel(true_while);
                stat(return_while);
                code.emit(OpCode.GOto, return_while);
                code.emitLabel(end_while);

                break;

            case Tag.COND:
                match(Tag.COND);
                int end_cond = code.newLabel();
                int lbl = code.newLabel();
                match(Token.lpq.tag); // [
                do {
                    match(Tag.OPTION);
                    match(Token.lpt.tag);
                    code.emitLabel(lbl);
                    lbl = code.newLabel();
                    bexpr(end_cond, lbl);
                    match(Token.rpt.tag);
                    match(Tag.DO);
                    stat(stat_next);
                    code.emit(OpCode.GOto, end_cond);
                } while (look.tag == Tag.OPTION);
                code.emitLabel(lbl);
                match(Token.rpq.tag); // ]

                if (look.tag == Tag.ELSE) {
                    match(Tag.ELSE);
                    stat(stat_next);
                }
                match(Tag.END);
                code.emitLabel(end_cond);
                break;

            case '{':
                match('{');
                statlist(stat_next);
                match('}');
                break;

            default:
                error("ERRORE - stat");
                
	// ... completare ...
        }
     }

    private void idlist(flag flag) {
        switch(look.tag) {
	        case Tag.ID:
        	    int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                    
                if (flag == flag.read)
                    code.emit(OpCode.invokestatic, 0);
                code.emit(OpCode.istore, id_addr);

                idlistp(flag);
                break;

            default:
                error("ERRORE - idlist");
	// ... completare ...
    	}
    }

    private void idlistp(flag flag) {
        switch(look.tag) {
            case ',':
                match(Token.comma.tag);
                
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }

                if (flag == flag.read)
                    code.emit(OpCode.invokestatic, 0);
                if (flag == flag.assign)
                    code.emit(OpCode.ldc, val_expr);
                
                match(look.tag);
                code.emit(OpCode.istore, id_addr);
                idlistp(flag);
                break;
            
            case ';': break;

            default:
                break;
        }
    }

    private void expr() {
        switch(look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist(OpCode.iadd);
                match(')');
                break;

            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;

            case '*':
                match('*');
                match('(');
                exprlist(OpCode.imul);
                match(')');
                break;

            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;
            
            case Tag.NUM:
                val_expr = NumberTok.lexeme;
                match(Tag.NUM);
                code.emit(OpCode.ldc, val_expr);
                break;
            
            case Tag.ID:
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr==-1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                code.emit(OpCode.iload, id_addr);
            
	// ... completare ...
        }
    }

    private void bexpr(int True, int False) {
        if (look.tag == Tag.RELOP) {
            String s = ((Word)look).lexeme;
            match(Tag.RELOP);
            expr(); 
            expr();
            /*
            if(s.compareTo(">") == 0)
                code.emit(OpCode.if_icmpgt, True);
            else if(s.compareTo("<") == 0) 
                code.emit(OpCode.if_icmplt, True);
            else if(s.compareTo(">=") == 0)
                code.emit(OpCode.if_icmpge, True);
            else if(s.compareTo("<=") == 0)
                code.emit(OpCode.if_icmple, True);
            else if(s.compareTo("==") == 0)
                code.emit(OpCode.if_icmpeq, True);
            else if(s.compareTo("<>") == 0)
                code.emit(OpCode.if_icmpne, True);
            else 
                code.emit(OpCode.GOto, False); 
            */
            switch(s) {
                case ">":
                    code.emit(OpCode.if_icmple, False);
                    break;
                case "<":
                    code.emit(OpCode.if_icmpge, False);
                    break;
                case ">=":
                    code.emit(OpCode.if_icmplt, False);
                    break;
                case "<=":
                    code.emit(OpCode.if_icmpgt, False);
                    break;
                case "==":
                    code.emit(OpCode.if_icmpne, False);
                    break;
                case "<>":
                    code.emit(OpCode.if_icmpeq, False);
                    break;
                default:
                    error("ERRORE - bexpr");
            }
            //code.emit(OpCode.GOto, False);
        } else error("ERRORE - bexpr");
    }


    private void exprlistp(OpCode op) {
        switch(look.tag) {
            case ',':
                match(Token.comma.tag);
                expr();
                if(OpCode.iadd == op)
                    code.emit(OpCode.iadd);
                else if(OpCode.imul == op)
                    code.emit(OpCode.imul);
                else 
                    code.emit(OpCode.invokestatic, 1);

                exprlistp(op);
                break;
        }
    }

    private void exprlist(OpCode op) {
        switch(look.tag) {
            case '+': case '-': case '*': case '/': case Tag.ID: case Tag.NUM:
                expr();
                if ((op != OpCode.iadd) && (op != OpCode.imul))
				    code.emit(OpCode.invokestatic, 1);
                exprlistp(op);
                break;

            default:
                error("ERRORE - exprlist");
        }
    }

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
