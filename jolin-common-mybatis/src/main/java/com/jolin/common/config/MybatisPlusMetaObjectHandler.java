package com.jolin.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jolin.common.service.CommonSecurityService;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusMetaObjectHandler.class);

    // 新增时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("sort", 1, metaObject);
        this.setFieldValByName("createdAt", LocalDateTime.now(), metaObject);
        this.setFieldValByName("isDelete", 0, metaObject);
        this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
    }

    // 更新时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
    }
}
