package org.example.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.mybatis.entity.User;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class UserMapperTest {

    @Test
    public void testGetUser() {
        String resource = "org/example/mybatis/Configuration.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder()
                    .build(reader);

            SqlSession session = sqlMapper.openSession();
            try {
                User user = (User) session.selectOne("org.example.mybatis.dao.UserMapper.getUser", 1);
                System.out.println(user.getId() + "," + user.getName());
            } finally {
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
