package org.example.property.resource;

import org.example.property.PropertyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 匹配特定后缀的配置源
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:41 AM
 */
public class SuffixPropertyResource implements PropertyResource {

    private final String name;
    private final int order;
    private final PropertyResource propertyResource;
    private final String suffix;
    private final Map<PropertyListener, SuffixPropertyListener> listenerMap = new HashMap<>(8);


    public SuffixPropertyResource(String name, int order, String suffix, PropertyResource propertyResource) {
        this.name = name;
        this.order = order;
        this.suffix = suffix;
        this.propertyResource = propertyResource;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public String getProperty(String key) {
        return propertyResource.getProperty(key + suffix);
    }

    @Override
    public synchronized boolean addListener(PropertyListener propertyListener) {
        if (listenerMap.containsKey(propertyListener)) {
            return false;
        }
        SuffixPropertyListener listener = new SuffixPropertyListener(propertyListener, suffix);
        if (propertyResource.addListener(listener)) {
            listenerMap.put(propertyListener, listener);
            return true;
        }
        return false;
    }

    @Override
    public synchronized boolean removeListener(PropertyListener propertyListener) {
        SuffixPropertyListener suffixPropertyListener = listenerMap.get(propertyListener);
        if (suffixPropertyListener == null) {
            return false;
        }
        return propertyResource.removeListener(suffixPropertyListener);
    }


    class SuffixPropertyListener implements PropertyListener {

        private final PropertyListener propertyListener;
        private final String suffix;

        public SuffixPropertyListener(PropertyListener propertyListener, String suffix) {
            this.propertyListener = propertyListener;
            this.suffix = suffix;
        }

        @Override
        public void onChanged(String key) {
            if (key.endsWith(suffix)) {
                propertyListener.onChanged(key.substring(0, key.indexOf(suffix)));
            }
        }
    }
}
