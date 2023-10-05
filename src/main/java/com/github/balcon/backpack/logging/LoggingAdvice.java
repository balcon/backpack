package com.github.balcon.backpack.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class LoggingAdvice {
    @Pointcut("@within(com.github.balcon.backpack.logging.Logged) &&" +
            "@within(org.springframework.stereotype.Service)")
    public void isLoggedService() {
    }

    @Pointcut("isLoggedService() && args(..)")
    public void anyMethods() {

    }

    @Before("anyMethods()")
    public void logBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.toShortString();
        log.info("Invoked [{}] method", methodName);
        if (log.isDebugEnabled()) {
            String parameters = String.join(", ", signature.getParameterNames());
            log.debug("Parameter names [{}]", parameters);
            String values = Arrays.stream(joinPoint.getArgs())
                    .map(Object::toString)
                    .collect(Collectors.joining(","));
            log.debug("Parameter values [{}]", values);
        }
    }
}
