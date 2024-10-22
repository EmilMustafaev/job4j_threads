package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification() {
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
            String body = String.format("Add a new event to %s", user.getEmail());
            send(subject, body, user.getEmail());
        });

    }

    public void close() {
        pool.shutdown();
        try {
            if (!pool.isTerminated()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("EmailNotification service is closed.");
    }

    public void send(String subject, String body, String email) {

    }

    public static void main(String[] args) {
        EmailNotification service = new EmailNotification();

        User user = new User("Emil Mustafaev", "mustafaev@mail.ru");

        service.emailTo(user);

        service.close();
    }
}
