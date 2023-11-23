class BankTestWrong {
	public static void main(String[] args) {
		Bank b = new Bank(10, 1000);
      	for (int i=0; i < 10; i++)
         	new TransferThread(b, 100).start();
   	}
}


class BankAccount {
   private int balance;

   public void deposit(int amount) {
	   	int temp = balance;
      	temp = temp + amount;
      	balance = temp;
   }

   public boolean withdraw(int amount) {
	if (amount > balance)
		return false;
    int temp = balance;
    try {
         Thread.sleep(1);
          } catch(InterruptedException e) {}
    temp = temp - amount;
    balance = temp;
    return true;
   }

   public int getBalance() {
	   return balance;
   }
}

class Bank {
	private BankAccount[] accounts;

   	Bank(int nacc, int init) {
	   accounts = new BankAccount[nacc];
	   for (int i=0; i<nacc; i++) {
		   accounts[i] = new BankAccount();
           accounts[i].deposit(init);
       }
   	}

   public void transfer(int from, int to, int amount) {
	   	System.out.println("trasferimento da " + from + " a " + to +
                        " di " + amount);
      	if (!accounts[from].withdraw(amount))
      		return;
      	accounts[to].deposit(amount);
      	printTotal();
   }

   public int size() {
	   return accounts.length;
   }

   public void printTotal() {
	   	int sum = 0;
      	for (int i=0; i < accounts.length; i++)
         	sum = sum + accounts[i].getBalance();
      	System.out.println("Somma totale di denaro in banca: " + sum);
   }
}

class TransferThread extends Thread {
	private Bank bank;
   	private int maxAmount;

   public TransferThread(Bank b, int max) {
	   	bank = b;
      	maxAmount = max;
   }

   public void run() {
         int from = (int)(bank.size() * Math.random());
         int to = (int)(bank.size() * Math.random());
         int amount = (int)(maxAmount * Math.random());
         bank.transfer(from,to,amount);
   }
}