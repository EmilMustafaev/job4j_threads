package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class FileReader {
    private final File file;

    public FileReader(final File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = input.read()) != -1) {
                char ch = (char) data;
                if (filter.test(ch)) {
                    output.append(ch);
                }
            }
        }
        return output.toString();
    }

    public String getContent() throws IOException {
        return getContent(ch -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(ch -> ch < 0x80);
    }
}
