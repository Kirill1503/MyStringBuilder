package hw_3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class ComplexTask {
    private final int taskId;
    private final CyclicBarrier barrier;
    private static final Random random = new Random();

    public ComplexTask(int taskId, CyclicBarrier barrier) {
        this.taskId = taskId;
        this.barrier = barrier;
    }

    public void execute() {
        try {
            int workTime = random.nextInt(1000) + 500;
            System.out.println(Thread.currentThread().getName() + " начал задачу " + taskId +
                    " (будет выполняться " + workTime + " мс)");

            Thread.sleep(workTime);

            System.out.println(Thread.currentThread().getName() + " завершил задачу " + taskId);

            barrier.await();

            System.out.println(Thread.currentThread().getName() + " прошел барьер после задачи " + taskId);
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            System.err.println("Задача " + taskId + " прервана: " + e.getMessage());
        }
    }
}
