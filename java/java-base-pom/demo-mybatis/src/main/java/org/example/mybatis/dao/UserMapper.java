package org.example.mybatis.dao;


import org.example.mybatis.entity.User;

public interface UserMapper {
    User getUser(int id);
}
