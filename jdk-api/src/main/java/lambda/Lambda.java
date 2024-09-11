package lambda;

import java.util.Arrays;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/15
 */
public class Lambda {
    public static void main(String[] args) {
//        int[] arr = new int[]{1,2,3,4,5};
//        Arrays.stream(arr).parallel().forEach(System.out::println);


        String s1 = new String("aaa");
        String s2 = new String("aaa");
        System.out.println(s1 == s2);           // false
        String s3 = s1.intern();
        System.out.println(s1 == s3);  // true
    }
}
