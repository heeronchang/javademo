package annotation;

import java.lang.annotation.*;

/**
 * @author 叽哒嘎叽
 * @since 2024/8/21
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface InheritedAnnotationTest {
    String[] values();
    int number();
}
