package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Handler implements InvocationHandler {

    private Object target;

    public Handler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Handler");
        System.out.println(proxy.getClass());
        return method.invoke(target, args);
    }
}
