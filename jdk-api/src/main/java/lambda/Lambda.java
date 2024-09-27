package lambda;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/15
 */
public class Lambda {
    static class Person {
        public String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {
//        int[] arr = new int[]{1,2,3,4,5};
//        Arrays.stream(arr).parallel().forEach(System.out::println);


//        String s1 = new String("aaa");
//        String s2 = new String("aaa");
//        System.out.println(s1 == s2);           // false
//        String s3 = s1.intern();
//        System.out.println(s1 == s3);  // true

        Person[] persons = new Person[]{
                new Person("a", 1),
                new Person("b", 2),
                new Person("c", 3),
                new Person("d", 4),
                new Person("e", 5),
        };

        // 过滤
//        Arrays.stream(persons).filter(p -> p.age > 2).forEach(p -> System.out.println(p.name));

        // map中间流使用指定的函数操作每一个元素
//        Arrays.stream(persons).map(p -> {
//            p.name = p.name.toUpperCase();
//            p.age = p.age + 1;
//            return p;
//        }).forEach(p -> System.out.println(p.name + p.age));

        // collect，Collectors.toMap转换为Map
//        Map<String, Integer> map = Arrays.stream(persons).collect(Collectors.toMap(p -> p.name.toUpperCase(), p -> p.age));
//        System.out.println(map);

//        Set<String> set = Arrays.stream(persons).map(p -> p.name + ":" + p.age).collect(Collectors.toSet());
//        System.out.println(set);

//        Set<String> set = Arrays.stream(persons).map(p -> p.name + ":" + p.age).collect(Collectors.toCollection(HashSet::new));
//        System.out.println(set);

//        String string = Arrays.stream(persons).map(p -> p.name + ":" + p.age).collect(Collectors.joining(","));
//        System.out.println(string);

//        String string2 = Arrays.stream(persons).map(p -> p.name + ":" + p.age).collect(Collectors.joining(",", "[","]"));
//        System.out.println(string2);

//        List list = Arrays.stream(persons).flatMap(p -> Arrays.stream(new String[]{p.name, String.valueOf(p.age)})).collect(Collectors.toList());
//        System.out.println(list);

//        double x = Arrays.stream(persons).collect(Collectors.averagingInt(p -> p.age));
//        System.out.println(x);

//        Map<Boolean, List<Person>> map = Arrays.stream(persons).collect(Collectors.groupingBy(p -> p.age > 3));
//        System.out.println(map);

        Map<String, List<Person>> map2 = Arrays.stream(persons).collect(Collectors.groupingBy(p -> p.name));
        System.out.println(map2);
    }
}
