package annotation;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/21
 */
public class Child extends Parent {
    public void test() {
        Class clazz = Child.class;
        Annotation[]  annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation.toString());
            System.out.println(annotation.annotationType());
            if (annotation instanceof InheritedAnnotationTest) {
                InheritedAnnotationTest inheritedAnnotationTest = (InheritedAnnotationTest) annotation;
                System.out.println(inheritedAnnotationTest.number());
                Arrays.stream(inheritedAnnotationTest.values()).forEach(System.out::println);
            }
        }

//        InheritedAnnotationTest[] annotationTests = (InheritedAnnotationTest[]) clazz.getAnnotationsByType(InheritedAnnotationTest.class);
//        for (InheritedAnnotationTest annotationTest : annotationTests) {
//            System.out.println(annotationTest.number());
//            Arrays.stream(annotationTest.values()).forEach(System.out::println);
//        }
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.test();
    }
}
