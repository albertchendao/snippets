package org.example.demo.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
public class OnMyPropertiesCondition extends SpringBootCondition {

    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Object propertiesName = metadata.getAnnotationAttributes(ConditionalOnMyProperties.class.getName()).get("value");
        if (propertiesName != null) {
            String value = context.getEnvironment().getProperty(propertiesName.toString());
            if (value != null) {
                return new ConditionOutcome(true, "get properties");
            }
        }
        return new ConditionOutcome(false, "none get properties");
    }
}
