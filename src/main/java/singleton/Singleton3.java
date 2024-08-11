package singleton;

public class Singleton3 {
    private static Singleton3 instance;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (instance == null) {
            synchronized (Singleton3.class) {
                if (instance == null) {
                    instance = new Singleton3();
                }
            }
        }

        return instance;
    }

    // 测试
//    public static Singleton3 getInstance() throws InterruptedException {
//        if (instance == null) {
//            synchronized (Singleton3.class) {
//                if (instance == null) {
//                    Thread.sleep(1000);
//                    instance = new Singleton3();
//                }
//            }
//        }
//
//        return instance;
//    }
}
