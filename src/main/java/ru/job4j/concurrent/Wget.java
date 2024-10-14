package ru.job4j.concurrent;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream("downloaded_file")) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer, 0, 1024)) != -1) {
                long startTime = System.currentTimeMillis();
                output.write(buffer, 0, bytesRead);

                long elapsedTime = System.currentTimeMillis() - startTime;
                int expectedTime = bytesRead / speed;

                if (elapsedTime < expectedTime) {
                    Thread.sleep(expectedTime - elapsedTime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Usage: java Wget <URL> <Speed>");
            return;
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
