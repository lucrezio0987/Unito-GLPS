 class BankTestSynchr {
	 public static void main(String[] args) {
		Bank b = new Bank(10, 1000);
		b.printTotal();
		System.out.println("%%%%%%%%%%%%%%%%%%\n\n");
		int num = 10;
		TransferThread[] tt = new TransferThread[num];
      	for (int i=0; i < num; i++) {
			// se metto una soglia di prelevamento altissima rischio la non terminazione
			// del programma perché le operazioni di prelevamento si bloccano
			//tt[i] = new TransferThread(b,80000);
			tt[i] = new TransferThread(b,8000);
			tt[i].start();
		}

		// attendo fine di tutti i trasferimenti per stampare totale in banca
      	for (int i=0; i < num; i++)
         	try {
         		tt[i].join();
			}
			catch (InterruptedException e) {System.out.println(e.getMessage());}
		System.out.println("\n\n%%%%%%%%%%%%%%%%%%");
		System.out.println("%%%%%%%%%%%%%%%%%%");
		b.printTotal();
	}

}


class BankAccount {
   private int balance = 0;

   public synchronized void deposit(int amount) {
	   	int temp = balance;
      	temp = temp + amount;
      	balance = temp;
      	notifyAll();
   }

			// se la soglia di prelevamento è troppo alta withdraw si blocca
   public synchronized void withdraw(int amount) {
	   while (amount > balance)
         try {
			 wait();
         } catch (InterruptedException e){}
      int temp = balance;
      try {
         Thread.sleep(1);
          } catch(InterruptedException e) {}
      temp = temp - amount;
      balance = temp;
   }

   public synchronized int getBalance() {
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

   public void transfer(int from, int to, int amount)	{
   		System.out.println("trasferimento da " + from + " a " + to + " di " + amount);
     	accounts[from].withdraw(amount);
      	System.out.println("ritirati da " + from + " " + amount);
      	accounts[to].deposit(amount);
      	System.out.println("versati a " + to + " " + amount);
   }

	   public int size() {
		   return accounts.length;
   }

   public void printTotal() {
	   	int sum = 0;
      	for (int i=0; i < accounts.length; i++)
         sum = sum + accounts[i].getBalance();
      	System.out.println("\nSomma totale in banca: " + sum);
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