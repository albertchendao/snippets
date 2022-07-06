package org.example.pattern.abstractdocument;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.example.common.json.JsonHelper;
import org.example.pattern.abstractdocument.domain.Car;
import org.example.pattern.abstractdocument.domain.Part;
import org.example.pattern.abstractdocument.domain.PropertyEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class App {

    private static Map<String, Object> createMap(
            String k1, Object v1,
            String k2, Object v2,
            String k3, Object v3
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put(k1, v1);
        props.put(k2, v2);
        props.put(k3, v3);
        return props;
    }

    public static void main(String[] args) {
        Map<String, Object> wheelProperties = createMap(
                PropertyEnum.TYPE.toString(), "wheel",
                PropertyEnum.MODEL.toString(), "15C",
                PropertyEnum.PRICE.toString(), 100L
        );
        Map<String, Object> doorProperties = createMap(
                PropertyEnum.TYPE.toString(), "door",
                PropertyEnum.MODEL.toString(), "Lambo",
                PropertyEnum.PRICE.toString(), 300L
        );
        Map<String, Object> carProperties = createMap(
                PropertyEnum.MODEL.toString(), "300SL",
                PropertyEnum.PRICE.toString(), 10000L,
                PropertyEnum.PARTS.toString(), Lists.newArrayList(wheelProperties, doorProperties)
        );
        Car car = new Car(carProperties);
        log.info("Here is our car:");
        log.info("-> model: {}", car.getModel().orElse(null));
        log.info("-> price: {}", car.getPrice().orElse(null));
        log.info("-> parts: ");
        car.getParts().forEach(part ->
                log.info("\t{}/{}/{}",
                        part.getType().orElse(null),
                        part.getModel().orElse(null),
                        part.getPrice().orElse(null)
                )
        );
    }
}
