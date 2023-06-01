package org.example.property;

import org.example.property.resource.*;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 多配置源测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:37 AM
 */
public class PropertyResourcesTest {

    public static final SdkProperty IP = SdkProperty.unmodifiable("ip", getLocalIp());

    private static String getLocalIp() {
        String hostIp;
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostIp = "UNKNOWN";
        }
        return hostIp;
    }

    static class SdkPropertyRegister {
        private String key;
        private Object localDefaultValue;
        private PropertyValidator validator;
        private boolean dynamicRefresh = false;

        SdkPropertyRegister(String key) {
            this.key = key;
        }

        SdkPropertyRegister key(String key) {
            this.key = key;
            return this;
        }

        SdkPropertyRegister dynamicRefresh() {
            this.dynamicRefresh = true;
            return this;
        }


        SdkPropertyRegister localDefaultValue(Object localDefaultValue) {
            this.localDefaultValue = localDefaultValue;
            return this;
        }

        SdkPropertyRegister validator(PropertyValidator validator) {
            this.validator = validator;
            return this;
        }

        @SuppressWarnings("unchecked")
        protected <T extends SdkProperty> T createSdkProperty(String key,
                                                              boolean dynamicRefresh,
                                                              PropertyValidator validator,
                                                              PropertyResources propertyResources) {
            return (T) new DefaultSdkProperty(key, dynamicRefresh, validator, propertyResources);
        }

        <T extends SdkProperty> T create() {
            if (validator == null) {
                validator = PropertyValidator.alwaysTrue();
            }
            T sdkProperty = createSdkProperty(key, dynamicRefresh, validator, PROPERTY_RESOURCES);
            if (localDefaultValue != null) {
                localDefaultResource.set(sdkProperty.getKey(), localDefaultValue);
            }
            return sdkProperty;
        }
    }

    // 入参配置
    // 配置中心配置
    private static MapPropertyResource paramResource = new MapPropertyResource("paramPropertyResource", 0);
    private static PropertyResource configCenterResource = new ConfigCenterPropertyResource(1000);
    private static PropertyResource ipSuffixResource = new SuffixPropertyResource("ipSuffixPropertyResource", 500, ":" + IP.getValue(), configCenterResource);
    private static MapPropertyResource remoteDefaultResource = new MapPropertyResource("remoteDefaultPropertyResource", 2000);
    private static MapPropertyResource localDefaultResource = new MapPropertyResource("localDefaultPropertyResource", 3000);

    // 配置优先级: 入参配置 > ip后缀配置中心配置 > 配置中心配置 > 远程默认配置 > 本地默认配置
    private static PropertyResources PROPERTY_RESOURCES = new DefaultPropertyResources();

    static {
        PROPERTY_RESOURCES.addPropertyResources(
                paramResource,
                ipSuffixResource,
                configCenterResource,
                remoteDefaultResource,
                localDefaultResource
        );
    }

    static SdkPropertyRegister register(String key) {
        return new SdkPropertyRegister(key);
    }


    @Test
    public void testGeProperty() {
        SdkProperty BUSINESS_CODE = register("businessCode").create();

        Assert.assertEquals("unmodifiable.value", PROPERTY_RESOURCES.getProperty("unmodifiable.key"));
    }
}
