package ru.job4j.pools;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RolColSumTest {

    @Test
    void whenSumThenCorrectSums() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        Sums[] result = RolColSum.sum(matrix);
        assertArrayEquals(expected, result);
    }

    @Test
    void whenAsyncSumThenCorrectSums() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        Sums[] expected = {
                new Sums(6, 12),
                new Sums(15, 15),
                new Sums(24, 18)
        };
        Sums[] result = RolColSum.asyncSum(matrix);
        assertArrayEquals(expected, result);
    }
}