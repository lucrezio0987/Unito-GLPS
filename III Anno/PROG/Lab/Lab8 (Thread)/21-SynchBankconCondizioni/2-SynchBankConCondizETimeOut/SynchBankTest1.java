/**
   @version 1.30 2004-08-01
   @author Cay Horstmann

   modificato con array di condizioni (una per ogni conto) e altro...
*/

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
   This program shows how multiple threads can safely access a data structure.
*/
public class SynchBankTest1 {

   public static final int NACCOUNTS = 20;
   public static final double INITIAL_BALANCE = 1000;

   public static void main(String[] args) {
      Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
      int i;
      for (i = 0; i < NACCOUNTS; i++) {
         TransferRunnable r = new TransferRunnable(b, i, INITIAL_BALANCE);
         new Thread(r).start();
      }
   }
}


/**
   A bank with a number of bank accounts.
*/
class Bank {
   /**
      Constructs the bank.
      @param n the number of accounts
      @param initialBalance the initial balance
      for each account
   */

   private final double[] accounts;
   private Lock bankLock;
   private Condition[] sufficientFunds;

   public Bank(int n, double initialBalance) {
      sufficientFunds = new Condition[n];
      accounts = new double[n];

      bankLock = new ReentrantLock();

      for (int i = 0; i < accounts.length; i++) {
		  	accounts[i] = initialBalance;
		  				// una condizione di wait per ciascuno conto
         	sufficientFunds[i] = bankLock.newCondition();
      }
   }


   /**
      Transfers money from one account to another.
      @param from the account to transfer from
      @param to the account to transfer to
      @param amount the amount to transfer
   */
   public void transfer(int from, int to, double amount) throws InterruptedException {
      bankLock.lock();
      try {
		  				// qui uso timeout per sbloccare il thread dopo 3 secondi di attesa
		  				// se nel frattempo non è stato caricato denaro sul conto rinuncia
		  				// al trasferimento
		 boolean check = false;
         //System.out.println(Thread.currentThread() + ": somma da spostare: " + amount);
         if (accounts[from] < amount)
            check = sufficientFunds[from].await(3000, TimeUnit.MILLISECONDS);
		 if (accounts[from] >= amount) {
         	System.out.print(Thread.currentThread());
         	accounts[from] -= amount;
         	System.out.printf(" %10.2f from %d to %d", amount, from, to);
         	accounts[to] += amount;
         	System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
         							// sblocca tutti per evitare deadlock
         	sufficientFunds[to].signalAll();
		 }
		 else {
			 System.out.println("NON HO ESEGUITO IL TRASFERIMENTO DI " + amount + ": > " + accounts[from]);
		 }
      }
      finally {
         bankLock.unlock();
      }
   }

   /**
      Gets the sum of all account balances.
      @return the total balance
   */

   					// NB: qui anche la lettura del total balance è fatta in mutua esclusione
   					// per evitare risultati parziali errati
   public double getTotalBalance() {
      bankLock.lock();
      try {
         double sum = 0;

         for (double a : accounts)
            sum += a;
         return sum;
      }
      finally {
         bankLock.unlock();
      }
   }

   /**
      Gets the number of accounts in the bank.
      @return the number of accounts
   */
   public int size() {
      return accounts.length;
   }
}

/**
   A runnable that transfers money from an account to other
   accounts in a bank.
*/
class TransferRunnable implements Runnable {

   private Bank bank;
   private int fromAccount;
   private double maxAmount;
   private int repetitions;
   private int DELAY = 10;

   /**
      Constructs a transfer runnable.
      @param b the bank between whose account money is transferred
      @param from the account to transfer money from
      @param max the maximum amount of money in each transfer
   */
   public TransferRunnable(Bank b, int from, double max) {
      bank = b;
      fromAccount = from;
      maxAmount = max;
   }

   public void run() {
      try {
         for (int i=0; i<3; i++) {
            int toAccount = (int) (bank.size() * Math.random());
            double amount = maxAmount * Math.random();
            bank.transfer(fromAccount, toAccount, amount);
            //bank.transfer(fromAccount, toAccount, 50*amount); // per bloccare i thread....
            Thread.sleep((int) (DELAY * Math.random()));
         }
      }
      catch (InterruptedException e) {}
   }
}

