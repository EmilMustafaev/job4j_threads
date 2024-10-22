package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RolColSumTest {

    @Test
    void whenSumThenCorrectSums() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] result = RolColSum.sum(matrix);

        assertEquals(6, result[0].getRowSum());
        assertEquals(15, result[1].getRowSum());
        assertEquals(24, result[2].getRowSum());

        assertEquals(12, result[0].getColSum());
        assertEquals(15, result[1].getColSum());
        assertEquals(18, result[2].getColSum());
    }

    @Test
    void whenAsyncSumThenCorrectSums() throws ExecutionException, InterruptedException {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] result = RolColSum.asyncSum(matrix);

        assertEquals(6, result[0].getRowSum());
        assertEquals(15, result[1].getRowSum());
        assertEquals(24, result[2].getRowSum());

        assertEquals(12, result[0].getColSum());
        assertEquals(15, result[1].getColSum());
        assertEquals(18, result[2].getColSum());
    }
}