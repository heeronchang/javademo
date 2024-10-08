package lock.reentrant;

import java.util.concurrent.Callable;
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

    class A  implements Callable<String> {
        public void a() {
            System.out.println("a");
        }

        @Override
        public String call() throws Exception {
            return "";
        }
    }
}