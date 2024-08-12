package functional;

/**
 * 分支处理接口
 *
 * @author 叽嗒嘎叽
 * @since 2024/8/9
 */
@FunctionalInterface
public interface IfBranchHandle {
    /**
     * 分支操作
     *
     * @param trueHandle  为true时的操作
     * @param falseHandle 为false时的操作
     */
    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);
}

class TestIfBranchHandle {
    public static IfBranchHandle isTureOrFalse(boolean flag) {
        return (trueHandle, falseHandle) -> {
            if (flag) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    public static void main(String[] args) {
        isTureOrFalse(false).trueOrFalseHandle(() -> System.out.println("true"), () -> System.out.println("false"));
    }
}
