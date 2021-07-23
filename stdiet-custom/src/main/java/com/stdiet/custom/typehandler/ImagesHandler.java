package com.stdiet.custom.typehandler;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MappedTypes({List.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class ImagesHandler extends BaseTypeHandler<List<String>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {
        String str = Joiner.on("\\|").skipNulls().join(strings);
        preparedStatement.setString(i, str);
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return this.stringToList(resultSet.getString(s));
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.stringToList(resultSet.getString(i));
    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.stringToList(callableStatement.getString(i));
    }

    private List<String> stringToList(String string) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(string)) {
            String[] urls = string.split("\\|");
            for (String url : urls) {
                list.add(AliyunOSSUtils.generatePresignedUrl(url));
            }
        }
        return list;
    }
}
