package org.example.junit;

public class TestEntity {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = String.valueOf(id);
    }
}
