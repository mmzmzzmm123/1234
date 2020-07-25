package com.muster.logic;

import com.muster.logic.BO.DaoContext;
import com.muster.logic.model.ProcedureResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Description
 * <p>
 * </p>
 * DATE 2020-07-04.
 *
 * @author 刘江涛.
 */
@Service
public class DbLogicServiceImpl implements DbLogicService {

    Logger log = LoggerFactory.getLogger(DbLogicServiceImpl.class);

    private DataSource dataSource;

    private final static int OUPUT_INDEX = 6;

    @Autowired
    DbLogicServiceImpl(@Qualifier("masterDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ProcedureResult exec(final String procedureName, final Map<String, Object> params) {
        ProcedureResult result = new ProcedureResult();
        DaoContext context = DaoContextUtils.createContext(params);
        String sql = this.createProcedure(procedureName, params.size());

        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            // 设置上下文参数
            this.setContext(cs, context);
            // 设置业务参数
            this.setParams(cs, params);

            cs.registerOutParameter(6, Types.INTEGER);
            cs.execute();

            int res = cs.getInt(OUPUT_INDEX);
            result.setRes(res);
            if (log.isDebugEnabled()) {
                log.debug("{}, {}, {}, {}", procedureName, context.toString(), params, res);
            }
            List<Map<String, Object>> list = this.getResult(cs);
            result.setResult(list);

        } catch (SQLException e) {
            log.error("{}, {}, {}", procedureName, context.toString(), params, e);
        }
        return result;
    }

    private List<Map<String, Object>> getResult(final CallableStatement cs) {
        List<Map<String, Object>> list = new ArrayList<>();
        try (ResultSet rs = cs.getResultSet();) {
            if (Objects.isNull(rs)) {
                return list;
            }
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String name = rsmd.getColumnName(i);
                    String type = rsmd.getColumnClassName(i);
                    Object value = null;
                    if ("java.lang.String".equals(type)) {
                        value = rs.getString(i);
                    } else if ("java.lang.Integer".equals(type)) {
                        value = rs.getInt(i);
                    } else if ("java.sql.Date".equals(type)) {
                        value = rs.getDate(i);
                    } else if ("java.math.BigDecimal".equals(type)) {
                        value = rs.getBigDecimal(i);
                    } else if ("java.lang.Boolean".equals(type)) {
                        value = rs.getBoolean(i);
                    } else if ("java.lang.Long".equals(type)) {
                        value = rs.getLong(i);
                    } else if ("java.sql.Timestamp".equals(type)) {
                        value = rs.getTimestamp(i);
                    }
                    map.put(name, value);
                }

                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void setParams(final CallableStatement cs, final Map<String, Object> params) {
        int index = 7;
        List<String> keys = params.keySet().stream().sorted().collect(Collectors.toList());
        for (int i = 0; i < keys.size(); i++) {
            setParam(cs, params, keys.get(i), index);
            index++;
        }
    }

    private void setParam(final CallableStatement cs, final Map<String, Object> params, final String key,
                          final int index) {
        Object v = params.get(key);

        try {
            if (v instanceof String) {
                cs.setString(index, v.toString());
            } else if (v instanceof Integer) {
                cs.setInt(index, Integer.valueOf(v.toString()));
            } else if (v instanceof Double) {
                cs.setDouble(index, Double.valueOf(v.toString()));
            }
        } catch (SQLException e) {

        }

    }

    private void setContext(final CallableStatement cs, final DaoContext context) throws SQLException {
        cs.setString(1, context.getAppId());
        cs.setString(2, context.getBizId());
        cs.setString(3, context.getUhId());
        cs.setInt(4, context.getUserId());
        cs.setInt(5, context.getToken());
    }

    private String createProcedure(final String name, final int size) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < size; i++) {
            str.append(" ,?");
        }

        return String.format(procedureTemplate, name, str.toString());
    }

    private final static String procedureTemplate = "call %s(?, ?, ?, ?, ?, ?%s)";
}
