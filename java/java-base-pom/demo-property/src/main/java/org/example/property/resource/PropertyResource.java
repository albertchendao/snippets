package org.example.property.resource;

import org.example.property.PropertyListener;

/**
 * 配置源
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:31 AM
 */
public interface PropertyResource {

    /**
     * 配置源的名称, 用于{@link PropertyResources}的唯一区分
     *
     * @return 配置源的名称
     */
    String getName();

    /**
     * 配置源的优先级，越小优先级越高. 会影响配置源在{@link PropertyResources}中的顺序
     *
     * @return 配置源的优先级
     */
    int getOrder();

    /**
     * 获取key对应的值
     *
     * @param key key
     * @return 获取key对应的值
     */
    String getProperty(String key);

    /**
     * 添加配置变更监听器
     *
     * @param propertyListener 配置变更监听器
     * @return true: 添加的配置变更监听器之前不存在
     */
    boolean addListener(PropertyListener propertyListener);

    /**
     * 移除配置变更监听器
     *
     * @param propertyListener 配置变更监听器
     * @return 移除配置变更监听器是否存在
     */
    boolean removeListener(PropertyListener propertyListener);
}
