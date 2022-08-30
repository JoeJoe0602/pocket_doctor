package com.jolin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = {"com.jolin.*.mapper"})
public class SysMybatisPlusMapperConfig {

}