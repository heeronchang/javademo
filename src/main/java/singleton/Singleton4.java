package singleton;

/**
 * 使用内部类延迟创建，使用了JVM 的类加载机制，保证线程安全
 */
public class Singleton4 {

    private static class SingletonHolder{
        public static Singleton4 instance = new Singleton4();
    }

    public static Singleton4 getInstance()
    {
        return SingletonHolder.instance;
    }
}
