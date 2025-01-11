package com.meta.mall;

import com.meta.mall.filter.AdminFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminFilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(AdminFilter adminFilter) {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(adminFilter);
        filterRegistrationBean.addUrlPatterns("/category/admin/*");
        filterRegistrationBean.addUrlPatterns("/product/admin/*");


        return filterRegistrationBean;
    }

}
