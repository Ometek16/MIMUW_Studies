package lab04.assignments;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

public class MatrixRowSums {
    private static final int N_ROWS = 100;
    private static final int N_COLUMNS = 100;

    private static IntBinaryOperator matrixDefinition = (row, col) -> {
        int a = 2 * col + 1;
        return (row + 1) * (a % 4 - 2) * a;
    };

    private static void printRowSumsSequentially() {
        for (int r = 0; r < N_ROWS; ++r) {
            int sum = 0;
            for (int c = 0; c < N_COLUMNS; ++c) {
                sum += matrixDefinition.applyAsInt(r, c);
            }
            System.out.println(r + " -> " + sum);
        }
    }

    private static void printRowSumsInParallel() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        ConcurrentHashMap<Integer, LinkedBlockingQueue<Integer>> matrix = new ConcurrentHashMap<>();

        for (int c = 0; c < N_COLUMNS; ++c) {
            final int myColumn = c;
            threads.add(new Thread(() -> {
                matrix.computeIfAbsent(myColumn, _ -> new LinkedBlockingQueue<>());
                for (int r = 0; r < N_ROWS; ++r) {
                    try {
                        matrix.get(myColumn).put(matrixDefinition.applyAsInt(r, myColumn));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        }
        for (Thread t : threads) {
            t.start();
        }

        for (int r = 0; r < N_ROWS; ++r) {
            int sum = 0;
            for (int c = 0; c < N_COLUMNS; ++c) {
                matrix.computeIfAbsent(c, _ -> new LinkedBlockingQueue<>());
                sum += matrix.get(c).take();
            }
            System.out.println(r + " -> " + sum);
        }

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            for (Thread t : threads) {
                t.interrupt();
            }
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("-- Sequentially --");
            printRowSumsSequentially();
            System.out.println("-- In parallel --");
            printRowSumsInParallel();
            System.out.println("-- End --");
        } catch (InterruptedException e) {
            System.err.println("Main interrupted.");
        }
    }
}
