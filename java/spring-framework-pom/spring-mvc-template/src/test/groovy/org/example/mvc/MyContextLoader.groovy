package org.example.mvc

import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.mock.web.MockServletContext
import org.springframework.test.context.ContextLoader
import org.springframework.web.context.ConfigurableWebApplicationContext
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

import javax.servlet.ServletContextEvent

class MyContextLoader implements ContextLoader {

    @Override
    String[] processLocations(Class<?> aClass, String... strings) {
        return new String[0]
    }

    @Override
    ApplicationContext loadContext(String... strings) throws Exception {
        MockServletContext servletContext = new MockServletContext();
        servletContext.addInitParameter("contextConfigLocation", "classpath:spring-context.xml")
        def listener = new ContextLoaderListener()
        listener.contextInitialized(new ServletContextEvent(servletContext))
        def context = (ConfigurableApplicationContext) servletContext.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory()
        return new MyApplicationContext(context, factory)
    }
}
