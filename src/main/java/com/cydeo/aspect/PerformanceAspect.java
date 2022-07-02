package com.cydeo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class PerformanceAspect {

    Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    @Pointcut("@annotation(com.cydeo.annotation.Loggable)")
    private void anyExecutionTimeOperation() {
    }

    @Around("anyExecutionTimeOperation()")
    public Object anyExecutionTimeOperationAdvice(ProceedingJoinPoint proceedingJoinPoint) {
            Long beforeTime = System.currentTimeMillis();
            Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            t.printStackTrace();
        }

        Long afterTime = System.currentTimeMillis();

        logger.info("Time taken to execute: {} ms - Method: {} - Parameters: {}", afterTime-beforeTime, proceedingJoinPoint.getSignature().toString(), proceedingJoinPoint.getArgs());
        return result;

    }


}
