package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        System.out.println(first.getState());
        System.out.println(second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("Состояние первой нити: " + first.getState());
            System.out.println("Состояние второй нити: " + second.getState());
        }
        System.out.println("Состояние первой нити после завершения: " + first.getState());
        System.out.println("Состояние второй нити после завершения: " + second.getState());

        System.out.println("Работа Завершена");
    }
}
