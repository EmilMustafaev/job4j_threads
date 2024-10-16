package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        var process = new char[]{'-', '\\', '|', '/'};
        int index = 0;
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int currentIndex = index % process.length;
                System.out.print("\r load: " + process[currentIndex]);
                index++;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();

    }
}
