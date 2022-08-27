package com.jolin.log.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.jolin.log.mapper"})
public class LogMybatisPlusMapperConfig {

}