package com.jolin.common.cache;

import com.jolin.common.dto.BaseDTO;
import com.jolin.common.service.ICommonService;
import com.jolin.common.util.CommonCacheUtil;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * 框架定义的缓存Key生成器
 */
public class BaseCacheKeyGenerator implements KeyGenerator {
    /**
     * 生成缓存自定义key
     * target：调用该方法的方法名
     * method：该方法的所在类名
     * params：传入值的参数值
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        //获取DTO类名
        String dtoClassName = ((ICommonService) target).getDTOClass().getSimpleName();
        Integer id = 0;
        //如果参数是DTO,则获取DTO的id值作为key
        if (params[0] instanceof BaseDTO) {
            id = ((BaseDTO) params[0]).getId();
        }
        return CommonCacheUtil.getCacheKey(dtoClassName, id);
    }
}
