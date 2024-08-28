package lock.locksupport;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/28
 */
public class WaitNotify extends Thread {
    public void run() {
        synchronized (this) {
            System.out.println("before notify");
            notify();
            System.out.println("after notify");
        }
    }

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify();
        synchronized (waitNotify) {
            try {
                waitNotify.start();
                Thread.sleep(3000L);
                System.out.println("before wait");
                waitNotify.wait();
                System.out.println("after wait");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
