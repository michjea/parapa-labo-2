import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Buffer {
    private int capacity;
    private LinkedList<Integer> buffer;
    private int totalAccesses;
    private int count = 0;

    public Buffer(int capacity, int totalAccesses) {
        this.capacity = capacity;
        this.buffer = new LinkedList<>();
        this.totalAccesses = totalAccesses;
        this.count = 0;
    }

    public int getSize() {
        return buffer.size();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public boolean isFull() {
        return buffer.size() >= capacity;
    }

    public synchronized void produce(int value) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        buffer.add(value);
        count++;
        // System.out.println("Produced: " + value);
        notify();
    }

    public synchronized int consume() throws InterruptedException {

        while (isEmpty()) {
            wait();
        }

        int value = buffer.remove();
        count++;
        // System.out.println("Consumed: " + value);
        notify();
        return value;
    }

    public int getCount() {
        return count;
    }

    public int getTotalAccesses() {
        return totalAccesses;
    }

}
