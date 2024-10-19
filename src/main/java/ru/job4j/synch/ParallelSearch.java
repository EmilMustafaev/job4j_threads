package ru.job4j.synch;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);

        final Thread consumer = new Thread(
                () -> {
                    while (true) {
                        try {
                            Integer value = queue.poll();
                            if (value == null) {
                                System.out.println("Потребитель получил маркер о завершении работы : " + value);
                                break;
                            }
                            System.out.println("Потребитель извлек элемент " + value);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();

        new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        queue.offer(index);
                        System.out.println("Производитель добавил элемент " + index);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    queue.offer(null);
                }
        ).start();
    }
}
