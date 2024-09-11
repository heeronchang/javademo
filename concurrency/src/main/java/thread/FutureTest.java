package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/30
 */
public class FutureTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future = executorService.submit((Callable<Object>) () -> {
            Long start = System.currentTimeMillis();
            while (true) {
                Long current = System.currentTimeMillis();
                if (current - start > 5000) {
                    return 1;
                }
            }
        });
//        future.isDone();
        try {
            System.out.println("before future.get()");
            System.out.println(future.get());
            System.out.println("after future.get()");
        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
