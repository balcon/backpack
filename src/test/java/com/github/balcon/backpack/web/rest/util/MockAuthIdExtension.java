package com.github.balcon.backpack.web.rest.util;

import com.github.balcon.backpack.config.SecurityConfig;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;

public class MockAuthIdExtension implements InvocationInterceptor {
    @Override
    public void interceptTestMethod(InvocationInterceptor.Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        Method method = invocationContext.getExecutable();
        if (method.isAnnotationPresent(MockAuthId.class)) {
            MockAuthId annotation = method.getAnnotation(MockAuthId.class);
            SecurityConfig.authUserId = annotation.id();
        }
        InvocationInterceptor.super.interceptTestMethod(invocation, invocationContext, extensionContext);
    }
}
