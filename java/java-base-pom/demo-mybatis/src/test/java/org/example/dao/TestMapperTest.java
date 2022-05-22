package org.example.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.example.mybatis.dao.TestMapper;
import org.example.mybatis.entity.TestEntity;
import org.example.mybatis.entity.User;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Consumer;

public class TestMapperTest {

    protected void doMapper(Consumer<SqlSession> consumer) {
        String resource = "org/example/mybatis/Configuration.xml";
        Reader reader;
        try {
            reader = Resources.getResourceAsReader(resource);
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder()
                    .build(reader);
            final Configuration configuration = sqlMapper.getConfiguration();
            SqlSession session = sqlMapper.openSession();
            try {
                consumer.accept(session);
            } finally {
                session.commit();
                session.close();
            }
            final TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
            System.out.println("re");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert() {
        doMapper(sqlSession -> {
            TestEntity entity = new TestEntity();
            final User user = new User();
            user.setId(10);
            user.setName("Albert");
            entity.setM(ImmutableMap.of("albert", user));
            entity.setA(ImmutableList.of(user));
            entity.setE(user);
            entity.setEs(ImmutableList.of(user));
            final TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
            testMapper.insert(entity);
            System.out.println(entity.getId());
        });
    }

    @Test
    public void testById() {
        doMapper(sqlSession -> {
            final TestMapper testMapper = sqlSession.getMapper(TestMapper.class);
            final TestEntity entity = testMapper.byId(2L);
            System.out.println(entity);
        });
    }
}
