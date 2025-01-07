package com.meta.mall.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {
    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.meta.mall.controller.*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();


        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        log.info("URL: {}", request.getRequestURL().toString());

        log.info("HTTP Method: {}", request.getMethod());

        log.info("IP: {}", request.getRemoteAddr());

        log.info("class+method{}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());

        log.info("ARGS: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturn(Object res) throws JsonProcessingException {
        log.info("Response:{}", new ObjectMapper().writeValueAsString(res));
    }

}
