public class Producer implements Runnable {
    private Buffer buffer;
    private int count;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        while (this.buffer.getCount() < this.buffer.getTotalAccesses()) {
            int data = (int) (Math.random() * 100);
            buffer.produce(data);
            count++;
        }
    }
}
