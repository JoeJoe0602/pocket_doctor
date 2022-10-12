package com.jolin.common.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CommonBeanUtil {

    public static CommonBeanUtil getInstance() {
        return CommonBeanUtilHolder.instance;
    }

    public static void saveCopy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static void updateCopy(Object source, Object target) {
        beanCopyWithoutNull(source, target);
    }

    public static String generateBeanId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * The non-null attribute in source is copied to target
     */
    public static <T> void beanCopyWithoutNull(T source, T target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    /**
     * Non-null attributes in source are copied to target, but the specified attributes are ignored, meaning that some attributes are not modifiable (personal business needs).
     */
    public static <T> void beanCopyWithIngore(T source, T target, String... ignoreProperties) {
        String[] pns = getNullAndIgnorePropertyNames(source, ignoreProperties);
        BeanUtils.copyProperties(source, target, pns);
    }

    public static String[] getNullAndIgnorePropertyNames(Object source, String... ignoreProperties) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        emptyNames.addAll(Arrays.asList(ignoreProperties));
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static String[] getNullPropertyNames(Object source) {
        Set<String> emptyNames = getNullPropertyNameSet(source);
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static Set<String> getNullPropertyNameSet(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }

        }
        return emptyNames;
    }



    private static class CommonBeanUtilHolder {
        private static CommonBeanUtil instance = new CommonBeanUtil();
    }
}
