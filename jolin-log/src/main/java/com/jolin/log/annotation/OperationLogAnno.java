package com.jolin.log.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD,ElementType.TYPE  })
@Documented
public @interface OperationLogAnno {

	String module() default ""; // module name

	String logInfo() default ""; // method description

	String logClassName() default "";//Used to determine which entity class to operate on

	String  logOperationType() default "";// 1 Create  2 Modify 3 Delete ,4 Query,5 Login in，6 Login out

	String desc() default "";// The method description is used in the section
}
