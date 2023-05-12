import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.LinkedList;
import java.util.Queue;

public class MonitoringProducerConsumer {
    private static int N_ACTIONS = 1000;

    public static void main(String[] args) throws Exception {
        int bufferSize = 10;

        int producerCount = 15;
        int consumerCount = 20;

        // Buffer size impact
        System.out.println("\nStart simulation with bufferSize=" + 10 + ", producerCount=" + producerCount
                + ", consumerCount=" + consumerCount);
        start(10, producerCount, consumerCount);

        System.out.println("\nStart simulation with bufferSize=" + 50 + ", producerCount=" + producerCount
                + ", consumerCount=" + consumerCount);
        start(50, producerCount, consumerCount);

        System.out.println("\nStart simulation with bufferSize=" + 100 + ", producerCount=" + producerCount
                + ", consumerCount=" + consumerCount);
        start(100, producerCount, consumerCount);

        // Producer / consumers count impact
        System.out.println("\nStart simulation with bufferSize=" + 100 + ", producerCount=" + 10
                + ", consumerCount=" + 10);
        start(100, 10, 10);
        // Producer / consumers count impact
        System.out.println("\nStart simulation with bufferSize=" + 100 + ", producerCount=" + 20
                + ", consumerCount=" + 20);
        start(100, 20, 20);

        System.out.println("\nStart simulation with bufferSize=" + 100 + ", producerCount=" + 40
                + ", consumerCount=" + 40);
        start(100, 40, 40);

        // Producer / consumers impact with different values
        System.out.println("\nStart simulation with bufferSize=" + 100 + ", producerCount=" + 10
                + ", consumerCount=" + 50);
        start(100, 10, 50);

        System.out.println("\nStart simulation with bufferSize=" + 100 + ", producerCount=" + 50
                + ", consumerCount=" + 10);
        start(100, 50, 10);
    }

    public static void start(int bufferSize, int producerCount, int consumerCount) {

        Buffer sharedBuffer = new Buffer(bufferSize, N_ACTIONS);

        Thread[] producerThreads = new Thread[producerCount];
        Thread[] consumerThreads = new Thread[consumerCount];

        // start time
        double startTime = System.currentTimeMillis();

        for (int i = 0; i < producerCount; i++) {
            Producer producer = new Producer(sharedBuffer);
            producerThreads[i] = new Thread(producer);
            producerThreads[i].start();
        }

        for (int i = 0; i < consumerCount; i++) {
            Consumer consumer = new Consumer(sharedBuffer);
            consumerThreads[i] = new Thread(consumer);
            consumerThreads[i].start();
        }

        // Wait for all producer threads to finish
        for (Thread producerThread : producerThreads) {
            try {
                producerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Wait for all consumer threads to finish
        for (Thread consumerThread : consumerThreads) {
            try {
                consumerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double endTime = System.currentTimeMillis();

        double time = endTime - startTime;
        System.out.println("Consumers number : " + consumerCount);
        System.out.println("Producers number : " + producerCount);

        System.out.println("Duration : " + time + " ms");

        System.out.println("Total produced: " + N_ACTIONS);

        double debit = N_ACTIONS / time;
        System.out.println("Debit : " + debit + " actions/s");

        double latence = time / N_ACTIONS;
        System.out.println("Latence : " + latence + " ms/action");
    }
}