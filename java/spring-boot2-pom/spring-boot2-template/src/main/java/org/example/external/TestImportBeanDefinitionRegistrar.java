package org.example.external;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 测试 ImportBeanDefinitionRegistrar
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 3:22 PM
 */
public class TestImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(ExternalSayService.class);
        registry.registerBeanDefinition("externalSayService", rootBeanDefinition);
    }
}
