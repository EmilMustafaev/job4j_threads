package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    private static Sums calculateSums(int[][] matrix, int index) {
        int rowSum = 0;
        int colSum = 0;
        int size = matrix.length;

        for (int j = 0; j < size; j++) {
            rowSum += matrix[index][j];
            colSum += matrix[j][index];
        }

        return new Sums(rowSum, colSum);
    }


    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = calculateSums(matrix, i);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        CompletableFuture<Sums>[] futures = new CompletableFuture[size];

        for (int i = 0; i < size; i++) {
            int rowIndex = i;
            futures[i] = CompletableFuture.supplyAsync(() -> calculateSums(matrix, rowIndex));
        }

        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = futures[i].get();
        }
        return sums;
    }

}