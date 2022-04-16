package org.example.mvc

import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.context.ApplicationContext

class MyApplicationContext implements ApplicationContext, BeanDefinitionRegistry {

    @Delegate ApplicationContext applicationContext
    @Delegate BeanDefinitionRegistry beanDefinitionRegistry

    MyApplicationContext(ApplicationContext applicationContext,
                         BeanDefinitionRegistry beanDefinitionRegistry) {
        this.applicationContext = applicationContext
        this.beanDefinitionRegistry = beanDefinitionRegistry
    }
}
