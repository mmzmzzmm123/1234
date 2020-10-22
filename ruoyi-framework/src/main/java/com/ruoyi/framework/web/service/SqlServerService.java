package com.ruoyi.framework.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import com.alibaba.fastjson.JSONObject;

/**
 * jdbc 调用sqlserver
 */
@Component
public class SysLoginService
{
	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;
	@Autowired
	protected FlowMenuMapper flowMenuMapper;

    public void testSqlServerRead() {
		String sql = "SELECT * FROM table_xxx;";
		List<Map<String, Object>> result = jdbcTemplate2.queryForList(sql);
		for (Map<String, Object> per : result) {
			System.out.println(JSONObject.toJSONString(per));
		}
	}
}