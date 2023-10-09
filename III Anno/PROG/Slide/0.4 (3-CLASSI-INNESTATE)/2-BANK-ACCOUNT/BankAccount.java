import java.util.*;

public class BankAccount {
	private long number;
	private long balance;
	private Vector<Permissions> grantedPerms;
	private Vector<Operation> history;

		private class Operation {
			private String op;
			private long amount;

			private Operation(String o, long q) {
				op = o;
				amount = q;
			}

			public String toString() {
				return number + ": " + op + " " + amount;
			}
		}


		private static class Permissions {
			private Person pers;
			private boolean canDeposit;
			private boolean canWithdraw;

			private Permissions(Person p, boolean deposit, boolean withdraw) {
				pers = p;
				canDeposit = deposit;
				canWithdraw = withdraw;
			}

		/*	private Person getPerson() {  // NON SERVE...
				return pers;
			} */
/*
			public boolean equals(Object o) {
				boolean ris = false;
				if (o!=null && o instanceof Permissions) {
					Permissions perm = (Permissions)o;
					ris = pers.equals(perm.pers) && (canDeposit == perm.canDeposit) && (canWithdraw == perm.canWithdraw);
				}
				return ris;
			}
			*/
		}


	public BankAccount(int n) {
		number = n;
		grantedPerms = new Vector<Permissions>();
		history = new Vector<Operation>();
	}


	public void grantPermissions(Person p, boolean deposit, boolean withdraw) {
		Permissions perm = new Permissions(p, deposit, withdraw);
		grantedPerms.add(perm);
	}

	public boolean checkPermissions(Person p, String permission) {
		boolean ris = false;
		for (int i=0; i<grantedPerms.size(); i++) {
			Permissions perm = grantedPerms.get(i);
			if (perm.pers.equals(p)) {
				if (permission.equals("deposit"))
					ris = perm.canDeposit;
				if (permission.equals("withdraw"))
					ris = perm.canWithdraw;
			}
		}
		return ris;
	}


	public boolean deposit(Person p, long amount) {
		if (checkPermissions(p, "deposit")) {
			Operation op = new Operation("deposit", amount);
			balance = balance+amount;
			history.add(op);
			return true;
		}
		else return false;
	}

	public boolean withdraw(Person p, long amount) {
		if (checkPermissions(p, "withdraw")) {
			Operation op = new Operation("withdraw", amount);
			balance = balance-amount;
			history.add(op);
			return true;
		}
		else return false;
	}

	public long getBalance() {
		return balance;
	}

	public void printHistory() {
		for (int i=0; i<history.size(); i++) {
			System.out.println(history.get(i));
		}
	}

}
