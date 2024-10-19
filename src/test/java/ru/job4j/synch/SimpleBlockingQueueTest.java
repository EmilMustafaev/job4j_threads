package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class SimpleBlockingQueueTest {
    @Test
    void whenOfferThenPollReturnsSameElement() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        queue.offer(1);
        assertEquals(1, queue.poll());
    }

    @Test
    void whenQueueIsFullThenOfferBlocks() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        queue.offer(1);
        queue.offer(2);

        Thread producer = new Thread(() -> queue.offer(3));
        producer.start();

        Thread.sleep(100);
        assertTrue(producer.isAlive());

        queue.poll();
        producer.join();
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
    }

    @Test
    void whenQueueIsEmptyThenPollBlocks() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);

        Thread consumer = new Thread(() -> {
            try {
                queue.poll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();

        Thread.sleep(100);
        assertTrue(consumer.isAlive());

        queue.offer(1);
        consumer.join();
        assertTrue(queue.isEmpty());
    }
}