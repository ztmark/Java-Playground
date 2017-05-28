package com.github.ztmark;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Author: Mark
 * Date  : 2017/5/28
 */
@Aspect
@Component
public class LogParamConfig {

    private static final Logger logger = LoggerFactory.getLogger(LogParamConfig.class);

    @Before("@annotation(Mine)")
    public void pointcut(JoinPoint joinPoint) {
        logger.info("method {} called and param is {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

}
