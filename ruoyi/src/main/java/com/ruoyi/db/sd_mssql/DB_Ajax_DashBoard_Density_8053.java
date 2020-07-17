package com.ruoyi.db.sd_mssql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DB_Ajax_DashBoard_Density_8053 {

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

	public static List<String> DoGet72hdensity() {
		List<String> list = new ArrayList<String>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\r\n" +
					"	ProductDate+TIME as time,\r\n" +
					"	AVG ( Density )  as density\r\n" +
					"FROM\r\n" +
					"	[dbo].[MouldingRealTimeDensity] \r\n" +
					"WHERE\r\n" +
					"	CreatedOn > GETDATE( ) - 3\r\n" +
					"GROUP BY\r\n" +
					"	ProductDate,\r\n" +
					"TIME  ORDER BY MAX(CreatedOn)";
			Statement stmt = conn.createStatement();//
//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				list.add("{time:\"" + rs.getString("time") + "\",density:\"" + rs.getString("density")
						+ "\"}");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return list;
	}
	public static List<String> DoGetgroupdensity() {
		List<String> list = new ArrayList<String>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\r\n" +
					"	line AS line,\r\n" +
					"	AVG ( Density ) AS density \r\n" +
					"FROM\r\n" +
					"	[dbo].[MouldingRealTimeDensity] \r\n" +
					"WHERE\r\n" +
					"	CreatedOn > dbo.GetWorkShiftStartDate ( GETDATE( ) ) \r\n" +
					"GROUP BY\r\n" +
					"	line \r\n" +
					"ORDER BY\r\n" +
					"	convert(int,REPLACE( line, '#', '' ) )";
			Statement stmt = conn.createStatement();//
//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				list.add("{line:\"" + rs.getString("line") + "\",density:\"" + rs.getString("density")
						+ "\"}");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return list;
	}
	public static List<String> DoGet30ddensity() {
		List<String> list = new ArrayList<String>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\r\n" +
					"	ProductDate as time,\r\n" +
					"	AVG ( Density )  as density\r\n" +
					"FROM\r\n" +
					"	[dbo].[MouldingRealTimeDensity] \r\n" +
					"WHERE\r\n" +
					"	CreatedOn > GETDATE( ) - 30\r\n" +
					"GROUP BY\r\n" +
					"	ProductDate  ORDER BY MAX(CreatedOn)";
			Statement stmt = conn.createStatement();//
//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				list.add("{time:\"" + rs.getString("time") + "\",density:\"" + rs.getString("density")
						+ "\"}");
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return list;
	}


	public static List<HashMap<String,String>> DoGet30dDensityListMap() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\r\n" +
					"	ProductDate as time,\r\n" +
					"	AVG ( Density )  as density\r\n" +
					"FROM\r\n" +
					"	[dbo].[MouldingRealTimeDensity] \r\n" +
					"WHERE\r\n" +
					"	CreatedOn > GETDATE( ) - 30\r\n" +
					"GROUP BY\r\n" +
					"	ProductDate  ORDER BY MAX(CreatedOn)";
			Statement stmt = conn.createStatement();//
//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("density",rs.getString("density"));
				map.put("time",rs.getString("time"));
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



	public static List<HashMap<String,String>> DoGet72hDensityListMap() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\r\n" +
					"	ProductDate+TIME as time,\r\n" +
					"	AVG ( Density )  as density\r\n" +
					"FROM\r\n" +
					"	[dbo].[MouldingRealTimeDensity] \r\n" +
					"WHERE\r\n" +
					"	CreatedOn > GETDATE( ) - 3\r\n" +
					"GROUP BY\r\n" +
					"	ProductDate,\r\n" +
					"TIME  ORDER BY MAX(CreatedOn)";
			Statement stmt = conn.createStatement();//
//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("density",rs.getString("density"));
				map.put("time",rs.getString("time"));
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



	public static List<HashMap<String,String>> DoGetGroupDensityListMap() {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\r\n" +
					"	line AS line,\r\n" +
					"	AVG ( Density ) AS density \r\n" +
					"FROM\r\n" +
					"	[dbo].[MouldingRealTimeDensity] \r\n" +
					"WHERE\r\n" +
					"	CreatedOn > dbo.GetWorkShiftStartDate ( GETDATE( ) ) \r\n" +
					"GROUP BY\r\n" +
					"	line \r\n" +
					"ORDER BY\r\n" +
					"	convert(int,REPLACE( line, '#', '' ) )";
			Statement stmt = conn.createStatement();//
//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("density",rs.getString("density"));
				map.put("time",rs.getString("line"));
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

	public static List<HashMap<String,String>> getDensityDataRange(String fDateStart, String fDateEnd) {
		List<HashMap<String,String>> hashMapList = new ArrayList<>();
		try {
			Connection conn = getSQLConnection();
			String sql = "SELECT\n" +
					"\tProductDate AS TIME,\n" +
					"\tAVG ( Density ) AS density \n" +
					"FROM\n" +
					"\t[dbo].[MouldingRealTimeDensity] \n" +
					"WHERE\n" +
					"\tCreatedOn >= '"+fDateStart+"' and CreatedOn <= '"+fDateEnd+"'" +
					"GROUP BY\n" +
					"\tProductDate \n" +
					"ORDER BY\n" +
					"\tMAX ( CreatedOn );";
			Statement stmt = conn.createStatement();//
//			System.out.println(sql);

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// 截面积(mm2)
				HashMap<String,String> map = new HashMap<>();
				map.put("density",rs.getString("density"));
				map.put("time",rs.getString("time"));
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


}
