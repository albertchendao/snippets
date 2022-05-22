package org.example.demo.condition;

import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMyProperties("say")
public class ConditionConfig {

    public GoodService sayService() {
        return new GoodService();
    }

}
