package functional;

import java.util.function.Consumer;

/**
 * 非空值与空值处理
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */
public interface IPresentOrElseHandler<T extends Object> {
    /**
     * 非空值与空值处理
     * @param presentHandle
     * @param elseHandle
     */
    void presentOrElseHandle(Consumer<? extends T> presentHandle, Runnable elseHandle);
}

class TestIPresentOrElseHandler {
    public static IPresentOrElseHandler isBlankOrNotBland(String str) {
        return (consumer, runnable) -> {
            if (str == null || str.isEmpty()) {
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }

    public static void main(String[] args) {
        isBlankOrNotBland("hello consumer").presentOrElseHandle(System.out::println, () -> System.out.println("空值"));
    }
}