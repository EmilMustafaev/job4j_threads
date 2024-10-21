package ru.job4j.cas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CASCountTest {

    @Test
    void whenInitialValueThen0() {
        CASCount count = new CASCount();
        assertEquals(0, count.get());
    }

    @Test
    void testSingleIncrement() {
        CASCount count = new CASCount();
        count.increment();
        assertEquals(1, count.get());
    }

    @Test
    void testMultipleIncrement() {
        CASCount count = new CASCount();
        count.increment();
        count.increment();
        count.increment();
        assertEquals(3, count.get());
    }

    @Test
    public void testConcurrentIncrements() throws InterruptedException {
        CASCount counter = new CASCount();
        int numberOfThreads = 100;
        int incrementsPerThread = 1000;


        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < incrementsPerThread; j++) {
                    counter.increment();
                }
                latch.countDown();
            });
        }


        latch.await();
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        int expectedValue = numberOfThreads * incrementsPerThread;
        assertEquals(expectedValue, counter.get(),
                "Count should be " + expectedValue + " after concurrent increments");
    }
}