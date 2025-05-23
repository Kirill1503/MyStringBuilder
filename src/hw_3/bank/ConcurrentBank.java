package hw_3.bank;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentBank {

    private final ConcurrentHashMap<Long, BankAccount> accounts = new ConcurrentHashMap<>();

    public BankAccount createAccount(BigDecimal amount) {
        BankAccount account = new BankAccount(amount);
        accounts.put(account.getId(), account);
        return account;
    }

    public void transfer(BankAccount from, BankAccount to, BigDecimal amount) {
        BankAccount firstLock = from.getId() < to.getId() ? from : to;
        BankAccount secondLock = from.getId() < to.getId() ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }


    public BigDecimal getTotalBalance() {
        BigDecimal totalBalance = new BigDecimal(0);
        for (BankAccount account : accounts.values()) {
            totalBalance = totalBalance.add(account.getBalance());
        }
        return totalBalance;
    }

    public ConcurrentHashMap<Long, BankAccount> getAccounts() {
        return accounts;
    }
}
