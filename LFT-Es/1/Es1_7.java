public class Es1_7 {
    public static boolean scan(String s) {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch(state) {
                case 0:
                    if (ch == 'p' || ch == 'P')
                        state = 1;
                    else if (ch != 'p' && ch != 'P')
                        state = 6;
                    else 
                        state = -1;
                    break;
                case 1:
                    if (ch == 'a' || ch == 'A')
                        state = 2;
                    else if (ch != 'a' && ch != 'A')
                        state = 7;
                    else
                        state = -1;
                    break;
                case 2: 
                    if (ch == 'o' || ch == 'O')
                        state = 3;
                    else if (ch != 'o' && ch != 'O')
                        state = 8;
                    else 
                        state = -1;
                    break;
                case 3:
                    if (ch == 'l' || ch == 'L')
                        state = 4;
                    else if (ch != 'l' && ch != 'L')
                        state = 9;
                    else 
                        state = -1;
                    break;
                case 4:
                    if (ch != ' ')
                        state = 5;
                    else 
                        state = -1;
                    break;
                case 5:
                    if (ch != ' ')
                        state = 10;
                    else 
                        state = -1;
                    break;
                case 6:
                    if (ch == 'a' || ch == 'A')
                        state = 7;
                    else if (ch != 'a' && ch != 'A')
                        state = 10;
                    else 
                        state = -1;
                    break;
                case 7:
                    if (ch == 'o' || ch == 'O')
                        state = 8;
                    else if (ch != 'o' && ch != 'O')
                        state = 10;
                    else 
                        state = -1;
                    break;
                case 8:
                    if (ch == 'l' || ch == 'L')
                        state = 9;
                    else if (ch != 'l' && ch != 'L')
                        state = 10;
                    else 
                        state = -1;
                    break;
                case 9:
                    if (ch == 'o' || ch == 'O')
                        state = 5;
                    else if (ch != 'o' && ch != 'O')
                        state = 10;
                    else 
                        state = -1;
                    break;
                case 10:
                    if (ch != ' ')
                        state = 10;
                    else 
                        state = -1;
                    break;
            }
        }
        return state == 5;
    }

    public static void main(String[] args) {
	    System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}