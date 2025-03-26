package lab03.assignments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;

public class VectorStream2 {
    private static final int STREAM_LENGTH = 1000000;
    private static final int VECTOR_LENGTH = 10;

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
        private volatile static int nowa_suma = 0;
        private static int vectorNo = 0;
        private static Semaphore mutex = new Semaphore(1);
        private static Semaphore gate = new Semaphore(0);
        private volatile static int finished = 0;

        public Helper(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < STREAM_LENGTH; ++i) {
                    vector[this.id] = vectorDefinition.applyAsInt(suma, this.id);
                    mutex.acquire();
                    nowa_suma += vector[this.id];
                    finished += 1;

                    if (finished == VECTOR_LENGTH) {
                        suma = nowa_suma;
                        System.out.println(vectorNo + " -> " + suma);
                        vectorNo++;
                        nowa_suma = 0;
                        finished -= 1;
                        gate.release();     /* DSK */
                    } else {
                        mutex.release();
                        gate.acquire();     /* DSK */
                        finished -= 1;
                        if (finished == 0) {
                            mutex.release();
                        } else {
                            gate.release();
                        }
                    }
                }
            } catch (InterruptedException e) {
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
