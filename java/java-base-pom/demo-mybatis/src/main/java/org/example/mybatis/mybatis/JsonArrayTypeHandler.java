package org.example.mybatis.mybatis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.example.mybatis.entity.User;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * MyBatis 通用 JSON 转换
 */
@Slf4j
public class JsonArrayTypeHandler<T> extends BaseTypeHandler<List<T>> {

    private Class<List<T>> type;

    public JsonArrayTypeHandler(Class<List<T>> type) throws Exception {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        final Type genericSuperclass = type.getGenericSuperclass();
        final TypeVariable<Class<List<T>>>[] typeParameters = type.getTypeParameters();
        final Method method = type.getMethod("get", int.class);
        final Type genericReturnType = method.getGenericReturnType();
        System.out.println(type);
        this.type = type;
    }

    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, obj2json(parameter));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String i = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return json2obj(i);
        }
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String i = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return json2obj(i);
        }
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String i = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return json2obj(i);
        }
    }

    private List<T> json2obj(String json) {
        if (json == null || json.length() == 0) {
            return null;
        }
        return (List<T>) JSON.parseArray(json, User.class);
    }

    private String obj2json(Object obj) {
        return JSON.toJSONString(obj);
    }

}
