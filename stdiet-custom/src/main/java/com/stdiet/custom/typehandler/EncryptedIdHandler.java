package com.stdiet.custom.typehandler;

import com.stdiet.common.utils.sign.AesUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({String.class})
@MappedJdbcTypes({JdbcType.BIGINT})
public class EncryptedIdHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String encrypedId, JdbcType jdbcType) throws SQLException {
        preparedStatement.setLong(i, Long.parseLong(AesUtils.decrypt(encrypedId)));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return this.encryptedString(resultSet.getLong(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.encryptedString(resultSet.getLong(i));
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.encryptedString(callableStatement.getLong(i));
    }

    private String encryptedString(Long id) {
        return AesUtils.encrypt(String.valueOf(id));
    }
}
