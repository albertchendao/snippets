package org.example.property;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 属性配置
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:25 AM
 */
public interface SdkProperty {

    /**
     * 创建不可变配置
     * @param key key
     * @param value value
     * @return 不可变配置
     */
    static SdkProperty unmodifiable(String key, String value) {
        return new UnmodifiableSdkProperty(key, value);
    }

    /**
     * 配置的key
     *
     * @return 配置的key
     */
    String getKey();

    /**
     * 配置的value
     *
     * @return 配置的value
     */
    String getValue();

    /**
     * 获取配置并解码, 当值为null时不会进行解码, 而是直接返回null
     * @param decoder 解码器
     * @param <T> T
     * @return 解码后的配置
     */
    default <T> T getValue(Function<String, T> decoder) {
        String value = getValue();
        if (value == null) {
            return null;
        }
        return decoder.apply(value);
    }

    /**
     * 配置的校验器
     *
     * @return 配置的校验器
     */
    PropertyValidator validator();


    /**
     * 配置是否需要支持变更后的动态刷新
     * 提醒作用, 具体的实现交由使用该配置的类, 当为{@code true}时, 使用该配置的类需要保证支持该配置的动态刷新.
     *
     * @return 配置是否需要支持动态刷新
     * @see #addChangeListener(Consumer)
     */
    boolean dynamicRefresh();


    /**
     * 添加针对该配置变更的监听器
     *
     * @param listener listener
     * @return true: 添加的listener之前不存在
     */
    boolean addChangeListener(Consumer<SdkProperty> listener);

    /**
     * 移除配置变更的监听器
     *
     * @param listener listener
     * @return 删除的listener是否存在
     */
    boolean removeChangeListener(Consumer<SdkProperty> listener);

    /**
     * 添加针对该配置值变更的监听器
     * @param listener 监听器
     * @param <T> T
     * @return 添加的监听器
     */
    default <T> Consumer<SdkProperty> addChangeValueListener(Consumer<String> listener) {
        Consumer<SdkProperty> sdkPropertyListener = sdkProperty -> {
            listener.accept(sdkProperty.getValue());
        };
        addChangeListener(sdkPropertyListener);
        return sdkPropertyListener;
    }

    /**
     * 添加针对该配置值变更的监听器
     * @param decoder 配置的解码器
     * @param listener 监听器
     * @param <T> T
     * @return 添加的监听器
     */
    default <T> Consumer<SdkProperty> addChangeValueListener(Function<String, T> decoder, Consumer<T> listener) {
        Consumer<SdkProperty> sdkPropertyListener = sdkProperty -> {
            listener.accept(decoder.apply(sdkProperty.getValue()));
        };
        addChangeListener(sdkPropertyListener);
        return sdkPropertyListener;
    }
}
