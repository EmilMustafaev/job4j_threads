package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int limit;

    public SimpleBlockingQueue(final int limit) {
        this.limit = limit;
    }

    public synchronized void offer(T value) throws InterruptedException {

            while (queue.size() == limit) {
                wait();
            }
            queue.add(value);
            notifyAll();
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        T value = queue.poll();
        notifyAll();
        return value;
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}
