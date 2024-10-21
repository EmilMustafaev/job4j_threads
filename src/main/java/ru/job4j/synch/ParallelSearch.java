package ru.job4j.synch;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);

        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {

                            Integer value = queue.poll();
                            System.out.println("Потребитель извлек элемент: " + value);
                        } catch (InterruptedException e) {

                            System.out.println("Потребитель завершил свою работу");
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        consumer.start();

        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            System.out.println("Производитель добавил элемент: " + index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );

        producer.start();

        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        consumer.interrupt();
    }
    }
