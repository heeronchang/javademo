package interview;

public class PrintOddEvenWithSynchronized {

    private int n;
    private int current = 1;

    public PrintOddEvenWithSynchronized(int n) {
        this.n = n;
    }

    // 打印奇数
    public void printOdd() {
        for (int i = 1; i <= n; i+=2) {
            synchronized (this) {
                while (current % 2 == 0) { // 等待偶数线程打印
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(Thread.currentThread().getName() + ": " + i);
                current++;
//                notifyAll(); // 唤醒所有线程
                notify();
            }
        }
    }

    // 打印偶数
    public void printEven() {
        for (int i = 2; i <= n; i+=2) {
            synchronized (this) {
                while (current % 2 == 1) { // 等待奇数线程打印
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e);
                    }
                }

                System.out.println(Thread.currentThread().getName() + ": " + i);
                current++;
//                notifyAll();
                notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrintOddEvenWithSynchronized printOddEven = new PrintOddEvenWithSynchronized(10);
        Thread oddThread = new Thread(printOddEven::printOdd, "odd");
        Thread evenThread = new Thread(printOddEven::printEven, "even");
        oddThread.start();
        evenThread.start();
    }
}
