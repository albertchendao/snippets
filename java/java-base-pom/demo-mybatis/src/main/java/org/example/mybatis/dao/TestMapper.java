package org.example.mybatis.dao;

import org.apache.ibatis.annotations.*;
import org.example.mybatis.entity.TestEntity;
import org.example.mybatis.mybatis.JsonArrayTypeHandler;
import org.example.mybatis.mybatis.JsonTypeHandler;

/**
 * 测试 JsonTypeHandler
 * <p>
 * CREATE TABLE `tb_test` (
 * `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
 * `m` json DEFAULT NULL,
 * `a` json DEFAULT NULL,
 * `e` json DEFAULT NULL,
 * `es` json DEFAULT NULL,
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8
 */
public interface TestMapper {

    @Select("SELECT id, m, a, e, es FROM tb_test WHERE `id` = #{id} ")
    @Results(value = {
            @Result(column = "id", property = "id"),
            @Result(column = "m", property = "m", typeHandler = JsonTypeHandler.class),
            @Result(column = "a", property = "a", typeHandler = JsonArrayTypeHandler.class),
            @Result(column = "e", property = "e", typeHandler = JsonTypeHandler.class),
            @Result(column = "es", property = "es", typeHandler = JsonArrayTypeHandler.class),
    }, id = "TestEntity")
    TestEntity byId(@Param("id") Long id);

    @Insert({"<script>",
            "INSERT INTO tb_test",
            "<trim prefix='(' suffix=')' suffixOverrides=',' >",
            "   <if test='m != null'>     `m`, </if>",
            "   <if test='a != null'>     `a`, </if>",
            "   <if test='e != null'>     `e`, </if>",
            "   <if test='es != null'>     `es`, </if>",
            "</trim>",
            "<trim prefix='values (' suffix=')' suffixOverrides=',' >",
            "   <if test='m != null '>     #{m, typeHandler=org.example.mybatis.JsonTypeHandler}, </if>",
            "   <if test='a != null '>     #{a, typeHandler=org.example.mybatis.JsonArrayTypeHandler}, </if>",
            "   <if test='e != null '>     #{e, typeHandler=org.example.mybatis.JsonTypeHandler}, </if>",
            "   <if test='es != null '>     #{es, typeHandler=org.example.mybatis.JsonArrayTypeHandler}, </if>",
            "</trim>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TestEntity entity);
}
