

public class ErroreProgrammazione {
	static int[] a = new int[2];

	private static void p(int i, int val) {
	        if (a.length > i)
	            a[i] = val;
	        else
	            throw new
	                ArrayIndexOutOfBoundsException();
	}

	public static void main (String[] args) {
	        try{
	            p(0, 5);
	            p(2, 4);
	            p(1, 3);
	        }
	        catch (ArrayIndexOutOfBoundsException err) {
	            System.out.println("Errore di indice! " + err.getMessage());
	        }
	}
}
