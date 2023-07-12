package org.example.common.helper.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vivo.appstore.common.util.JsonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 动态 json 对象测试
 *
 * @author Albert
 * @version 1.0
 * @since 2023/6/29 10:28 AM
 */
public class DynamicJsonObjectTest {

    interface IInfo {
        String getImg();

        void setImg(String img);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    static class Info extends DynamicJsonObject {
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    static class InfoV0 extends DynamicJsonObject implements IInfo {

        /**
         * 必须添加 @JsonIgnore @JSONField(serialize = false), 不然 json 序列化会出现重复字段
         */
        @JsonIgnore
        @JSONField(serialize = false)
        @Override
        public String getImg() {
            return (String) get(FIELD_IMG);
        }

        @Override
        public void setImg(String img) {
            this.set(FIELD_IMG, img);
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @FieldNameConstants(asEnum = true)
    static class InfoV1 extends DynamicJsonObject implements IInfo {
        private String img;

        // 列出已声明的字段用于屏蔽直接 set
        private static Set<String> FIELD_NAMES = Collections.unmodifiableSet(Arrays.stream(Fields.values())
                .map(Enum::name)
                .collect(Collectors.toSet()));

        @Override
        protected Set<String> fieldNames() {
            return FIELD_NAMES;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @FieldNameConstants(asEnum = true)
    static class InfoV2 extends DynamicJsonObject implements IInfo {
        private String img;
        // 新增的字段
        private String name;

        // 列出已声明的字段用于屏蔽直接 set
        private static Set<String> FIELD_NAMES = Collections.unmodifiableSet(Arrays.stream(Fields.values())
                .map(Enum::name)
                .collect(Collectors.toSet()));

        @Override
        protected Set<String> fieldNames() {
            return FIELD_NAMES;
        }
    }

    static String FIELD_IMG = "img";
    static String FIELD_NAME = "name";
    static String FIELD_EXTRA = "extra";

    static Info INFO = null;
    static InfoV0 V0 = null;
    static InfoV1 V1 = null;
    static InfoV2 V2 = null;

    static {
        INFO = new Info();
        INFO.set(FIELD_IMG, "http://1.jpg");
        INFO.set(FIELD_EXTRA, "额外参数");

        V0 = new InfoV0();
        V0.set(FIELD_IMG, "http://1.jpg");
        V0.set(FIELD_EXTRA, "额外参数");

        V1 = new InfoV1();
        V1.setImg("http://1.jpg");
        V1.set(FIELD_EXTRA, "额外参数");

        V2 = new InfoV2();
        V2.setImg("http://1.jpg");
        V2.set(FIELD_EXTRA, "额外参数");
        V2.setName("v2name");
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String INFO_JSON_STR = "{\"img\":\"http://1.jpg\",\"extra\":\"额外参数\"}";
    private static final String V0_JSON_STR = "{\"img\":\"http://1.jpg\",\"extra\":\"额外参数\"}";
    private static final String V1_JSON_STR = "{\"img\":\"http://1.jpg\",\"extra\":\"额外参数\"}";
    private static final String V2_JSON_STR = "{\"img\":\"http://1.jpg\",\"name\":\"v2name\",\"extra\":\"额外参数\"}";

    /**
     * 对象 与 json 互转, 全部填充到 properties
     */
    @Test
    public void testInfo() throws Exception {
        {
            // fastjson 序列化
            Assert.assertEquals(INFO_JSON_STR, JsonUtil.toString(INFO));
            // fastjson 反序列化
            Info result = JsonUtil.parse(V0_JSON_STR, Info.class);
            Assert.assertEquals(INFO.get(FIELD_IMG), result.get(FIELD_IMG));
            Assert.assertEquals(INFO.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
        }
        {
            // jackson 序列化
            Assert.assertEquals(INFO_JSON_STR, OBJECT_MAPPER.writeValueAsString(INFO));
            // jackson 反序列化
            Info result = OBJECT_MAPPER.readValue(V0_JSON_STR, Info.class);
            Assert.assertEquals(INFO.get(FIELD_IMG), result.get(FIELD_IMG));
            Assert.assertEquals(INFO.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
        }
    }

    /**
     * 对象 与 json 互转, v0 自身
     */
    @Test
    public void testV0() throws Exception {
        {
            // fastjson 序列化
            Assert.assertEquals(V0_JSON_STR, JsonUtil.toString(V0));
            // fastjson 反序列化
            InfoV0 result = JsonUtil.parse(V0_JSON_STR, InfoV0.class);
            Assert.assertEquals(V0.getImg(), result.getImg());
            Assert.assertEquals(V0.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
        }
        {
            // jackson 序列化
            Assert.assertEquals(V0_JSON_STR, OBJECT_MAPPER.writeValueAsString(V0));
            // jackson 反序列化
            InfoV0 result = OBJECT_MAPPER.readValue(V0_JSON_STR, InfoV0.class);
            Assert.assertEquals(V0.getImg(), result.getImg());
            Assert.assertEquals(V0.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
        }
    }

    /**
     * 对象 与 json 互转, v1 自身
     */
    @Test
    public void testV1() throws Exception {
        {
            // fastjson 序列化
            Assert.assertEquals(V1_JSON_STR, JsonUtil.toString(V1));
            // fastjson 反序列化
            InfoV1 result = JsonUtil.parse(V1_JSON_STR, InfoV1.class);
            Assert.assertEquals(V1.getImg(), result.getImg());
            Assert.assertEquals(V1.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
        }
        {
            // jackson 序列化
            Assert.assertEquals(V1_JSON_STR, OBJECT_MAPPER.writeValueAsString(V1));
            // jackson 反序列化
            InfoV1 result = OBJECT_MAPPER.readValue(V1_JSON_STR, InfoV1.class);
            Assert.assertEquals(V1.getImg(), result.getImg());
            Assert.assertEquals(V1.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
        }
    }

    /**
     * 不能将已声明字段设置到 properties
     */
    @Test(expected = IllegalArgumentException.class)
    public void testV1Override() throws Exception {
        InfoV1 tmp = new InfoV1();
        tmp.setImg("http://1.jpg");
        tmp.set(FIELD_IMG, "http://2.jpg");
    }

    /**
     * 对象 与 json 互转, v2 自身
     */
    @Test
    public void testV2() throws Exception {
        {
            // fastjson 序列化
            Assert.assertEquals(V2_JSON_STR, JsonUtil.toString(V2));
            // fastjson 反序列化
            InfoV2 result = JsonUtil.parse(V2_JSON_STR, InfoV2.class);
            Assert.assertEquals(V2.getImg(), result.getImg());
            Assert.assertEquals(V2.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
            Assert.assertEquals(V2.getName(), result.getName());
        }
        {
            // jackson 序列化
            Assert.assertEquals(V2_JSON_STR, OBJECT_MAPPER.writeValueAsString(V2));
            // jackson 反序列化
            InfoV2 result = OBJECT_MAPPER.readValue(V2_JSON_STR, InfoV2.class);
            Assert.assertEquals(V2.getImg(), result.getImg());
            Assert.assertEquals(V2.getName(), result.getName());
        }
    }

    /**
     * v1 转 v2, 字段不丢失, 无多余字段
     */
    @Test
    public void testV1toV2() throws Exception {
        {
            // fastjson 反序列化
            InfoV2 result = JsonUtil.parse(V1_JSON_STR, InfoV2.class);
            Assert.assertEquals(V1.getImg(), result.getImg());
            Assert.assertEquals(V1.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
            Assert.assertEquals(V1.get(FIELD_NAME), result.getName());
            Assert.assertEquals(V1.get(FIELD_NAME), result.get(FIELD_NAME));
        }
        {
            // jackson 反序列化
            InfoV2 result = OBJECT_MAPPER.readValue(V1_JSON_STR, InfoV2.class);
            Assert.assertEquals(V1.getImg(), result.getImg());
            Assert.assertEquals(V1.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
            Assert.assertEquals(V1.get(FIELD_NAME), result.getName());
            Assert.assertEquals(V1.get(FIELD_NAME), result.get(FIELD_NAME));
        }
    }

    /**
     * v2 转 v1, 字段不丢失, 多余字段放到 properties 中
     */
    @Test
    public void testV2toV1() throws Exception {
        {
            // fastjson 反序列化
            InfoV1 result = JsonUtil.parse(V2_JSON_STR, InfoV1.class);
            Assert.assertEquals(V2.getImg(), result.getImg());
            Assert.assertEquals(V2.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
            Assert.assertEquals(V2.getName(), result.get(FIELD_NAME));
        }
        {
            // jackson 反序列化
            InfoV1 result = OBJECT_MAPPER.readValue(V2_JSON_STR, InfoV1.class);
            Assert.assertEquals(V2.getImg(), result.getImg());
            Assert.assertEquals(V2.get(FIELD_EXTRA), result.get(FIELD_EXTRA));
            Assert.assertEquals(V2.getName(), result.get(FIELD_NAME));
        }
    }


}
