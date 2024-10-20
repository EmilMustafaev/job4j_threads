package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int ref;
        int newValue;
        do {
            ref = count.get();
            newValue = ref + 1;
        } while (!count.compareAndSet(ref, newValue));
    }

    public int get() {
        return count.get();
    }
}
