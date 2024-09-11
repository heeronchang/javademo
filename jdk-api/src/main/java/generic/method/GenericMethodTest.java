package generic.method;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/21
 */
public class GenericMethodTest {
    public <T> T getObject(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        T t = clazz.newInstance();
        return t;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        GenericMethodTest genericMethodTest = new GenericMethodTest();
        User user = genericMethodTest.getObject(User.class);
        user.setAge(18);
        user.setName("jiajada");
        System.out.println(user.getAge());
        System.out.println(user.getName());

        Order order = (Order) genericMethodTest.getObject(Class.forName("generic.method.Order"));
        order.setNo("123456");
        System.out.println(order.getNo());
    }
}
