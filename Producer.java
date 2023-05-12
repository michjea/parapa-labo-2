public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (this.buffer.getCount() < this.buffer.getTotalAccesses()) {
            int data = (int) (Math.random() * 100);
            try {
                buffer.produce(data);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
