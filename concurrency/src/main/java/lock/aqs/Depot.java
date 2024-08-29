package lock.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/29
 */
public class Depot extends Thread {
    private int capacity;
    private int size;

    private Lock lock;
    private Condition fullCondition;
    private Condition emptyCondition;

    public Depot(int capacity) {
        this.capacity = capacity;
        lock = new ReentrantLock();
        fullCondition = lock.newCondition();
        emptyCondition = lock.newCondition();
    }

    public void produce(int no) {
        lock.lock();
        try {
            int left = no;
            while (left > 0) {
                while (size >= capacity) {
                    System.out.println("仓库已满，等待消费" + Thread.currentThread() + " before await");
                    fullCondition.await();
                    System.out.println("仓库未满，继续生产" + Thread.currentThread() + " after await");
                }

                int inc = (left > (capacity - size)) ? (capacity - size) : left;
                size += inc;
                left -= inc;
                System.out.println("produce = " + inc + ", size = " + size);
                emptyCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int no) {
        try {
            lock.lock();
            int left = no;
            while (left > 0) {
                while (size <= 0) {
                    System.out.println("仓库已空，等待生产" + Thread.currentThread() + " before await");
                    emptyCondition.await();
                    System.out.println("仓库有货，继续消费" + Thread.currentThread() + " after await");
                }

                int dec = (left > size) ? size : left;
                size -= dec;
                left -= dec;
                System.out.println("consume = " + dec + ", size = " + size);
                fullCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Depot depot = new Depot(100);
        new Thread(() -> depot.produce(100)).start();
        new Thread(() -> depot.produce(50)).start();
        new Thread(() -> depot.consume(100)).start();
        new Thread(() -> depot.consume(50)).start();    }
}
