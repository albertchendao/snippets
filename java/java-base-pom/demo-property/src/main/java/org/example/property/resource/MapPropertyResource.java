package org.example.property.resource;

import org.example.property.PropertyListener;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 内存配置源
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:39 AM
 */
public class MapPropertyResource implements PropertyResource {

    private final String name;
    private final ConcurrentMap<String, Object> props;
    private final int order;

    public MapPropertyResource(String name, int order) {
        this(name, order, Collections.emptyMap());
    }

    public MapPropertyResource(String name, int order, Map<String, String> props) {
        this.name = name;
        this.order = order;
        this.props = new ConcurrentHashMap<>(props);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    public String set(String key, Object value) {
        Object result = props.put(key, value);
        return result != null ? result.toString() : null;
    }

    public void setAll(Map<String, String> map) {
        props.putAll(map);
    }

    public void clear() {
        props.clear();
    }


    @Override
    public String getProperty(String key) {
        Object result = props.get(key);
        return result != null ? result.toString() : null;
    }

    @Override
    public boolean addListener(PropertyListener propertyListener) {
        return false;
    }

    @Override
    public boolean removeListener(PropertyListener propertyListener) {
        return false;
    }
}
