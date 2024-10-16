package ru.job4j.concurrent;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String outputFileName;

    public Wget(String url, int speed, String outputFileName) {
        this.url = url;
        this.speed = speed;
        this.outputFileName = outputFileName;
    }

    @Override
    public void run() {
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(outputFileName)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            int totalBytesRead = 0;

            while ((bytesRead = input.read(buffer, 0, 1024)) != -1) {
                output.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                if (totalBytesRead >= speed) {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime < 1000) {
                        Thread.sleep(1000 - elapsedTime);
                    }

                    totalBytesRead = 0;
                    startTime = System.currentTimeMillis();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: java Wget <URL> <Speed> <FileName>");
        }

        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String outputFileName = args[2];

        Thread wget = new Thread(new Wget(url, speed, outputFileName));
        wget.start();
        wget.join();
    }
}
