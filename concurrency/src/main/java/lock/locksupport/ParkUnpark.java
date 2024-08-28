package lock.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/28
 */
public class ParkUnpark extends Thread {
    private Object object;

    public ParkUnpark(Object object) {
        this.object = object;
    }

    public void run() {
        System.out.println("before unpark");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 获取blocker
        System.out.println("blocker " + LockSupport.getBlocker((Thread) object));
        // 释放许可
        LockSupport.unpark((Thread) object);
        // 休眠500ms，保证先执行park中的setBlocker(t, null);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 再次获取blocker
        System.out.println("blocker " + LockSupport.getBlocker((Thread) object));
        System.out.println("after unpark");
    }

    public static void main(String[] args) {
        ParkUnpark parkUnpark = new ParkUnpark(Thread.currentThread());
        parkUnpark.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("before park");
        LockSupport.park("ParkUnpark");
        System.out.println("after park");
    }
}
