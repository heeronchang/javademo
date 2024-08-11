package lock.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/11
 */
public class ReentrantTest {
    public static void hello() {
        System.out.println("hello");
    }
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        hello();
        lock.unlock();
    }
}