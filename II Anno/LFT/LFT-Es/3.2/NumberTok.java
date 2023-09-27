public class NumberTok extends Token {
    public int lexeme;
    public NumberTok(int tag, String s) { super(tag); lexeme=Integer.parseInt(s); }
    public String toString() { return "<" + tag + ", " + lexeme + ">"; }
}
