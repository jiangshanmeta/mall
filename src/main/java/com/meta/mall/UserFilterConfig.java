package com.meta.mall;

import com.meta.mall.filter.UserFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserFilterConfig {
    @Bean(name = "userFilterConf")
    public FilterRegistrationBean<Filter> filterRegistrationBean(UserFilter userFilter) {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(userFilter);
        filterRegistrationBean.addUrlPatterns("/cart/*");

        return filterRegistrationBean;
    }
}
