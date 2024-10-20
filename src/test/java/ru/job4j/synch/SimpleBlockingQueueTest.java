package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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

        Thread producer = new Thread(() -> {
            try {
                queue.offer(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);

        Thread producer = new Thread(
                () -> IntStream.range(0, 10).forEach(value -> {
                    try {
                        queue.offer(value);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                })
        );

        Thread consumer = new Thread(
                () -> {
                    try {
                        while (!Thread.currentThread().isInterrupted() || !queue.isEmpty()) {
                            Integer value = queue.poll();
                            buffer.add(value);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
        );

        producer.start();
        consumer.start();

        producer.join();

        consumer.interrupt();

        consumer.join();

        assertThat(buffer).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    }
}