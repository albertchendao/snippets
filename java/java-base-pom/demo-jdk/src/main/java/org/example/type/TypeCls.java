package org.example.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 泛型类，参数为T 和 V
 */
public class TypeCls<T, V extends @Custom Number & Serializable> {
    private Number number;
    public T t;
    public V v;
    public List<T> list = new ArrayList<>();
    public Map<String, T> map = new HashMap<>();

    public T[] tArray;
    public List<T>[] ltArray;

    public TypeCls testClass;
    public TypeCls<T, Integer> testClass2;

    public Map<? super String, ? extends Number> mapWithWildcard;

    //泛型构造函数,泛型参数为X
    public <X extends Number> TypeCls(X x, T t) {
        number = x;
        this.t = t;
    }

    //泛型方法，泛型参数为Y
    public <Y extends T> void method(Y y) {
        t = y;
    }
}

