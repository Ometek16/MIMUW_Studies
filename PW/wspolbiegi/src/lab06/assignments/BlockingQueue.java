package lab06.assignments;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private final int capacity;
    private final Queue<T> queue;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    public synchronized T take() throws InterruptedException {
        while (this.queue.isEmpty()) { // Wait if queue is empty
            wait();
        }
        T item = this.queue.poll();
        notifyAll(); // Notify after taking an item (in case waiting put threads)
        return item;
    }

    public synchronized void put(T item) throws InterruptedException {
        while (this.queue.size() == this.capacity) { // Wait if queue is full
            wait();
        }
        this.queue.add(item);
        notifyAll(); // Notify after adding an item (in case waiting take threads)
    }

    public synchronized int getSize() {
        return this.queue.size();
    }

    public int getCapacity() {
        return capacity;
    }
}
