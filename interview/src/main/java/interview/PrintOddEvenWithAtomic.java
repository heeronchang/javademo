package interview;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/28
 */
public class PrintOddEvenWithAtomic {
    private AtomicInteger count = new AtomicInteger(0);

    public void addAndPrintOdd(int num) {
        for (int i = 0; i < 100; i+=2) {
            while (true) {
                int current = count.get();
                if (current >= 100) {
                    return;
                }
                if (current % 2 == 0) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " odd: " + current);
                    count.addAndGet(1);
                }
            }
        }
    }

    public void addAndPrintEven(int num) {
        for (int i = 1; i < 100; i+=2) {
            while (true) {
                int current = count.get();
                if (current >= 100) {
                    return;
                }
                if (current % 2 != 0) {
                    System.out.println("Thread: " + Thread.currentThread().getName() + " even: " + current);
                    count.addAndGet(1);
                }
            }
        }
    }

    public static void main(String[] args) {
        PrintOddEvenWithAtomic printOddEvenWithAtomic = new PrintOddEvenWithAtomic();
        new Thread(() -> printOddEvenWithAtomic.addAndPrintOdd(1), "Thread-odd").start();
        new Thread(() -> printOddEvenWithAtomic.addAndPrintEven(1), "Thread-even").start();
    }
}
