package com.meta.mall;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;


@Configuration
@EnableJdbcRepositories(basePackages = "com.meta.mall")
@ComponentScan(basePackages = "com.meta.mall")
public class MallConfig {


}
