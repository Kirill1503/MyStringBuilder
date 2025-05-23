package hw_3;

import java.util.LinkedList;
import java.util.Queue;

public class MyBlockingQueue<T> {
    private final Queue<T> queue;
    private final Integer maxSize;

    public MyBlockingQueue(Integer maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
        this.queue = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while (queue.size() == maxSize) {
            wait();
        }
        queue.add(item);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T item = queue.poll();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return queue.size();
    }
}

