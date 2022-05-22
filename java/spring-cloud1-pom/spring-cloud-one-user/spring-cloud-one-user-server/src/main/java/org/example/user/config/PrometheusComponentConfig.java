package org.example.user.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrometheusComponentConfig {

//    @Bean
    public Counter requestTotalCountCollector(PrometheusMeterRegistry prometheusMeterRegistry) {
        return Counter.builder("http_requests_total").register(prometheusMeterRegistry);
    }
}
