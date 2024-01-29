package org.example.property.resource;

import org.apache.commons.collections4.set.MapBackedSet;
import org.example.property.PropertyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置中心配置源
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:42 AM
 */
public class ConfigCenterPropertyResource implements PropertyResource {

    private static final Logger log = LoggerFactory.getLogger(ConfigCenterPropertyResource.class);

    private final int order;
    private final Set<PropertyListener> listeners = MapBackedSet.mapBackedSet(new ConcurrentHashMap<>(), true);

    public ConfigCenterPropertyResource(int order) {
        this.order = order;
    }

    @Override
    public String getName() {
        return "vivoConfigPropertyResource";
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public String getProperty(String key) {
        // 从远程获取配置
        return null;
    }

    @Override
    public synchronized boolean addListener(PropertyListener propertyListener) {
        if (listeners.contains(propertyListener)) {
            return false;
        }
        if (listeners.isEmpty()) {
            // 远程添加监听器
        }

        return listeners.add(propertyListener);
    }

    @Override
    public synchronized boolean removeListener(PropertyListener propertyListener) {
        boolean remove = listeners.remove(propertyListener);
        if (remove && listeners.isEmpty()) {
            // 远程删除监听器
        }

        return remove;
    }


}
