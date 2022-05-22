package org.example.mybatis.mybatis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatis 通用 JSON 转换
 *
 * @param <T>
 */
@Slf4j
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

    private Class<T> type;

    public JsonTypeHandler(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        log.debug("type = {}", type.getName());
        log.debug("GenericSuperclass = {}", type.getGenericSuperclass());
        log.debug("isParameterizedType = {}", type.getGenericSuperclass() instanceof ParameterizedType);
        this.type = type;
    }

    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, obj2json(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String i = rs.getString(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return json2obj(i);
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String i = rs.getString(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return json2obj(i);
        }
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String i = cs.getString(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return json2obj(i);
        }
    }

    private T json2obj(String json) {
        if (json == null || json.length() == 0) {
            return null;
        }
        return JSON.parseObject(json, type);
    }

    private String obj2json(Object obj) {
        return JSON.toJSONString(obj);
    }

}
