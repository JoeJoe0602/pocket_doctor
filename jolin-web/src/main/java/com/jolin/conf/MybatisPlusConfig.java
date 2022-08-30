package com.jolin.conf;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    /**
     * 1.分页插件
     * 2.乐观锁
     * 3.多租户
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁
//        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //多租户
//        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor());
        return interceptor;
    }
    /**
     * 自定义 SqlInjector
     * 里面包含自定义的全局方法
     */
//    @Bean
//    public MyLogicSqlInjector myLogicSqlInjector() {
//        return new MyLogicSqlInjector();
//    }
}
