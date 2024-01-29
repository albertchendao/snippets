package org.example.property;

import java.util.function.Consumer;

/**
 * 不可变属性配置
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:26 AM
 */
public class UnmodifiableSdkProperty implements SdkProperty {

    private final String key;
    private final String value;


    public UnmodifiableSdkProperty(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public PropertyValidator validator() {
        return PropertyValidator.alwaysTrue();
    }

    @Override
    public boolean dynamicRefresh() {
        return false;
    }

    @Override
    public boolean addChangeListener(Consumer<SdkProperty> listener) {
        return false;
    }

    @Override
    public boolean removeChangeListener(Consumer<SdkProperty> listener) {
        return false;
    }
}
