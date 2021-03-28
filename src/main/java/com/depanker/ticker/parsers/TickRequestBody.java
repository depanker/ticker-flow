package com.depanker.ticker.parsers;

import java.lang.annotation.*;

/**
 * TickRequestBodyArgumentResolver will only
 * be invoked when this annotation is present
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TickRequestBody {

    boolean required() default true;

}