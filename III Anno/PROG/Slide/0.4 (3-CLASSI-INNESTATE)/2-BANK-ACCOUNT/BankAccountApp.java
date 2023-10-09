public class BankAccountApp {
	public static void main(String[] args) {

		Person ugo = new Person("Ugo");
		Person maria = new Person("Maria");

		BankAccount ba = new BankAccount(1);
		ba.grantPermissions(ugo, true, true);
		ba.grantPermissions(maria, true, false);

		System.out.println("Saldo iniziale: " + ba.getBalance());

		System.out.print("Ugo deposita 199 e preleva 99: ");
		ba.deposit(ugo, 199);
		ba.withdraw(ugo, 99);
		System.out.println("Saldo: " + ba.getBalance());

		System.out.println("Maria deposita 20 ma non riesce a prelevare");
		ba.deposit(maria, 20);
		ba.withdraw(maria, 20);

		System.out.println("Saldo: " + ba.getBalance());

		System.out.println("\nOperazioni eseguite: ");

		ba.printHistory();
	}

}
