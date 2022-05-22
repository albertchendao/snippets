package org.example.type;

public class Child<T> implements Parent<T> {

    private Class<T> cls;

    public Child(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public Class<T> clazz() {
        return cls;
    }
}
