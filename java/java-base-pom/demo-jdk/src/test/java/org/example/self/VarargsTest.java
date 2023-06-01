package org.example.self;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * 可变参数测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/3/23 4:00 PM
 */
public class VarargsTest {

    public static String format() {
        return "none";
    }


    public static String format(String... a) {
        return Arrays.toString(a);
    }

//    /**
//     * 会提示 方法调用不明确
//     */
//    public static String format(String h, String... a) {
//        return h + " - " + Arrays.toString(a);
//    }

    public static String formatObj(String h1) {
        return h1;
    }

    public static String formatObj(String h1, Object... a) {
        return h1 + " - " + Arrays.deepToString(a);
    }

    public static String formatObj(String h1, String h2, Object... a) {
        return h1 + " - " + h2 + " - " + Arrays.deepToString(a);
    }


    @Test
    public void testFormat() {
        Assert.assertEquals("none", format());
        Assert.assertEquals("[1]", format("1"));
        Assert.assertEquals("[1, 2]", format("1", "2"));
    }

    @Test
    public void testFormatObj() {
        Assert.assertEquals("begin", formatObj("begin"));
        Assert.assertEquals("begin - 1 - []", formatObj("begin", "1"));
        Assert.assertEquals("begin - 1 - [2]", formatObj("begin", "1", "2"));
        // 不会命中 formatObj(String h1, String h2, Object... a)
        Assert.assertEquals("begin - 1 - [2, 3]", formatObj("begin", "1", "2", "3"));
        // 不会命中 formatObj(String h1, String h2, Object... a)
        Assert.assertEquals("begin - 1 - [2, [3]]", formatObj("begin", "1", "2", new Object[]{"3"}));
    }
}
