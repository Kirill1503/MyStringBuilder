package hw_3.bank;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class BankAccount {

    private static final AtomicLong ID = new AtomicLong();
    private final long id;
    private BigDecimal balance;

    public BankAccount(BigDecimal balance) {
        id = ID.incrementAndGet();
        this.balance = balance;
    }

    public synchronized void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public synchronized void withdraw(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
        } else {
            throw new IllegalArgumentException("Недостаточно средств");
        }
    }

    public synchronized BigDecimal getBalance() {
        return this.balance;
    }

    public long getId() {
        return this.id;
    }
}
