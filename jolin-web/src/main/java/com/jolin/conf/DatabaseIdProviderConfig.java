package com.jolin.conf;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * This class is used to dynamically identify data sources and solve the problem of sql differentiation
 */

@Configuration
public class DatabaseIdProviderConfig {

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("MySQL", "mysql");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }

}