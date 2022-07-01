package com.cydeo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@Aspect
public class LoggingAspect {

    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount details = (SimpleKeycloakAccount) authentication.getDetails();
        return details.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || execution(* com.cydeo.controller.TaskController.*(..))")
    private void anyControllerOperation() {}

    @Before("anyControllerOperation()")
    public void anyBeforeControllerOperationAdvice(JoinPoint joinPoint) {
        String username = getUsername();
        logger.info("Before () \n-> user: {} \n-> Method: {} \n-> Parameters: {}", username, joinPoint.getSignature().toString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "anyControllerOperation()", returning = "results")
    public void anyAfterControllerOperation(JoinPoint joinPoint, Object results) {
        String username = getUsername();
        logger.info("After () \n-> user: {} \n-> Method: {} \n-> Parameters: {} \n-> results: {}", username, joinPoint.getSignature().toString(), joinPoint.getArgs(), results.toString());
    }
}
