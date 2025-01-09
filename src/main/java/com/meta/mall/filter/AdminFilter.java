package com.meta.mall.filter;

import com.meta.mall.common.Constant;
import com.meta.mall.model.pojo.User;
import com.meta.mall.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AdminFilter implements Filter {
    UserService userService;

    public AdminFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("""
                    {
                        "status": 10007,
                        "msg": "NEED_LOGIN",
                        "data": null
                    }""");


            return;

        }
        if (userService.isAdmin(currentUser)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("""
                    {
                        "status": 10008,
                        "msg": "NEED_ADMIN",
                        "data": null
                    }""");
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
