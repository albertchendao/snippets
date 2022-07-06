package org.example.pattern.abstractdocument;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class AbstractDocument implements Document {

    private Map<String, Object> props =  new HashMap<>();

    public AbstractDocument(Map<String, Object> props) {
        this.props = props;
    }

    @Override
    public void put(String key, Object value) {
        props.put(key, value);
    }

    @Override
    public Object get(String key) {
        return props.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        return Optional.ofNullable(get(key))
                .map(e -> (List<Map<String, Object>>)e)
                .map(Collection::stream)
                .orElse(Stream.empty())
                .map(constructor);
    }
}
