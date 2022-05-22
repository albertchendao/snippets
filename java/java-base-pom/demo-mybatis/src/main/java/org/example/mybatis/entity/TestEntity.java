package org.example.mybatis.entity;

import java.util.List;
import java.util.Map;

public class TestEntity {
    private Long id;
    private Map<String, User> m;
    private List<Object> a;
    private User e;
    private List<User> es;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, User> getM() {
        return m;
    }

    public void setM(Map<String, User> m) {
        this.m = m;
    }

    public List<Object> getA() {
        return a;
    }

    public void setA(List<Object> a) {
        this.a = a;
    }

    public User getE() {
        return e;
    }

    public void setE(User e) {
        this.e = e;
    }

    public List<User> getEs() {
        return es;
    }

    public void setEs(List<User> es) {
        this.es = es;
    }
}
