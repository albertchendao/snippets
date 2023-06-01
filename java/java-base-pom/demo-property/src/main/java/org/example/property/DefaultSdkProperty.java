package org.example.property;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.set.MapBackedSet;
import org.example.property.resource.PropertyResources;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 默认配置
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:56 AM
 */
@Slf4j
public class DefaultSdkProperty implements SdkProperty, PropertyListener {

    private final String key;
    private final PropertyValidator validator;
    private final PropertyResources propertyResources;
    private final boolean dynamicRefresh;
    private final Set<Consumer<SdkProperty>> listeners = MapBackedSet.mapBackedSet(new ConcurrentHashMap<>(), true);

    DefaultSdkProperty(String key, boolean dynamicRefresh, PropertyValidator validator, PropertyResources propertyResources) {
        this.key = key;
        this.dynamicRefresh = dynamicRefresh;
        this.validator = validator;
        this.propertyResources = propertyResources;
    }

    @Override
    public String getValue() {
        return propertyResources.getProperty(getKey(), validator());
    }

    @Override
    public PropertyValidator validator() {
        return this.validator;
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public boolean dynamicRefresh() {
        return dynamicRefresh;
    }

    @Override
    public synchronized boolean addChangeListener(Consumer<SdkProperty> listener) {
        if (listeners.isEmpty()) {
            propertyResources.addListener(this);
        }
        return listeners.add(listener);
    }

    @Override
    public synchronized boolean removeChangeListener(Consumer<SdkProperty> listener) {
        if (listeners.remove(listener)) {
            if (listeners.isEmpty()) {
                propertyResources.removeListener(this);
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultSdkProperty)) return false;
        DefaultSdkProperty that = (DefaultSdkProperty) o;
        return Objects.equals(getKey(), that.getKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }

    @Override
    public String toString() {
        return "DefaultSdkProperty{" +
                "key='" + this.getKey() + '\'' +
                "value='" + this.getValue() + '\'' +
                ", dynamicRefresh=" + this.dynamicRefresh() +
                '}';
    }

    @Override
    public void onChanged(String key) {
        if (this.getKey().equals(key)) {
            for (Consumer<SdkProperty> listener : listeners) {
                try {
                    listener.accept(this);
                } catch (Exception e) {
                    log.error("listener error. sdkProperty={}", this, e);
                }
            }
        }
    }
}
