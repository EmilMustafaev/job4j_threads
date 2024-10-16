package ru.job4j.io;

import java.io.*;

public class FileWriter {
    private final File file;

    public FileWriter(final File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (OutputStream o = new BufferedOutputStream(new FileOutputStream(file))) {
            o.write(content.getBytes());
        }
    }
}

