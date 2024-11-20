package netty.future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Java Future demo
 * @author 叽哒嘎叽
 * @since 2024/11/19
 */

public class JavaFuture {
    private static Logger log = LoggerFactory.getLogger(JavaFuture.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("计算中...");
                Thread.sleep(1000);
                return 1;
            }
        });

        log.debug("waiting calculate");
        log.debug("result: {}", future.get());
    }
}
