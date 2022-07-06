package org.example.pattern.abstractdocument;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Document {

    /**
     * 添加属性值
     */
    void put(String key, Object value);

    /**
     * 获取属性值
     */
    Object get(String key);

    /**
     * 转换成其他类型
     */
    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
