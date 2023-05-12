public class Consumer implements Runnable {
    private Buffer buffer;
    private int count;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void run() {
        while (this.buffer.getCount() < this.buffer.getTotalAccesses()) {
            int data = buffer.consume();
            count++;
        }
    }
}
