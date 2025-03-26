package lab03.assignments;

import lab01.assignments.Primes;
import lab03.examples.ArrayRearrangement;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

public class VectorStream {
    private static final int STREAM_LENGTH = 100000;
    private static final int VECTOR_LENGTH = 10;

    private static final CyclicBarrier barrier = new CyclicBarrier(VECTOR_LENGTH, Helper::update_sum);

    /**
     * Function that defines how vectors are computed: the i-th element depends on
     * the previous sum and the index i.
     * The sum of elements in the previous vector is initially given as zero.
     */
    private final static IntBinaryOperator vectorDefinition = (previousSum, i) -> {
        int a = 2 * i + 1;
        return (previousSum / VECTOR_LENGTH + 1) * (a % 4 - 2) * a;
    };

    private static void computeVectorStreamSequentially() {
        int[] vector = new int[VECTOR_LENGTH];
        int sum = 0;
        for (int vectorNo = 0; vectorNo < STREAM_LENGTH; ++vectorNo) {
            for (int i = 0; i < VECTOR_LENGTH; ++i) {
                vector[i] = vectorDefinition.applyAsInt(sum, i);
            }
            sum = 0;
            for (int x : vector) {
                sum += x;
            }
            System.out.println(vectorNo + " -> " + sum);
        }
    }

    private static class Helper implements Runnable {
        private final int id;
        private static int[] vector = new int[VECTOR_LENGTH];;
        private volatile static int suma = 0;
        private static AtomicInteger nowa_suma = new AtomicInteger(0);
        private static int vectorNo = 0;

        public Helper(int id) {
            this.id = id;
        }

        public static void update_sum() {
            // suma = nowa_suma.get();
            // nowa_suma.set(0);
            suma = 0;
            for (int i = 0; i < VECTOR_LENGTH; ++i) {
                suma += vector[i];
            }
            System.out.println(vectorNo + " -> " + suma);
            vectorNo++;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < STREAM_LENGTH; ++i) {
                    vector[this.id] = vectorDefinition.applyAsInt(suma, this.id);
                    // nowa_suma.addAndGet(vector[this.id]);
                    barrier.await();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                System.err.println(Thread.currentThread().getName() + " interrupted.");
            }
        }
    }

    private static void computeVectorStreamInParallel() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < VECTOR_LENGTH; ++i) {
            threads.add(new Thread(new Helper(i)));
        }

        int sum = 0;
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("-- Sequentially --");
            computeVectorStreamSequentially();
            System.out.println("-- Parallel --");
            computeVectorStreamInParallel();
            System.out.println("-- End --");
        } catch (InterruptedException e) {
            System.err.println("Main interrupted.");
        }
    }
}
