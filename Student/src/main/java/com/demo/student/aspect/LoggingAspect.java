package com.demo.student.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("execution(* com.demo.student.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        logger.info("Executing method: {}", signature.getName());

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("Exception in method {}: {}", signature.getName(), e.getMessage(), e);
            throw e;
        }

        long duration = System.currentTimeMillis() - start;
        logger.info("Method {} executed in {} ms", signature.getName(), duration);
        return result;
    }

    @AfterReturning(pointcut = "execution(* com.demo.student.*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        logger.info("Method {} returned: {}", joinPoint.getSignature().getName(), result);
    }
}
