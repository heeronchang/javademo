package interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */
public class PrintOddEvenWithLock {
    private final int n;
    private int current = 1;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition oddCondition = lock.newCondition();
    private final Condition evenCondition = lock.newCondition();

    public PrintOddEvenWithLock(int n) {
        this.n = n;
    }

    public void oddPrint() {
        for (int i = 1; i <= n; i += 2) {
            lock.lock();
            try {
                while (current % 2 == 0) {
                    oddCondition.await();
                }
                System.out.println(Thread.currentThread().getName() + ":" + current);
                current++;
                evenCondition.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public void evenPrint() {
        for (int i = 2; i <= n; i += 2) {
            lock.lock();
            try {
                while (current % 2 != 0) {
                    evenCondition.await();
                }
                System.out.println(Thread.currentThread().getName() + ":" + current);
                current++;
                oddCondition.signal();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        PrintOddEvenWithLock printOddEven = new PrintOddEvenWithLock(10);
        new Thread(printOddEven::oddPrint, "odd").start();
        new Thread(printOddEven::evenPrint, "even").start();
    }
}
