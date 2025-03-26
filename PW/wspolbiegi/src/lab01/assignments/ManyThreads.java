package lab01.assignments;

import java.util.concurrent.ThreadLocalRandom;

public class ManyThreads {
    private static final int THREAD_COUNT = 100; // If it works for 5, you can increase it to 100.

    private static class MyRunnable implements Runnable {
        private final int nSubthreads;

        public MyRunnable(int nSubthreads) {
            this.nSubthreads = nSubthreads;
        }

        @Override
        public void run() {
            if (this.nSubthreads > 1) {
                Thread kiddo = new Thread(new MyRunnable(this.nSubthreads - 1), "kiddo");
                // System.out.println(kiddo);
                kiddo.start();
            }
            for (int i = 0; i < 100000; i++) {
                // Math.random();
                ThreadLocalRandom.current().nextDouble(1.0);
            }

            System.out.println(nSubthreads);
        }
    }

    public static void main(String[] args) {
        Thread mainThread = new Thread(new MyRunnable(THREAD_COUNT));
        mainThread.start();
    }
}
