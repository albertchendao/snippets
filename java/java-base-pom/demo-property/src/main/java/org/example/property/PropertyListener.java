package org.example.property;

/**
 * 配置变更监听器
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:31 AM
 */
public interface PropertyListener {
    /**
     * 配置变更
     * @param key 变更的key
     */
    void onChanged(String key);
}
