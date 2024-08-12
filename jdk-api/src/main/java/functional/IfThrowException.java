package functional;

/**
 * 处理抛出异常的if
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */

@FunctionalInterface
interface IfThrowException {
    /**
     * 抛出异常信息
     * @param message 异常信息
     */
    void throwMessage(String message);
}

class TestIfThrowException {
    public static IfThrowException isTrue(boolean condition) {
        return message -> {
            if (condition) {
                throw new RuntimeException(message);
            }
        };
    }

    public static void main(String[] args) {
        TestIfThrowException.isTrue(true).throwMessage("xxxxx message");
    }
}