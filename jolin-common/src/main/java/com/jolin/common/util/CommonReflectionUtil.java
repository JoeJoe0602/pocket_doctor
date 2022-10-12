package com.jolin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

// The reflection method
public class CommonReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonReflectionUtil.class);

    public static CommonReflectionUtil getInstance() {
        return CommonReflectionUtilHolder.instance;
    }

    /**
     * <p>
     * Reflection objects get generics
     * </p>
     *
     * @param clazz Objects
     * @param index Location of the generics
     * @return Class
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                    clazz.getSimpleName(), params.length));
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                    clazz.getSimpleName()));
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    private static class CommonReflectionUtilHolder {
        private static CommonReflectionUtil instance = new CommonReflectionUtil();
    }
}
