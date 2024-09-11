package generic.boundary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/21
 */
public class ExtendsTest {

    static class Info<T extends Number> {
        private T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    static class Info2<T> {
        private T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    public static void func(Info2<? super String> info2) {
        System.out.println(info2.getT());
    }

    public static void main(String[] args) {
        Info<Integer> info = new Info<>();
        info.setT(1);
        System.out.println(info.getT());

        Info2<String> infoStr = new Info2<>();
        infoStr.setT("hello");
        func(infoStr);
        Info2<Object> infoObj = new Info2<>();
        infoObj.setT(new Object());
        func(infoObj);

//        List<String>[] list1 = new ArrayList<String>[10];
//        List<String>[] list2 = new ArrayList<?>[10];
//        List<String>[] list3 = (List<String>[]) new ArrayList<?>[10];
//        List<?>[] list4 = new ArrayList<String>[10];
        List<?>[] list5 = new ArrayList<?>[10];
        List<String>[] list6 = new ArrayList[10];
        List<?>[] list7 = new ArrayList[10];
    }
}
