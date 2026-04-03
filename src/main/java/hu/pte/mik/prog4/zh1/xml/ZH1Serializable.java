package hu.pte.mik.prog4.zh1.xml;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ZH1Serializable {
    String text() default "";
}
