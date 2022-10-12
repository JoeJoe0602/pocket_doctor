package com.jolin.common.cache;

import com.jolin.common.dto.BaseDTO;
import com.jolin.common.dto.BaseJoinDTO;
import com.jolin.common.service.ICommonService;
import com.jolin.common.util.CommonCacheUtil;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * Framework-defined cache Key generator
 */
public class BaseCacheKeyGenerator implements KeyGenerator {
    /**
     * Generate a cache custom key
     * target: The name of the method that calls the method
     * method: specifies the class name of the method
     * params: Parameter value of the passed value
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {

        //Gets the name of the DTO class
        String dtoClassName = ((ICommonService) target).getDTOClass().getSimpleName();
        String id = "";
        //If the parameter is DTO, obtain the DTO id as the key
        if (params[0] instanceof BaseDTO) {
            id = ((BaseDTO) params[0]).getId();
        }
        if (params[0] instanceof BaseJoinDTO) {
            id = ((BaseJoinDTO) params[0]).getId();
        } else if (params[0] instanceof String) {
            id = (String) params[0];
        }
        return CommonCacheUtil.getCacheKey(dtoClassName, id);
    }
}
