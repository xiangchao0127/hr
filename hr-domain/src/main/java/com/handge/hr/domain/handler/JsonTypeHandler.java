package com.handge.hr.domain.handler;

/**
 * Created by DaLu Guo on 2018/8/23.
 */
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes({Object.class})
public class JsonTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Object o, JdbcType jdbcType) throws SQLException {
        PGobject jsonObject = new PGobject();
        jsonObject.setType("json");
        jsonObject.setValue(new JsonParser().parse(o.toString()).toString());
        preparedStatement.setObject(i, jsonObject);

    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Gson gson = new Gson();
        return gson.fromJson(resultSet.getString(s), Object.class);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Gson gson = new Gson();
        return gson.fromJson(resultSet.getString(i), Object.class);
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Gson gson = new Gson();
        return gson.fromJson(callableStatement.getString(i), Object.class);
    }
}
