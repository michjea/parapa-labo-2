import java.util.LinkedList;
import java.util.Queue;

public class Buffer {
    private int capacity;
    private Queue<Integer> buffer;
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

    public synchronized void produce(int value) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        buffer.add(value);
        count++;
        // System.out.println("Produced: " + value);
        notifyAll();
    }

    public synchronized int consume() {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int value = buffer.remove();
        count++;
        // System.out.println("Consumed: " + value);
        notifyAll();
        return value;
    }

    public int getCount() {
        return count;
    }

    public int getTotalAccesses() {
        return totalAccesses;
    }

}
