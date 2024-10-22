package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelSearchTest {

    @Test
    public void whenSearchInteger() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int index = ParallelSearch.search(array, 7);
        assertEquals(6, index);
    }

    @Test
    public void whenSearchString() {
        String[] array = {"apple", "banana", "cherry", "date"};
        int index = ParallelSearch.search(array, "cherry");
        assertEquals(2, index);
    }

    @Test
    public void whenSmallArray() {
        Integer[] array = {1, 2, 3, 4, 5};
        int index = ParallelSearch.search(array, 4);
        assertEquals(3, index);
    }

    @Test
    public void whenLargeArray() {
        Integer[] array = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            array[i] = i;
        }
        int index = ParallelSearch.search(array, 987);
        assertEquals(987, index);
    }

    @Test
    public void whenElementNotFound() {
        Integer[] array = {1, 2, 3, 4, 5};
        int index = ParallelSearch.search(array, 10);
        assertEquals(-1, index);
    }
}