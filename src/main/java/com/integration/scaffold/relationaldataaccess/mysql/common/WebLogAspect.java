package com.integration.scaffold.relationaldataaccess.mysql.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class WebLogAspect {

    @Autowired
    ObjectMapper objectMapper;
    Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Around("execution(* com.integration.scaffold.relationaldataaccess.mysql.controller.*.*(..))")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // 记录调用的方法和参数
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Long startTime = System.currentTimeMillis();
        logger.info("请求ip是：{}",request.getRemoteAddr());
        logger.info("请求url是: {}",request.getRequestURL().toString());
        logger.info("请求方法是: {}",request.getMethod());
        logger.info("处理Method:{},请求方法：{}",pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName());
        logger.info("请求参数是：{}",pjp.getArgs());
        Object retVal = pjp.proceed();
        // stop stopwatch
        logger.info("返回结果是：{}",objectMapper.writeValueAsString(retVal));
        Long endTime = System.currentTimeMillis();
        logger.info("执行方法耗时:{}秒",(endTime-startTime)/1000.0);
        return retVal;
    }
}
