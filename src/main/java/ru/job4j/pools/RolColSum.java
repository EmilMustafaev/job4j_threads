package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

    }

    public static Sums[] sum(int[][] matrix) {
        int size = matrix.length;
        Sums[] sums = new Sums[size];

        for (int i = 0; i < size; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        int size = matrix.length;
        CompletableFuture<Sums>[] futures = new CompletableFuture[size];

        for (int i = 0; i < size; i++) {
            int rowIndex = i;
            futures[i] = CompletableFuture.supplyAsync(() -> {
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j < size; j++) {
                    rowSum += matrix[rowIndex][j];
                    colSum += matrix[j][rowIndex];
                }
                return new Sums(rowSum, colSum);
            });
        }

        Sums[] sums = new Sums[size];
        for (int i = 0; i < size; i++) {
            sums[i] = futures[i].get();
        }
        return sums;
    }

}