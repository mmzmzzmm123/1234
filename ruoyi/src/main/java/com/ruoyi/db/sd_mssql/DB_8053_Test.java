package com.ruoyi.db.sd_mssql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DB_8053_Test {

	private static String IP = "192.168.80.53";
	private static String DBName = "ERPDATA";
	private static String USER = "sa";
	private static String PWD = "#080507h";

	/** 创建数据库对象 */
	private static Connection getSQLConnection() {
		Connection con = null;
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			// 加上 useunicode=true;characterEncoding=UTF-8 防止中文乱码
			con = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";useunicode=true;characterEncoding=UTF-8",
					USER, PWD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("==连接数据库失败===");
		}
		// System.out.println("==连接数据库成功===");
		return con;
	}

	public static void main(String[] args) {

	}



	public static List<HashMap<String,String>> DoGetChangeMouldTimeList() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"\tLine,\n" +
					"\tLEFT ( MouldingStyleCode, charindex( '-', MouldingStyleCode ) - 1 ) as Mould,MouldingStyleCode,\n" +
					"\tStartTime,\n" +
					"\tEndTime ,\n" +
					"\tDATEDIFF( minute,starttime, endtime) as ChangeMouldTime\n" +
					"FROM\n" +
					"\t[dbo].[MouldingDisplayBoard] \n" +
					"WHERE\n" +
					"\tChangeMould =  1 and \tDATEDIFF( minute,starttime, endtime) > 20\n" +
					"\torder by starttime desc \n";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("Line",rs.getString("Line"));
				map.put("Mould",rs.getString("Mould"));
				map.put("MouldingStyleCode",rs.getString("MouldingStyleCode"));
				map.put("StartTime",rs.getString("StartTime"));
				map.put("EndTime",rs.getString("EndTime"));
				map.put("ChangeMouldTime",rs.getString("ChangeMouldTime"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}



	public static List<HashMap<String,String>> DoGet_avg_changemouldtime_date_range(String fstarttime, String fendtime) {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT AVG\r\n" +
					"	(\r\n" +
					"	DATEDIFF( MINUTE, StartTime, EndTime )) AS avgchangemouldtime \r\n" +
					"FROM\r\n" +
					"	dbo.MouldingDisplayBoard \r\n" +
					"WHERE\r\n" +
					"\tChangeMould =  1 and \tDATEDIFF( minute,starttime, endtime) > 20\n" +
					"	AND Deleted = 0 \r\n" +
					"	AND StartTime > '" + fstarttime + "' \r\n" +
					"	AND endtime < '" + fendtime + "'";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("avgchangemouldtime",rs.getString("avgchangemouldtime"));
				hashMapList.add(map);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return hashMapList;
	}



	public static String JsonTest() {
		StringBuilder jsonpath = new StringBuilder();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT * from [chen_12个月_A版本BOM销售额] FOR JSON PATH";
			Statement stmt = conn.createStatement();//

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				jsonpath.append(rs.getString(1));


			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return jsonpath.toString();
	}
}
