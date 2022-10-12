package com.jolin.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The table id field is associated
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BaseJoinId {

    //The order of multiple primary table id fields, starting from 0
    Index index() default Index.first;

    enum Index {
        first,
        second,
        third,
        fourth,
        Fifth;
    }
}
