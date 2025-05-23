import hw_1.MyStringBuilder;
import hw_2.Filter;
import hw_2.FilterImpl;
import hw_3.ComplexTaskExecutor;
import hw_3.MyBlockingQueue;
import hw_3.bank.BankAccount;
import hw_3.bank.ConcurrentBank;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        /*HW_1*/
        System.out.println("hw_1\n");
        MyStringBuilder stringBuilder = new MyStringBuilder();

        stringBuilder.append("Papa");
        System.out.println(stringBuilder);
        stringBuilder.append("Mama");
        System.out.println(stringBuilder);
        stringBuilder.append("Roma");
        System.out.println(stringBuilder);
        stringBuilder.append("Soda");
        System.out.println(stringBuilder);
        stringBuilder.undo();
        System.out.println(stringBuilder);
        stringBuilder.undo();
        System.out.println(stringBuilder);

        System.out.println("\n");
        /*HW_2*/
        System.out.println("hw_2\n");
        Integer[] arrayTest1 = new Integer[]{1, 3, 5, 6, 7, 44, 0};
        Double[] arrayTest2 = new Double[]{-1.1, -3.33, -5.23, -6.76, -7.21, -44.90};

        Filter<Integer> filter1 = new FilterImpl<>();
        Filter<Double> filter2 = new FilterImpl<>();

        System.out.println(Arrays.toString(filter(arrayTest1, filter1)));
        System.out.println(Arrays.toString(filter(arrayTest2, filter2)));

        Integer[] arrayTest3 = new Integer[]{1, 1, 1, 2, 2, 5, 5, 5, 5, 5, 8, 9, 11};
        String[] arrayTest4 = new String[]{"bob", "bob", "bob", "bob", "pop", "pop", "mom", "mom", "dad", "PAPA"};

        System.out.println(countElements(arrayTest3));
        System.out.println(countElements(arrayTest4));

        System.out.println("\n");

        /*HW_3*/
        System.out.println("hw_3\n");

        testConcurrency1();

        Thread.sleep(4000);
        System.out.println("\n".repeat(3));

        testConcurrency2();

        Thread.sleep(4000);
        System.out.println("\n".repeat(3));

        testConcurrency3();

        System.out.println("\n");
    }

    public static void testConcurrency1() {
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(3);

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.enqueue(i);
                    System.out.println("Добавлено: " + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    int item = queue.dequeue();
                    System.out.println("Извлечено: " + item);
                    Thread.sleep(300);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public static void testConcurrency2() throws InterruptedException {
        ComplexTaskExecutor taskExecutor = new ComplexTaskExecutor(5);

        Runnable testRunnable = () -> {
            System.out.println(Thread.currentThread().getName() + " started the test.");

            taskExecutor.executeTasks(5);

            System.out.println(Thread.currentThread().getName() + " completed the test.");
        };

        Thread thread1 = new Thread(testRunnable, "TestThread-1");
        Thread thread2 = new Thread(testRunnable, "TestThread-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            taskExecutor.shutdown();
        }
    }

    public static void testConcurrency3() {
        ConcurrentBank bank = new ConcurrentBank();

        // Создание счетов
        BankAccount account1 = bank.createAccount(BigDecimal.valueOf(1000));
        BankAccount account2 = bank.createAccount(BigDecimal.valueOf(500));

        // Перевод между счетами
        Thread transferThread1 = new Thread(() -> bank.transfer(account1, account2, BigDecimal.valueOf(200)));
        Thread transferThread2 = new Thread(() -> bank.transfer(account2, account1, BigDecimal.valueOf(100)));

        transferThread1.start();
        transferThread2.start();

        try {
            transferThread1.join();
            transferThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Вывод общего баланса
        System.out.println("Total balance: " + bank.getTotalBalance());
        System.out.println("First balance: " + bank.getAccounts().get(account1.getId()).getBalance());
        System.out.println("Second balance: " + bank.getAccounts().get(account2.getId()).getBalance());
    }


    public static <T> T[] filter(T[] array, Filter<T> filter) {
        T[] result = Arrays.copyOf(array, array.length);

        for (int i = 0; i < array.length; i++) {
            result[i] = filter.apply(array[i]);
        }

        return result;
    }

    public static <T> Map<T, Integer> countElements(T[] o) {
        Map<T, Integer> map = new HashMap<>();
        for (int i = 0; i < o.length; i++) {
            if (map.containsKey(o[i])) {
                map.put(o[i], map.get(o[i]) + 1);
            } else {
                map.put(o[i], 1);
            }
        }
        return map;
    }
}