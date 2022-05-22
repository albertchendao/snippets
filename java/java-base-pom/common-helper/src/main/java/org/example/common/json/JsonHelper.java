package org.example.common.json;

import com.alibaba.fastjson.JSON;

public class JsonHelper {

    /**
     * 对象序列化为 json
     */
    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

}
