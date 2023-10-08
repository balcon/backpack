package com.github.balcon.backpack.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Aspect
@Component
@Slf4j
public class LoggingAdvice {
    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isRestController() {
    }

    @Pointcut("within(com.github.balcon.backpack.exception.ExceptionAdvice)")
    public void isExceptionAdvice() {
    }

    @Before("isRestController()")
    public void logBefore(JoinPoint joinPoint) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String method = joinPoint.getSignature().toShortString();
        log.info("User [{}] tried to call [{}]", userName, method);
    }

    @AfterReturning("isRestController()")
    public void logAfterReturning(JoinPoint joinPoint) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String method = joinPoint.getSignature().toShortString();
        log.info("User [{}] called [{}] successfully", userName, method);
    }

    @AfterReturning("isExceptionAdvice() && args(exception) && @annotation(responseStatus)")
    public void logAfterExceptionHandle(Exception exception, ResponseStatus responseStatus) {
        Level level = responseStatus.value().equals(HttpStatus.INTERNAL_SERVER_ERROR)
                ? Level.ERROR
                : Level.INFO;
        log.atLevel(level).log("Exception [{} : {}]", exception.getClass().getSimpleName(), exception.getMessage());
    }
}
