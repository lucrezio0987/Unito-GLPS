public class Es1_8 {
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch(state) {
                
            }

    public static void main(String[] args) {
	    System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}