package com.jolin.common.util;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.jolin.common.annotation.BaseJoinId;
import com.jolin.common.domain.CommonDomain;
import com.jolin.common.dto.BaseJoinDTO;
import com.jolin.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


// The ID of the associated table is associated with the tool class
public class JoinIDUtil {
    private static final Logger logger = LoggerFactory.getLogger(JoinIDUtil.class);

    public static JoinIDUtil getInstance() {
        return JoinIDUtilHolder.instance;
    }

    /**
     * Call the getXXId method corresponding to the id property of the domain
     */
    public static <D extends CommonDomain> String callIdFieldGetMethod(D domain, Map<String, Method> methodMap, String idFieldName) {
        try {
            Method method = getIdFieldGetMethod(idFieldName, methodMap, domain.getClass());
            Object value = method.invoke(domain, new Object[]{});
            return (String) value;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Generate the associated table name
     */
    public static <DTO extends BaseJoinDTO, D extends CommonDomain> String generateJoinId(D domain, Map<String, Method> methodMap) {
        Class domainClass = domain.getClass();

        List<String> indexIds = Arrays.asList("", "", "", "", "");
        List<Field> fields = Arrays.asList(ReflectUtil.getFields(domainClass));
        for (Field field : fields) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                continue;
            }
            //Gets the @BaseJoinId annotation on the field
            BaseJoinId qw = field.getAnnotation(BaseJoinId.class);
            if (qw == null) {
                continue;
            }
            if (qw.index().ordinal() >= CommonStatic.MAX_JOIN_TABLE_NUM) {
                throw new CommonException("The index attribute of BaseJoinId is greater than or equal to {}. Associated tables cannot be associated with {} tables. You are advised to reset the Domain. Note also whether the"  +
                        "index attribute of BaseJoinId starts from 0 and accumulates in sequence", CommonStatic.MAX_JOIN_TABLE_NUM, CommonStatic.MAX_JOIN_TABLE_NUM);
            }
            String value = callIdFieldGetMethod(domain, methodMap, field.getName());
            indexIds.set(qw.index().ordinal(), value);
        }

        if (CollectionUtils.isEmpty(indexIds)) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        indexIds.forEach(id ->
                result.append(id)
        );

        return result.toString();
    }


    /**
     * Gets the getXXId method corresponding to the id attribute
     *
     * @param idFieldName idFieldName
     * @return
     */
    public static <D extends CommonDomain> Method getIdFieldGetMethod(String idFieldName, Map<String, Method> methodMap, Class<D> domainClass) {
        Method method = methodMap.get(idFieldName);
        if (method == null) {
            try {
                String firstLetter = idFieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + idFieldName.substring(1);
                method = domainClass.getMethod(getter, new Class[]{});
                methodMap.put(idFieldName, method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    /**
     * Get the names of the corresponding order from the BaseJoinId annotation
     *
     * @param index
     * @return
     */
    public static <D extends CommonDomain> String getIdFieldNameByIndex(BaseJoinId.Index index,
                                                                        List<String> idFieldNames, Class<D> domainClass) {
        String idFieldName = idFieldNames.get(index.ordinal());
        if (StrUtil.isBlank(idFieldName)) {
            List<Field> fields = Arrays.asList(ReflectUtil.getFields(domainClass));
            for (Field field : fields) {
                //Gets the @BaseJoinId annotation on the field
                BaseJoinId qw = field.getAnnotation(BaseJoinId.class);
                if (qw == null) {
                    continue;
                }
                if (qw.index() == index) {
                    idFieldNames.set(index.ordinal(), field.getName());
                    return field.getName();
                }
            }
        }

        //The corresponding index did not find the attribute name
        return idFieldName;
    }

    private static class JoinIDUtilHolder {
        private static JoinIDUtil instance = new JoinIDUtil();
    }
}
