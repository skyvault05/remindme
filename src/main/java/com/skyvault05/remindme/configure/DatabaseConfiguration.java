//package com.skyvault05.remindme.configure;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DatabaseConfiguration {
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("remindme");
//        dataSource.setPassword("remindmepass");
//        dataSource.setUrl(
//                "jdbc:mysql://localhost:3306/remindme");
//
//        return dataSource;
//    }
//}
