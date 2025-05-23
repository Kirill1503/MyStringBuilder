package hw_3;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ComplexTaskExecutor {
    private final ExecutorService executor;

    public ComplexTaskExecutor(int threads) {
        this.executor = Executors.newFixedThreadPool(threads);
    }

    public synchronized void executeTasks(int numberOfTasks) {
        CyclicBarrier barrier = new CyclicBarrier(numberOfTasks, () -> {
            System.out.println("Все " + numberOfTasks + " задач завершены! Барьер преодолен");
        });

        for (int i = 1; i <= numberOfTasks; i++) {
            final int taskId = i;
            executor.submit(() -> {
                ComplexTask task = new ComplexTask(taskId, barrier);
                task.execute();
            });
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
