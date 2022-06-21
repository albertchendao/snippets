package org.example.spring.boot2.dubbo.configuration;

import org.apache.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Sentinel 限流配置
 *
 * 参考: https://xie.infoq.cn/article/10fe698dffd35f65bade2dc64
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/21 8:19 PM
 */
@Configuration
public class SentinelConfiguration {

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setFilter("-sentinel.dubbo.filter");
        return consumerConfig;
    }
}