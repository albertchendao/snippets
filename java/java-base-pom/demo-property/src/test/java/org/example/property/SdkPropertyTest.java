package org.example.property;

import org.junit.Assert;
import org.junit.Test;

/**
 * 属性配置测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/1 11:30 AM
 */
public class SdkPropertyTest {

    public static final SdkProperty SDK_VERSION = SdkProperty.unmodifiable("sdk.version", "1");

    @Test
    public void testUnmodifiable() {
        String expected = "1";
        String result = SDK_VERSION.getValue();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testDefault() {
        String expected = "1";
        String result = SDK_VERSION.getValue();
        Assert.assertEquals(expected, result);
    }
}
