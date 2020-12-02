package com.example.order.annotatiion;

import java.lang.annotation.*;

@Target( ElementType.METHOD)
@Retention( RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceTarget {
    String name() default "";

    boolean readOnly() default false;
}
