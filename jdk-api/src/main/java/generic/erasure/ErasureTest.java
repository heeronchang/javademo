package generic.erasure;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/21
 */
public class ErasureTest {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<String> list1 = new ArrayList<>();
        list1.add("hello");
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(1);

        System.out.println(list1.getClass() == list2.getClass());

        list2.getClass().getMethod("add", Object.class).invoke(list2, "sss");
        System.out.println(list2.get(1));
    }
}
