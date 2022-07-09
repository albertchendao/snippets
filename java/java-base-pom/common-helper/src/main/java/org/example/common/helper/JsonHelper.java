package org.example.common.helper;

import com.alibaba.fastjson.JSON;

public class JsonHelper {

    /**
     * 对象序列化为 json
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 将 json 转换为对象
     */
    public static <T> T parseObject(String json, Class<T> cls) {
        return JSON.parseObject(json, cls);
    }


}
