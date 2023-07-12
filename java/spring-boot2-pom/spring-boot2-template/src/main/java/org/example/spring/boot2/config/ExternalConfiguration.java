package org.example.spring.boot2.config;

import org.example.external.TestImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 外部模块配置
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 3:23 PM
 */
@Configuration
//@Import(TestImportSelector.class)
@Import(TestImportBeanDefinitionRegistrar.class)
public class ExternalConfiguration {
}
