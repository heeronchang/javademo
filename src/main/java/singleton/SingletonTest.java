package singleton;

public class SingletonTest {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton4 singleton4 = null;
                singleton4 = Singleton4.getInstance();
                System.out.println("singleton4:" + singleton4);
            }).start();
        }
    }
}


