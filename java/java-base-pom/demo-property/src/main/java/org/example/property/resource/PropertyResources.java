package org.example.property.resource;

import org.example.property.PropertyValidator;
import org.example.property.resource.PropertyResource;

import java.util.Collection;

/**
 * 多个配置源
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:32 AM
 */
public interface PropertyResources extends PropertyResource {

    /**
     * 获取key对应的配置
     *
     * @param key key
     * @return 配置
     * @see #getProperty(String, PropertyValidator)
     */
    @Override
    default String getProperty(String key) {
        return getProperty(key, PropertyValidator.alwaysTrue());
    }

    /**
     * 获取key对应的配置
     * 获取的逻辑:
     * 按照配置源的{@link PropertyResource#getOrder()} 依次查询配置源的配置;
     * 当配置不为{@code null}, 且{@link PropertyValidator#validate(String)}为{@code true}, 则返回该配置;
     * 所有配置源都查询完后配置依然不存在时将返回{@code null}
     *
     * @param key       配置的key
     * @param validator 配置校验器
     * @return 配置
     */
    String getProperty(String key, PropertyValidator validator);

    /**
     * 添加配置源
     *
     * @param propertyResource 添加配置源
     * @return true: 添加的配置变更监听器之前不存在
     */
    boolean addPropertyResource(PropertyResource propertyResource);

    /**
     * 批量添加配置源
     *
     * @param propertyResource 配置源
     */
    default void addPropertyResources(PropertyResource... propertyResource) {
        for (PropertyResource resource : propertyResource) {
            addPropertyResource(resource);
        }
    }

    /**
     * 获取所有配置源, 配置源间按照{@link PropertyResource#getOrder()} 从小到大排序
     *
     * @return 配置源集合
     */
    Collection<PropertyResource> getPropertyResources();

}
