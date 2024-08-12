package interview;

/**
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */
public class PrintOddEvenWithSynchronized2 {
    private static int n;
    private static int current = 1;
    private static final Object obj = new Object();

    static class Printer implements Runnable {

        @Override
        public void run() {
            while (current <= n) {
                synchronized (obj) {
                    // 打印数字，并立即释放锁
                    System.out.println(Thread.currentThread().getName() + "打印:" + current++);
                    obj.notify();
                    if (current <= n) {
                        try {
                            obj.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        PrintOddEvenWithSynchronized2.n = 10;
        new Thread(new Printer(), "奇数线程").start();
        new Thread(new Printer(), "偶数线程").start();
    }
}
