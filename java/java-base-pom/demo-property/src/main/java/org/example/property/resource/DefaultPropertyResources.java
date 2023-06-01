package org.example.property.resource;

import lombok.extern.slf4j.Slf4j;
import org.example.property.PropertyListener;
import org.example.property.PropertyValidator;

import java.util.*;

/**
 * 默认多配置源实现
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:35 AM
 */
@Slf4j
public class DefaultPropertyResources implements PropertyResources {

    private volatile List<PropertyResource> propertyResources = Collections.emptyList();

    @Override
    public String getName() {
        return "defaultPropertyResources";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public String getProperty(String key, PropertyValidator validator) {

        for (PropertyResource propertyResource : propertyResources) {
            String property = propertyResource.getProperty(key);
            if (property == null) {
                continue;
            }
            if (validator.validate(property)) {
                return property;
            } else {
                log.error(
                        "property validate is fail. key={}, value={}, propertyResource={}, validator={}",
                        key, property, propertyResource.getName(), validator
                );
            }
        }

        return null;
    }

    @Override
    public synchronized boolean addPropertyResource(PropertyResource propertyResource) {
        if (propertyResource == this) {
            throw new IllegalStateException("propertyResource == this");
        }

        for (PropertyResource resource : propertyResources) {
            if (resource.getName().equals(propertyResource.getName())) {
                return false;
            }
        }

        List<PropertyResource> next = new ArrayList<>(propertyResources.size() + 1);
        next.addAll(propertyResources);
        next.add(propertyResource);
        next.sort(Comparator.comparing(PropertyResource::getOrder));
        this.propertyResources = next;
        return true;
    }

    @Override
    public Collection<PropertyResource> getPropertyResources() {
        return Collections.unmodifiableList(propertyResources);
    }

    @Override
    public synchronized boolean addListener(PropertyListener propertyListener) {
        boolean result = false;
        for (PropertyResource propertyResource : propertyResources) {
            result |= propertyResource.addListener(propertyListener);
        }
        return result;
    }

    @Override
    public synchronized boolean removeListener(PropertyListener propertyListener) {
        boolean result = false;
        for (PropertyResource propertyResource : propertyResources) {
            result |= propertyResource.removeListener(propertyListener);
        }
        return result;
    }
}