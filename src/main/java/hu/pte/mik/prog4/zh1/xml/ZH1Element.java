package hu.pte.mik.prog4.zh1.xml;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ZH1Element {
    String text() default "";
}
