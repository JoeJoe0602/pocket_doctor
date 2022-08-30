package com.jolin.conf;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 该类用于动态识别数据源,解决sql差异化的问题
 */

@Configuration
public class DatabaseIdProviderConfig {

    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties p = new Properties();
        p.setProperty("Oracle", "oracle");
        p.setProperty("KingbaseES", "kingbasees");
        p.setProperty("MySQL", "mysql");
//        p.setProperty("PostgreSQL", "postgresql");
//        p.setProperty("DB2", "db2");
//        p.setProperty("SQL Server", "sqlserver");
        databaseIdProvider.setProperties(p);
        return databaseIdProvider;
    }

}